package com.nnn.moviee.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nnn.moviee.R;
import com.nnn.moviee.fragment.ListFragment;
import com.nnn.moviee.fragment.SettingFragment;
import com.nnn.moviee.utils.S;
import com.nnn.moviee.utils.db.Pref;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pref.loadLocale(this);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        searchItem = menu.findItem(R.id.search);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);

        SearchView searchView =(SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        selectFragment(R.id.nav_playing);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.frame);
        fragment.setQuery(s);
        S.log("QUERY SUBMIT : "+s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        selectFragment(item.getItemId());

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    void selectFragment(int id){

        Fragment fragment = null;
        String title = "Moviee";

        searchItem.setVisible(false);

        switch (id){
            case R.id.nav_playing :
                fragment = ListFragment.newInstance(ListFragment.TYPE.PLAYING);
                title = getString(R.string.nav_playing);
                break;
            case R.id.nav_upcoming :
                fragment = ListFragment.newInstance(ListFragment.TYPE.UPCOMING);
                title = getString(R.string.nav_upcoming);
                break;
            case R.id.nav_search :
                searchItem.setVisible(true);
                fragment= ListFragment.newInstance(ListFragment.TYPE.SEARCH);
                title = getString(R.string.nav_search);
                break;
            case R.id.nav_favorite :
                fragment = ListFragment.newInstance(ListFragment.TYPE.FAVORITE);
                title = "Favorite";
                break;
            case R.id.nav_settings :
                fragment = new SettingFragment();
                title = getString(R.string.nav_settings);
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            ft.commit();
            toolbar.setTitle(title);
        }
        drawer.closeDrawer(GravityCompat.START);
    }


}
