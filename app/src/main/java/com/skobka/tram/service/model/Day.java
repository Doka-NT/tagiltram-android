/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.service.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Day implements Parcelable {
    private String id;
    private String title;

    public Day(String id) {
        this.id = id;
    }

    private Day(Parcel in) {
        id = in.readString();
        title = in.readString();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getTitle() {
        return id.equals("working") ? "Рабочий" : "Выходной";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
    }
}
