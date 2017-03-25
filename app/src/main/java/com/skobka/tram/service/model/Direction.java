/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */


package com.skobka.tram.service.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Direction implements Parcelable {
    private static final String TYPE_IN = "inner";
    private static final String TYPE_OUT = "outer";

    private String type;
    private String title;

    public Direction(String type, String title) {
        this.type = type;
        this.title = title;
    }

    private Direction(Parcel in) {
        type = in.readString();
        title = in.readString();
    }

    public static final Creator<Direction> CREATOR = new Creator<Direction>() {
        @Override
        public Direction createFromParcel(Parcel in) {
            return new Direction(in);
        }

        @Override
        public Direction[] newArray(int size) {
            return new Direction[size];
        }
    };

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(title);
    }
}
