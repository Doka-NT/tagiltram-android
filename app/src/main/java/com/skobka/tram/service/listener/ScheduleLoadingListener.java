/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

/*
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

/*
 *
 *  * Author: Soshnikov Artem <213036@skobka.com>
 *
 */

package com.skobka.tram.service.listener;

import com.skobka.tram.service.model.Schedule;

public interface ScheduleLoadingListener extends ListLoadListener {
    void onScheduleLoaded(Schedule schedule);
}
