package com.materialdesign.ramiz.materialdesignnavdrawer;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //if hamburger icon is enabled the it means we have
        //registered a drawer toggle listener so we should
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //if hamburger icon is enabled the it means we have
        //registered a drawer toggle listener so we should
        //update its configuration
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //handle your action bar items click
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //if nav drawer is open then hide action items for the current view
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mNavigationView);
        //menu.findItem(R.id.action_websearch).setVisible(!isDrawerOpen);
        return super.onPrepareOptionsMenu(menu);
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
                onNavDrawerClosed();
                //invalidate action bar menu items so that it
                //calls onPrepareOptionsMenu in which new
                //items can be added according to selection made by user
                invalidateOptionsMenu();
            }

            //called when nav drawer is completely open
            //and settled
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                onNavDrawerOpened();
                //invalidate action bar menu items so that it
                //calls onPrepareOptionsMenu in which we hide
                //action bar menu items if drawer is open
                invalidateOptionsMenu();
            }
        };

        //add listener to navigation drawer, to be called
        //when drawer opens or closes
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    /**
     * Calling this method enable the display
     * of hamburger icon on action bar clicking which
     * will open nav drawer
     */
    private void setDrawerIndicatorIconEnabled(boolean isEnabled) {
        mDrawerToggle.setDrawerIndicatorEnabled(isEnabled);
    }
    
    public boolean isDrawerIndicatorIconEnabled() {
        return mDrawerToggle.isDrawerIndicatorEnabled();
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

    /**
     * a helper method in case any subclass is interested in
     * in listening drawer open event. This method will be only called
     * if
     */

    public void onNavDrawerOpened() {}

    /**
     * a helper method in case any subclass is interested in
     * listening drawer close event.
     */
    public void onNavDrawerClosed() {}
}
