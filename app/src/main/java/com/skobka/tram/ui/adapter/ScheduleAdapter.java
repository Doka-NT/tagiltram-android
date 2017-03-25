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

import com.skobka.tram.service.model.Time;
import com.skobka.tram.ui.abstraction.ListAdapter;

public class ScheduleAdapter extends ListAdapter<Time> {
    public ScheduleAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(parent.getContext());
        textView.setPadding(20, 10, 20, 10);

        Time time = getItem(position);
        textView.setText(time.getValue());

        return textView;
    }
}
