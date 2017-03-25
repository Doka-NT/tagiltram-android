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

package com.skobka.tram.ui.abstraction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

abstract class AbstractInflaterAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;

    AbstractInflaterAdapter(Context context) {
        setLayoutInflater(context);

    }

    private LayoutInflater getLayoutInflater() {
        return this.layoutInflater;
    }

    protected View createView(int layout, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = getLayoutInflater().inflate(layout, parent, false);
        }
        return view;
    }

    private void setLayoutInflater(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
