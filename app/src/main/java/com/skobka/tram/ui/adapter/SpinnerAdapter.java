/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skobka.tram.service.model.Station;
import com.skobka.tram.ui.abstraction.ListAdapter;

public class SpinnerAdapter extends ListAdapter<Station> implements android.support.v7.widget.ThemedSpinnerAdapter {
    private final ThemedSpinnerAdapter.Helper mDropDownHelper;

    public SpinnerAdapter(Context context) {
        super(context);
        mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }

    @Override
    public void setDropDownViewTheme(@Nullable Resources.Theme theme) {
        mDropDownHelper.setDropDownViewTheme(theme);
    }

    @Nullable
    @Override
    public Resources.Theme getDropDownViewTheme() {
        return mDropDownHelper.getDropDownViewTheme();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        Station station = getItem(position);
        textView.setText(station.getTitle());
        textView.setPadding(40, 20, 40, 20);

        return textView;
    }
}
