/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skobka.tram.R;
import com.skobka.tram.service.model.Day;
import com.skobka.tram.service.model.Direction;
import com.skobka.tram.service.model.Route;
import com.skobka.tram.ui.abstraction.ListAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class RouteListAdapter extends ListAdapter<Route> {
    public RouteListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = createView(R.layout.route_item, convertView, parent);

        Route route = this.getItem(position);
        ((TextView) view.findViewById(R.id.routeItemTitle)).setText(route.getFullTitle());
        ((TextView) view.findViewById(R.id.routeItemDays)).setText(getRouteDays(route));
        ((TextView) view.findViewById(R.id.routeItemDirections)).setText(getRouteDirections(route));

        return view;
    }

    private String getRouteDirections(Route route) {
        ArrayList<String> items = new ArrayList<>();
        for (Direction direction: route.getDirections()) {
            items.add(direction.getTitle());
        }

        return StringUtils.join(items, "\n");
    }

    private String getRouteDays(Route route) {
        ArrayList<String> days = new ArrayList<>();

        for (Day day : route.getDays()) {
            days.add(day.getTitle());
        }

        return StringUtils.join(days, ", ");
    }
}
