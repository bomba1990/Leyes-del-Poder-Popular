package com.publisnet.leyesconcejoscomunales.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.publisnet.leyesconcejoscomunales.R;
import com.publisnet.leyesconcejoscomunales.adapter.ArticuloSectionPagerAdapter;
import com.publisnet.leyesconcejoscomunales.database.Articulo;
import com.publisnet.leyesconcejoscomunales.database.Ley;

import io.realm.Realm;


public class ArticuloActivity extends AppCompatActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    ArticuloSectionPagerAdapter mSectionsPagerAdapter;
    public static final String ARG_LEY_ID = "ley";
    public static final String ARG_ARTICULO_NUMBER = "art";
    public static final String ARG_TITULO_NUMBER = "titulo";
    private int leyid;
    private int artid;
    private int tituloid;
    private Articulo articulo;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private Realm realm;
    private Articulo mArticulo;
    private Ley mLey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);
        leyid = getIntent().getExtras().getInt(ARG_LEY_ID);
        artid = getIntent().getExtras().getInt(ARG_ARTICULO_NUMBER);
        tituloid = getIntent().getExtras().getInt(ARG_TITULO_NUMBER);


//        Tracker t = ((App) getApplication()).getTracker(App.TrackerName.APP_TRACKER);
//        t.setScreenName("Articulo");
//
//        // Send a screen view.
//        t.send(new HitBuilders.AppViewBuilder().build());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        realm = Realm.getDefaultInstance();

        mArticulo = realm.where(Articulo.class).equalTo("id",artid).findFirst();
        mLey = realm.where(Ley.class).equalTo("id",leyid).findFirst();
        getSupportActionBar().setTitle(mLey.titulo);

        // Set up the action bar.
        mSectionsPagerAdapter =
                new ArticuloSectionPagerAdapter(
                        getSupportFragmentManager(),realm.where(Articulo.class).equalTo("ley", leyid).findAllSorted("posicion"));

        mViewPager = (ViewPager) findViewById(R.id.pagerarticulo);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(mArticulo.posicion);
        mViewPager.setPageMargin(1);
        mViewPager.setPageMarginDrawable(R.color.colorPrimaryDark);
    }
    @Override
    protected void onStop() {
        super.onStop();
//        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/
        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    /**
     * A placeholder fragment containing a simple view.
     */


}
