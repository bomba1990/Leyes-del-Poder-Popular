package com.publisnet.leyesconcejoscomunales.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.publisnet.leyesconcejoscomunales.R;
import com.publisnet.leyesconcejoscomunales.activity.RealmBaseFragment;
import com.publisnet.leyesconcejoscomunales.adapter.LeyesViewAdapter;
import com.publisnet.leyesconcejoscomunales.database.Ley;
import com.publisnet.leyesconcejoscomunales.ui.DividerItemDecoration;

import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HomeFragment extends RealmBaseFragment {
    private OnFragmentInteractionListener mListener;
    private View view;
    private RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.lista_leyes);
        RealmResults<Ley> lista = realm.where(Ley.class).findAll().sort("pos");
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        final LeyesViewAdapter adapter = new LeyesViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(new LeyesViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mListener != null) {
                    mListener.onLeySelected(adapter.getItem(position).id);
                }

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.app_name);
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
    public interface OnFragmentInteractionListener {
        void onLeySelected(int id);
    }
}
