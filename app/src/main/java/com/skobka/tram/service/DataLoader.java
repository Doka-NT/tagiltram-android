/*
 * Copyright
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.service;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.skobka.tram.service.listener.RoutesLoadingListener;
import com.skobka.tram.service.listener.ScheduleLoadingListener;
import com.skobka.tram.service.model.Day;
import com.skobka.tram.service.model.Direction;
import com.skobka.tram.service.model.Route;
import com.skobka.tram.service.model.Schedule;
import com.skobka.tram.service.model.Station;
import com.skobka.tram.service.model.Time;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DataLoader {
    private static final String HTTP_SCHEME = "http";
    private static final String HTTP_DOMAIN = "tagiltram.ru";
    private static final String BASE_URL = "services/schedule-xml";
    private static final String ROUTES_LIST_URL = "routes-list.xml";
    private static final String SCHEDULE_LIST_URL = "schedule.xml";
    private static final String ELEMENT_ROUTES = "routes";
    private static final String PARAM_ROUTE = "route";
    private static final String PARAM_DAY = "day";
    private static final String PARAM_DIR = "dir";


    private RoutesLoadingListener loadingListener;
    private ScheduleLoadingListener scheduleLoadingListener;

    DataLoader(
            @Nullable RoutesLoadingListener routesLoadingListener,
            @Nullable ScheduleLoadingListener scheduleLoadingListener
    ) {
        this.loadingListener = routesLoadingListener;
        this.scheduleLoadingListener = scheduleLoadingListener;
    }

    public void loadRoutes() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URI(createUriBuilder(ROUTES_LIST_URL).build().toString()).toURL();
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    parseRoutes(connection.getInputStream());
                } catch (IOException | ParserConfigurationException | SAXException | URISyntaxException e) {
                    e.printStackTrace();
                    loadingListener.onFailure();
                }
            }
        });

        thread.start();
    }

    public void loadSchedule(final Route route, final Direction direction, final Day day) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Uri uri = createUriBuilder(SCHEDULE_LIST_URL)
                            .appendQueryParameter(PARAM_ROUTE, String.valueOf(route.getId()))
                            .appendQueryParameter(PARAM_DAY, day.getId())
                            .appendQueryParameter(PARAM_DIR, direction.getType())
                            .build();
                    URI juri = new URI(uri.toString());
                    URLConnection connection = juri.toURL().openConnection();
                    connection.connect();
                    parseSchedule(connection.getInputStream());
                } catch (IOException | ParserConfigurationException | SAXException | URISyntaxException e) {
                    e.printStackTrace();
                    loadingListener.onFailure();
                }
            }
        });

        thread.start();
    }

    private Uri.Builder createUriBuilder(String path) {
        return new Uri.Builder()
                .scheme(HTTP_SCHEME)
                .authority(HTTP_DOMAIN)
                .appendEncodedPath(BASE_URL)
                .appendEncodedPath(path)
                ;
    }


    private void parseRoutes(InputStream is) throws ParserConfigurationException, IOException, SAXException {
        Document doc = getDocument(is);

        Node routes = doc.getElementsByTagName(ELEMENT_ROUTES).item(0);
        NodeList routesChildNodes = routes.getChildNodes();

        List<Route> routeList = new ArrayList<>();

        for (int i = 0; i < routesChildNodes.getLength(); i++) {
            Node routeNode = routesChildNodes.item(i);
            if (!(routeNode instanceof Element)) {
                continue;
            }
            Element el = (Element) routeNode;
            String id = el.getAttribute("id");
            String title = el.getAttribute("title");

            List<Day> days = parseDays(el);
            List<Direction> directions = parseDirections(el);

            Route route = new Route(Integer.valueOf(id), title);
            route.setDays(days);
            route.setDirections(directions);

            routeList.add(route);
        }

        is.close();
        loadingListener.onRoutesLoaded(routeList);
    }

    @NonNull
    private Document getDocument(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);

        doc.getDocumentElement().normalize();

        return doc;
    }

    private List<Day> parseDays(Element element) {
        List<Day> days = new ArrayList<>();

        NodeList daysNodes = element.getElementsByTagName("days").item(0).getChildNodes();
        for (int i = 0; i < daysNodes.getLength(); i++) {
            Node node = daysNodes.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Element el = (Element) node;

            days.add(new Day(el.getTextContent()));
        }

        return days;
    }

    private List<Direction> parseDirections(Element element) {
        List<Direction> directions = new ArrayList<>();

        NodeList directionNodes = element.getElementsByTagName("directions").item(0).getChildNodes();
        for (int i = 0; i < directionNodes.getLength(); i++) {
            Node node = directionNodes.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Element el = (Element) node;

            directions.add(new Direction(el.getAttribute("id"), el.getTextContent()));
        }

        return directions;
    }

    private void parseSchedule(InputStream is) throws ParserConfigurationException, IOException, SAXException {
        Document doc = getDocument(is);

        Element scheduleNode = (Element) doc.getElementsByTagName("schedule").item(0);
        Element headerNode = (Element) scheduleNode.getElementsByTagName("header").item(0);

        Schedule schedule = new Schedule();

        parseStation(headerNode, schedule);
        parseTimes(scheduleNode, schedule);
        schedule.filter();

        scheduleLoadingListener.onScheduleLoaded(schedule);
    }

    private void parseTimes(Element scheduleNode, Schedule schedule) {
        NodeList rows = scheduleNode.getElementsByTagName("row");
        for (int i = 0; i < rows.getLength(); i++) {
            Element row = (Element) rows.item(i);
            NodeList cells = row.getElementsByTagName("cell");
            for (int ii = 0; ii < cells.getLength(); ii++) {
                Element cell = (Element) cells.item(ii);
                String textContent = cell.getTextContent();
                Station station = schedule.getStation(ii);
                station.addTime(new Time(textContent));
            }
        }
    }

    private void parseStation(Element headerNode, Schedule schedule) {
        NodeList stationNames = headerNode.getElementsByTagName("hcell");
        for (int i = 0; i < stationNames.getLength(); i++) {
            Element node = (Element) stationNames.item(i);
            String textContent = node.getTextContent();
            schedule.add(new Station(textContent));
        }
    }
}
