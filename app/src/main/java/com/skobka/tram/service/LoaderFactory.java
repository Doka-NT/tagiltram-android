/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.service;

import android.support.annotation.Nullable;

import com.skobka.tram.service.listener.RoutesLoadingListener;
import com.skobka.tram.service.listener.ScheduleLoadingListener;

public class LoaderFactory {
    public DataLoader create(
            @Nullable RoutesLoadingListener routesLoadingListener,
            @Nullable ScheduleLoadingListener scheduleLoadingListener
    ) {
        return new DataLoader(routesLoadingListener, scheduleLoadingListener);
    }
}
