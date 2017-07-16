package com.materialdesign.ramiz.materialdesignnavdrawer;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

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

    }

    @Override
    public void onNavDrawerItemSelected(@IdRes int selectedItemId) {
        if (selectedItemId == R.id.navigation_item_remotes) {
            Toast.makeText(this, "loading remote fragment", Toast.LENGTH_SHORT).show();
            loadRemotesFragment(true);
        } else if (selectedItemId == R.id.navigation_item_servers) {
            ServerFragment serverFragment = ServerFragment.newInstance("", "");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout_content, serverFragment)
                    .addToBackStack(getString(R.string.home_servers))
                    .commit();
        }
    }

    private void loadRemotesFragment(boolean addToBackStack) {
        RemotesFragment remotesFragment = RemotesFragment.newInstance("", "");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content, remotesFragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(getString(R.string.home_remotes));
        }

        fragmentTransaction.commit();
    }
}
