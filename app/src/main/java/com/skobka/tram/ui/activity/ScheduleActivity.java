/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.skobka.tram.R;
import com.skobka.tram.service.DataLoader;
import com.skobka.tram.service.LoaderFactory;
import com.skobka.tram.service.listener.ScheduleLoadingListener;
import com.skobka.tram.service.model.Day;
import com.skobka.tram.service.model.Direction;
import com.skobka.tram.service.model.Route;
import com.skobka.tram.service.model.Schedule;
import com.skobka.tram.service.model.Station;
import com.skobka.tram.ui.abstraction.activity.WaitableActivity;
import com.skobka.tram.ui.adapter.ScheduleAdapter;
import com.skobka.tram.ui.adapter.SpinnerAdapter;

public class ScheduleActivity extends WaitableActivity {

    public static final String EXTRA_DAY = "day";
    public static final String EXTRA_DIRECTION = "direction";
    public static final String EXTRA_ROUTE = "route";

    Route route;
    Day day;
    Direction direction;

    SpinnerAdapter spinnerAdapter;
    LoaderFactory loaderFactory = new LoaderFactory();

    DataLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        route = getIntent().getParcelableExtra(EXTRA_ROUTE);
        day = getIntent().getParcelableExtra(EXTRA_DAY);
        direction = getIntent().getParcelableExtra(EXTRA_DIRECTION);

        // Setup spinner
        spinnerAdapter = new SpinnerAdapter(this);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                Station station = spinnerAdapter.getItem(position);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(station))
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        loader = loaderFactory.create(null, new ScheduleLoadingListener() {
            @Override
            public void onScheduleLoaded(final Schedule schedule) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinnerAdapter.addAll(schedule.getStationList());
                        spinnerAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "Не удалось загрузить данные", Toast.LENGTH_SHORT).show();
            }
        });

        loader.loadSchedule(route, direction, day);
        setTitle(route.getFullTitle() + ", " + day.getTitle());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            startDirectionActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void startDirectionActivity() {
        Intent intent = new Intent(this, DirectionActivity.class);
        intent.putExtra(DirectionActivity.EXTRA_ROUTE, route);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startDirectionActivity();
    }

    @Override
    protected View getTargetView() {
        return null;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_STATION = "station";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Station station) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putParcelable(ARG_STATION, station);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(
                LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState
        ) {
            Station station = getArguments().getParcelable(ARG_STATION);
            View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

            ScheduleAdapter scheduleAdapter = new ScheduleAdapter(rootView.getContext());
            ListView listView = (ListView) rootView.findViewById(R.id.schedule_list);
            listView.setAdapter(scheduleAdapter);

            if (station != null) {
                scheduleAdapter.addAll(station.getTimes());
                scheduleAdapter.notifyDataSetInvalidated();
            }

            return rootView;
        }
    }
}
