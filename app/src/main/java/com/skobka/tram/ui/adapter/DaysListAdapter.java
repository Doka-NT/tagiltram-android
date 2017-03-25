/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.skobka.tram.R;
import com.skobka.tram.service.model.Direction;
import com.skobka.tram.ui.abstraction.ListAdapter;
import com.skobka.tram.ui.activity.ScheduleActivity;
import com.skobka.tram.ui.model.DayListItem;

public class DaysListAdapter extends ListAdapter<DayListItem> {

    public DaysListAdapter(Context context) {

        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DayListItem model = getItem(position);
        View view = createView(R.layout.direction_item, convertView, parent);
        TextView dayLabel = (TextView) view.findViewById(R.id.day_name);

        String title = model.getDay().getTitle();
        dayLabel.setText(title);

        final DaysListDirectionListAdapter adapter = new DaysListDirectionListAdapter(view.getContext());
        ListView listView = (ListView) view.findViewById(R.id.direction_list_view);
        listView.setAdapter(adapter);

        adapter.addAll(model.getDirections());
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Direction directionModel = adapter.getItem(position);
                Intent intent = new Intent(view.getContext(), ScheduleActivity.class);

                intent.putExtra(ScheduleActivity.EXTRA_DAY, model.getDay());
                intent.putExtra(ScheduleActivity.EXTRA_DIRECTION, directionModel);
                intent.putExtra(ScheduleActivity.EXTRA_ROUTE, model.getRoute());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                view.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
