package com.publisnet.leyesconcejoscomunales.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.publisnet.leyesconcejoscomunales.database.Articulo;
import com.publisnet.leyesconcejoscomunales.fragment.ArticuloFragment;

import io.realm.RealmResults;

/**
 * Created by mariano on 03/01/17.
 */

public class ArticuloSectionPagerAdapter extends FragmentStatePagerAdapter {

    private final RealmResults<Articulo> mArticulos;

    public ArticuloSectionPagerAdapter(FragmentManager fm, RealmResults<Articulo> articulos ) {
        super(fm);
        mArticulos = articulos;

    }

    @Override
    public Fragment getItem(int position) {
        return ArticuloFragment.newInstance(mArticulos.get(position).id);
    }

    @Override
    public int getCount() {
        return mArticulos.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mArticulos.get(position).getTitulo();
    }
}
