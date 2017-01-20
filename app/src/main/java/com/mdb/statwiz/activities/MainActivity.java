package com.mdb.statwiz.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mdb.statwiz.R;
import com.mdb.statwiz.fragments.DescriptiveInputFragment;
import com.mdb.statwiz.fragments.FunctionViewFragment;
import com.mdb.statwiz.fragments.MainContentFragment;
import com.mdb.statwiz.fragments.ReferencesFragment;
import com.mdb.statwiz.fragments.SamplingFragment;
import com.mdb.statwiz.fragments.TablesFragment;

/**
 * App uses one activity and multiple fragment pages to optimize navigation UX and for more intuitive structure for implementing function abstractions
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String FUNCTIONTYPE = "FUNCTION_TYPE";
    public static final String DISTRIBUTIONS = "distributions";
    public static final String PROBABILITY = "probability";
    private FragmentManager fragmentManager;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainContentFragment fragment = new MainContentFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        mHandler = new Handler();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {       //implement back button interface for fragment and drawer navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int count = getFragmentManager().getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        final String functionTypeName = item.getTitle().toString();
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment targetFragment = getTargetFragment(item);
                if (targetFragment == null)
                    return;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment_container, getTargetFragment(item)).addToBackStack(functionTypeName);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        mHandler.post(mPendingRunnable);

        return true;
    }

    /**
     * Implement drawer navigation to show fragment based on element selected in drawer
     * @param item
     * @return current fragment
     */
    private Fragment getTargetFragment(MenuItem item) {
        int id = item.getItemId();
        Fragment currentlyVisible = fragmentManager.findFragmentById(R.id.fragment_container);
        Fragment targetFragment = null;
        Bundle args = new Bundle();

        switch (id) {
            case R.id.descriptive:
                if (currentlyVisible instanceof DescriptiveInputFragment)   //no need to return fragment if already on current fragment
                    break;
                targetFragment = new DescriptiveInputFragment();
                break;
            case R.id.distributions:
                if (currentlyVisible instanceof FunctionViewFragment && currentlyVisible.getArguments().getString(FUNCTIONTYPE) == DISTRIBUTIONS)
                    break;
                targetFragment = new FunctionViewFragment();
                args.putString(FUNCTIONTYPE, DISTRIBUTIONS);
                break;
            case R.id.probability:
                if (currentlyVisible instanceof FunctionViewFragment && currentlyVisible.getArguments().getString(FUNCTIONTYPE) == PROBABILITY)
                    break;
                targetFragment = new FunctionViewFragment();
                args.putString(FUNCTIONTYPE, PROBABILITY);
                break;
            case R.id.sampling:
                if (currentlyVisible instanceof SamplingFragment)
                    break;
                targetFragment = new SamplingFragment();
                break;
            case R.id.tables:
                if (currentlyVisible instanceof TablesFragment)
                    break;
                targetFragment = new TablesFragment();
                break;
            case R.id.reference:
                if (currentlyVisible instanceof ReferencesFragment)
                    break;
                targetFragment = new ReferencesFragment();
                break;
        }
        if (targetFragment != null)
            targetFragment.setArguments(args);
        return targetFragment;
    }
}
