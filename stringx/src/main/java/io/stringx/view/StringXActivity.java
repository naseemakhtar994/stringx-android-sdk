package io.stringx.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuInflater;

import io.stringx.RestartBroadcast;
import io.stringx.StringX;

public class StringXActivity extends AppCompatActivity {

    private AppCompatDelegate mDelegate;
    private RestartBroadcast broadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcast = RestartBroadcast.register(this, getClass());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(StringX.wrap(base));
    }

    @NonNull
    public AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = StringX.wrap(this, this);
        }
        if (mDelegate == null) {
            mDelegate = super.getDelegate();
        }
        return mDelegate;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StringX stringX = StringX.get(this);
        if (stringX != null) {
            stringX.onResume(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RestartBroadcast.unregister(this,broadcast);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @NonNull
    @Override
    public MenuInflater getMenuInflater() {
        return StringX.wrap(this, super.getMenuInflater());
    }
}
