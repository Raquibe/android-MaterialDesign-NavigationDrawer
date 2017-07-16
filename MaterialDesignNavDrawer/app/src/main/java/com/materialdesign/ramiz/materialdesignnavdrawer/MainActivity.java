package com.materialdesign.ramiz.materialdesignnavdrawer;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends NavDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addActionBarDrawerToggle(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        populateDummyDataIntoHeaderView();
    }

    public void populateDummyDataIntoHeaderView() {
        ArrayList<BackendHost> backendHosts = new ArrayList<>();
        BackendHost backendHost = new BackendHost();
        backendHost.name = "Ramiz-MBP";
        backendHost.client = Preferences.Clients.BLUETOOTH;

        BackendHost backendHost1 = new BackendHost();
        backendHost1.name = "Ramiz-MBP-Wifi";
        backendHost1.client = Preferences.Clients.TCP;

        backendHosts.add(backendHost);
        backendHosts.add(backendHost1);
        updateNavDrawerHeaderView(backendHosts);
    }

    @Override
    public void onNavDrawerItemSelected(@IdRes int selectedItemId) {
        Toast.makeText(this, "Navigation item click", Toast.LENGTH_SHORT).show();
    }
}
