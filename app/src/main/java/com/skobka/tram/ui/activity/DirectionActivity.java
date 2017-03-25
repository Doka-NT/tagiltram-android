/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.skobka.tram.R;
import com.skobka.tram.service.model.Day;
import com.skobka.tram.service.model.Route;
import com.skobka.tram.ui.abstraction.activity.WaitableActivity;
import com.skobka.tram.ui.adapter.DaysListAdapter;
import com.skobka.tram.ui.model.DayListItem;

public class DirectionActivity extends WaitableActivity {
    public static final String EXTRA_ROUTE = "route";

    ListView listView;
    DaysListAdapter daysListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        listView = (ListView) findViewById(R.id.days_list_view);
        daysListAdapter = new DaysListAdapter(getApplicationContext());
        listView.setAdapter(daysListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showWaitBox();
        Route route = getIntent().getParcelableExtra(EXTRA_ROUTE);
        setTitle(route.getFullTitle());
        updateList(route);
    }

    private void updateList(Route route) {
        for (Day day : route.getDays()) {
            daysListAdapter.add(new DayListItem(day, route.getDirections(), route));
        }
        daysListAdapter.notifyDataSetChanged();

        hideWaitBox();
    }

    @Override
    protected View getTargetView() {
        return listView;
    }
}
