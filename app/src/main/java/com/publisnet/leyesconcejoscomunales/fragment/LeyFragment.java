package com.publisnet.leyesconcejoscomunales.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.publisnet.leyesconcejoscomunales.R;
import com.publisnet.leyesconcejoscomunales.adapter.CategoriaFragmentPagerAdapter;
import com.publisnet.leyesconcejoscomunales.database.Ley;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class LeyFragment extends Fragment {
    private static final String ARG_LEY_ID = "ley_id";
    private static final String TAB_POSITION = "tab_position";

    private long mId;
    private View view;
    private CategoriaFragmentPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Realm realm;
    private Ley ley;

    public LeyFragment() {
    }
    public static LeyFragment newInstance(long id) {
        LeyFragment fragment = new LeyFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_LEY_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();

        realm = Realm.getInstance(realmConfiguration);

        if (getArguments() != null) {
            mId = getArguments().getLong(ARG_LEY_ID);
            ley = realm.where(Ley.class).equalTo("id",mId).findFirst();
            //.getActionBar().setTitle("priena");
            getActivity().setTitle(ley.titulo);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(ley != null)
            getActivity().setTitle(ley.titulo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ley, container, false);

        mSectionsPagerAdapter = new CategoriaFragmentPagerAdapter(getChildFragmentManager(), getContext(),ley.categorias.sort("pos") );

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAB_POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null)
            mViewPager.setCurrentItem(savedInstanceState.getInt(TAB_POSITION));
    }



    /*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
