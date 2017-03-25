/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.service.listener;

import com.skobka.tram.service.model.Route;

import java.util.List;

public interface RoutesLoadingListener extends ListLoadListener {
    void onRoutesLoaded(List<Route> list);
}
