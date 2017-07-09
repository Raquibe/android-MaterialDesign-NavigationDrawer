package com.materialdesign.ramiz.materialdesignnavdrawer;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class NavDrawerActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        setupToolbar();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView)findViewById(R.id.navigation_view);

        //assign listeners to all the views we are interested in
        setListeners();

        setTitle(R.string.home_remotes);
    }

    private void setListeners() {
        //add listener to navigation view
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //check/highlight the item selected
                item.setCheckable(true);
                //update activity title
                setTitle(item.getTitle());
                //close nav drawer
                mDrawerLayout.closeDrawer(GravityCompat.START);

                //notify to the system that we have handled
                //the event by sending true
                return true;
            }
        });

        //initialize ActionBarDrawerToggleListener to listen
        //for navigation drawer open/close events. It also handles
        //opening nav drawer on hamburger icon click
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close) {
            //called when nav drawer is completely closed
            //and settled
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            //called when nav drawer is completely open
            //and settled
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //add listener to navigation drawer, to be called
        //when drawer opens or closes
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void setupToolbar() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
        mTitle = title;
    }

    @Override
    public void setTitle(int titleId) {
        getSupportActionBar().setTitle(titleId);
        mTitle = getString(titleId);
    }
}
