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

import com.skobka.tram.service.model.Direction;
import com.skobka.tram.ui.abstraction.ListAdapter;

class DaysListDirectionListAdapter extends ListAdapter<Direction> {
    DaysListDirectionListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Direction direction = getItem(position);
        TextView textView = new TextView(parent.getContext());
        textView.setText(direction.getTitle());
        textView.setPadding(15, 10, 15, 10);

        return textView;
    }
}
