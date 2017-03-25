/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.ui.model;

import com.skobka.tram.service.model.Day;
import com.skobka.tram.service.model.Direction;
import com.skobka.tram.service.model.Route;

import java.util.List;

public class DayListItem {
    private Day day;
    private List<Direction> directions;
    private Route route;

    public DayListItem(Day day, List<Direction> directions, Route route) {
        this.day = day;
        this.directions = directions;
        this.route = route;
    }

    public Day getDay() {
        return day;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public Route getRoute() {
        return route;
    }
}
