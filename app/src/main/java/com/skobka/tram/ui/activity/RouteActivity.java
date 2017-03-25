/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */


package com.skobka.tram.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.skobka.tram.R;
import com.skobka.tram.service.DataLoader;
import com.skobka.tram.service.LoaderFactory;
import com.skobka.tram.service.listener.RoutesLoadingListener;
import com.skobka.tram.service.model.Route;
import com.skobka.tram.ui.abstraction.activity.WaitableActivity;
import com.skobka.tram.ui.adapter.RouteListAdapter;

import java.util.List;

public class RouteActivity extends WaitableActivity {
    private ListView routeList;
    private RouteListAdapter routeListAdapter;
    private LoaderFactory loaderFactory = new LoaderFactory();
    private DataLoader loader;

    private RoutesLoadingListener routesLoadingListener = new RoutesLoadingListener() {
        @Override
        public void onRoutesLoaded(final List<Route> routeList) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    routeListAdapter.addAll(routeList);
                    routeListAdapter.notifyDataSetChanged();
                    hideWaitBox();
                }
            });
        }

        @Override
        public void onFailure() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showWaitBox();
                    Toast.makeText(getApplicationContext(), "Не удалось загрузить данные.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        routeList = (ListView) findViewById(R.id.routeList);
        routeListAdapter = new RouteListAdapter(this);
        routeList.setAdapter(routeListAdapter);

        loader = loaderFactory.create(routesLoadingListener, null);
        routeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DirectionActivity.class);
                Route item = routeListAdapter.getItem(position);
                intent.putExtra(DirectionActivity.EXTRA_ROUTE, item);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loader.loadRoutes();
    }

    @Override
    protected View getTargetView() {
        return routeList;
    }
}
