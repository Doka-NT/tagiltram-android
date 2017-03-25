/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.service.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Station implements Parcelable {
    private static final String SKIP_TIME_VALUE = "EMPTY";

    private String title;
    private List<Time> times = new ArrayList<>();

    public Station(String title) {
        this.title = title;
    }

    private Station(Parcel in) {
        title = in.readString();
        times = in.createTypedArrayList(Time.CREATOR);
    }

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };

    public void addTime(Time time) {
        times.add(time);
    }

    public String getTitle() {
        return title;
    }

    public List<Time> getTimes() {
        return times;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeTypedList(times);
    }

    void filter() {
        ArrayList<Time> toRemove = new ArrayList<>();
        for (Time time : times) {
            if (!time.getValue().equals(SKIP_TIME_VALUE)) {
                continue;
            }
            toRemove.add(time);
        }

        times.removeAll(toRemove);
    }
}
