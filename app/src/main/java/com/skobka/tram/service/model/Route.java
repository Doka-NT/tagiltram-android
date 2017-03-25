/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.service.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Route implements Parcelable {
    private int id;
    private String title;
    private List<Day> days;
    private List<Direction> directions;

    public Route(int id, String title) {
        this.id = id;
        this.title = title;
    }

    private Route(Parcel in) {
        id = in.readInt();
        title = in.readString();
        days = in.createTypedArrayList(Day.CREATOR);
        directions = in.createTypedArrayList(Direction.CREATOR);
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Day> getDays() {
        return days;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeTypedList(days);
        dest.writeTypedList(directions);
    }

    public String getFullTitle () {
        return "Маршрут №" + title;
    }
}
