package com.mdb.statwiz.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String FUNCTIONTYPE = "FUNCTION_TYPE";
    public static final String DISTRIBUTIONS = "distributions";
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainContentFragment fragment = new MainContentFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String functionTypeName = item.getTitle().toString();
        Fragment currentlyVisible = fragmentManager.findFragmentById(R.id.fragment_container);

        switch (id) {
            case R.id.descriptive:
                if (currentlyVisible instanceof DescriptiveInputFragment)
                    break;
                DescriptiveInputFragment descriptiveInputFragment = new DescriptiveInputFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, descriptiveInputFragment).addToBackStack("input").commit();
                break;
            case R.id.distributions:
                if (currentlyVisible instanceof FunctionViewFragment)
                    break;
                FunctionViewFragment functionViewFragment = new FunctionViewFragment();
                Bundle args = new Bundle();
                args.putString(FUNCTIONTYPE, DISTRIBUTIONS);
                functionViewFragment.setArguments(args);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, functionViewFragment).addToBackStack(DISTRIBUTIONS).commit();
                break;
            case R.id.regression:
                break;
            case R.id.probability:
                break;
            case R.id.sampling:
                if (currentlyVisible instanceof SamplingFragment)
                    break;
                SamplingFragment samplingFragment = new SamplingFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, samplingFragment).addToBackStack("sampling").commit();
                break;
            case R.id.tables:
                if (currentlyVisible instanceof TablesFragment)
                    break;
                TablesFragment tablesFragment = new TablesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, tablesFragment).addToBackStack("tables").commit();
                break;
            case R.id.reference:
                if (currentlyVisible instanceof ReferencesFragment)
                    break;
                ReferencesFragment referencesFragment = new ReferencesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, referencesFragment).addToBackStack("references").commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
