/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */


package com.skobka.tram.ui.abstraction;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public abstract class ListAdapter<T> extends AbstractInflaterAdapter {
    private List<T> items = new ArrayList<>();

    public ListAdapter(Context context) {
        super(context);
    }

    public void addAll(List<T> collection) {
        this.items.addAll(collection);
    }

    public void add(T item) {
        this.items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
