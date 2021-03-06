package com.materialdesign.ramiz.materialdesignnavdrawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class NavDrawerActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    @Nullable
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private @IdRes int mSelectedNavItemId = R.id.navigation_item_remotes;
    @NonNull
    private NavDrawerHeaderView mNavDrawerHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //call super method directly because we are overriding
        //this method in current class
        super.setContentView(R.layout.activity_nav_drawer);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView)findViewById(R.id.navigation_view);

        if (!License.isUnlocked()) {
            mNavigationView.getMenu().findItem(R.id.navigation_item_upgrade).setVisible(true);
        }

        if (License.showRateOption()) {
            mNavigationView.getMenu().findItem(R.id.navigation_item_rate).setVisible(true);
        }

        mNavDrawerHeaderView = (NavDrawerHeaderView) mNavigationView.getHeaderView(0);

        //assign listeners to all the views we are interested in
        setListeners();
    }

    /**
     * Overridden this method here so that child classes don't overwrite
     * this class's content view and still can call this method
     * to add their content view to nav drawer
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View contentView = getLayoutInflater().inflate(layoutResID, null, false);
        mDrawerLayout.addView(contentView, 0);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //if hamburger icon is enabled the it means we have
        //registered a drawer toggle listener so we should
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //if hamburger icon is enabled the it means we have
        //registered a drawer toggle listener so we should
        //update its configuration
        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //handle your action bar items click
        return super.onOptionsItemSelected(item);
    }

    private void setListeners() {
        //add listener to navigation view
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //check/highlight the item selected
                item.setCheckable(true);

                //save the selected menu item id
                mSelectedNavItemId = item.getItemId();

                //update activity title
                setTitle(item.getTitle());

                //call the method responsible for handling click
                onNavDrawerItemSelected(item.getItemId());

                //close nav drawer
                mDrawerLayout.closeDrawer(GravityCompat.START);

                //notify to the system that we have handled
                //the event by sending true
                return true;
            }
        });

        mNavDrawerHeaderView.setOnItemSelectedListener(new NavDrawerHeaderView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BackendHost backendHost) {
                onNavDrawerHeaderViewItemClick(backendHost);
            }
        });
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
     * Call this method to add a nav drawer indicator icon (Hamburger menu icon)
     * to the toolbar
     * @param toolbar the toolbar to which to add the drawer indicator icon
     */
    protected void addActionBarDrawerToggle(Toolbar toolbar) {
        //initialize ActionBarDrawerToggleListener to listen
        //for navigation drawer open/close events. It also handles
        //opening nav drawer on hamburger icon click
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
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
        mDrawerToggle.syncState();
    }

    /**
     * Updates the content of header view of nav drawer with this new data
     * @param hosts list of hosts to update header with
     */
    protected void updateNavDrawerHeaderView(ArrayList<BackendHost> hosts) {
        mNavDrawerHeaderView.populateData(hosts);
    }


    /**
     * This method is called when user selects an item from nav drawer header view
     * popup menu. Child classes can override this method to listen for this event
     * @param host host object which is selected by user
     */
    protected void onNavDrawerHeaderViewItemClick(BackendHost host) {

    }

    /**
     * Locks the nav drawer so that it does not open either when swiping from left
     * or by clicking on nav drawer indicator icon
     */
    protected void lockNavDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    /**
     * Unlocks the nav drawer if locked so that it does open either by swiping from left
     * or by clicking on nav drawer indicator icon
     */
    protected void unlockNavDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    /**
     * Child classes can call this method to enable the display
     * of hamburger icon on action bar by clicking which
     * will open nav drawer
     */
    protected void setDrawerIndicatorIconEnabled(boolean isEnabled) {
        if (mDrawerToggle != null) {
            mDrawerToggle.setDrawerIndicatorEnabled(isEnabled);
        }
    }

    /**
     * Child classes can call this method to check if nav drawer indicator is enabled
     * @return boolean depending on whether drawer indicator is enabled or not
     */
    protected boolean isDrawerIndicatorIconEnabled() {
        return mDrawerToggle != null && mDrawerToggle.isDrawerIndicatorEnabled();
    }

    /**
     * Call this method to highlight/check a navigation drawer item
     * @param itemId id of the item to highlight
     */
    protected void checkNavDrawerItem(@IdRes int itemId) {
        mNavigationView.getMenu().findItem(itemId).setChecked(true);
    }

    /**
     * Method responsible for handling nav drawer items clicks.
     * If any child classes wants to handle nav drawer item clicks
     * then it should override this method
     * @param selectedItemId id of the item selected
     */
    public void onNavDrawerItemSelected(@IdRes int selectedItemId) {

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
