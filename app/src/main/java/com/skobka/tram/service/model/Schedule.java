/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.service.model;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private static final String SKIP_STATION_VALUE = "(поезд)";

    private List<Station> stationList = new ArrayList<>();

    public void add(Station station) {
        stationList.add(station);
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public Station getStation(int index) {
        return stationList.get(index);
    }

    public void filter() {
        ArrayList<Station> toRemove = new ArrayList<>();

        for (Station station : stationList) {
            if (station.getTitle().equals(SKIP_STATION_VALUE)) {
                toRemove.add(station);
                continue;
            }

            station.filter();
        }

        stationList.removeAll(toRemove);
    }
}
