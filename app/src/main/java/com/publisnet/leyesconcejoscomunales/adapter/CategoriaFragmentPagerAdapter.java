package com.publisnet.leyesconcejoscomunales.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.publisnet.leyesconcejoscomunales.database.Categoria;
import com.publisnet.leyesconcejoscomunales.fragment.CapituloFragment;

import io.realm.RealmResults;

/**
 * Created by mariano on 30/10/16.
 */

public class CategoriaFragmentPagerAdapter extends FragmentPagerAdapter {
    private final RealmResults<Categoria> mCapitulos;
    private Context mContext;
    public CategoriaFragmentPagerAdapter(FragmentManager fm, Context context, RealmResults<Categoria> capitulos) {
        super(fm);
        mContext = context;
        mCapitulos = capitulos;
    }

    @Override
    public Fragment getItem(int position) {
        return CapituloFragment.newInstance(mCapitulos.get(position).id);

    }

    @Override
    public int getCount() {
        return mCapitulos.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return mCapitulos.get(position).name;
    }
}
