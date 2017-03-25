/*
 * Copyright $year$
 * License: Apache
 * Author: Soshnikov Artem <213036@skobka.com>
 */

package com.skobka.tram.ui.abstraction.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.skobka.tram.R;

public abstract class WaitableActivity extends AppCompatActivity {
    private View waitBox;

    abstract protected View getTargetView();

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        waitBox = findViewById(R.id.wait_layout);
    }

    protected void hideWaitBox() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                waitBox.setVisibility(View.GONE);
                getTargetView().setVisibility(View.VISIBLE);
            }
        });
    }

    protected void showWaitBox() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                waitBox.setVisibility(View.VISIBLE);
                getTargetView().setVisibility(View.GONE);
            }
        });
    }
}
