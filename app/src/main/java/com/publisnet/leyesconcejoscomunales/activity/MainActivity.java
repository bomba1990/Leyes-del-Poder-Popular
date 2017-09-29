package com.publisnet.leyesconcejoscomunales.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.publisnet.leyesconcejoscomunales.R;
import com.publisnet.leyesconcejoscomunales.database.Ley;
import com.publisnet.leyesconcejoscomunales.fragment.HomeFragment;
import com.publisnet.leyesconcejoscomunales.fragment.LeyFragment;

import io.realm.RealmResults;

public class MainActivity extends RealmBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener {
    private FragmentManager mFragmentManager;
    private HomeFragment homeFragment;
    private Fragment mContent;
    private int mIdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();

        homeFragment = HomeFragment.newInstance();

        restoreSaveInstance(savedInstanceState);

        if(mContent == null){
            mContent = homeFragment;
            mIdFragment = -1;
            mFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, mContent)
                    .commit();
        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();

        SubMenu submenu = menu.getItem(1).getSubMenu();

        RealmResults<Ley> lista = realm.where(Ley.class).findAll().sort("pos");

        for (Ley ley : lista) {
            submenu.add(R.id.leyes,ley.id,0,"Ley "+ley.titulocorto);
        }
    }

    private void restoreSaveInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null && mFragmentManager != null) {
            mIdFragment = savedInstanceState.getInt("mIdFragment",-1);
            if(mIdFragment > -1) {
                onLeySelected(mIdFragment);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else  {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreSaveInstance(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("mIdFragment",mIdFragment);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mContent = homeFragment;
            mFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, mContent)
                    .commit();
        } else if (id == R.id.nav_about) {
            Answers.getInstance().logCustom(new CustomEvent("About")
                    .putCustomAttribute("Category", "Share"));
            Intent intentAbout = new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(intentAbout);
        } else if (id == R.id.nav_rating) {
            Answers.getInstance().logCustom(new CustomEvent("Rating")
                    .putCustomAttribute("Category", "Share"));
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.getPackageName())));
            }catch (ActivityNotFoundException ignored){
                Toast.makeText(this, R.string.no_play_store, Toast.LENGTH_LONG).show();
            }
        }else if (id == R.id.nav_share) {
            Answers.getInstance().logCustom(new CustomEvent("Share")
                    .putCustomAttribute("Category", "Share"));

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.descarga_instala_action));
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));

        } /*else if (id == R.id.nav_send) {

        }*/else {
            onLeySelected(item.getItemId());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLeySelected(int id) {
        mContent = LeyFragment.newInstance(id);
        mIdFragment = id;
        mFragmentManager.beginTransaction()
                .replace(R.id.container_fragment, mContent)
                .addToBackStack("ley"+String.valueOf(id))
                .commit();
    }
}
