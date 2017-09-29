package com.publisnet.leyesconcejoscomunales.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.publisnet.leyesconcejoscomunales.R;
import com.publisnet.leyesconcejoscomunales.activity.RealmBaseFragment;
import com.publisnet.leyesconcejoscomunales.adapter.SimpleAdapter;
import com.publisnet.leyesconcejoscomunales.adapter.SimpleSectionedRecyclerViewAdapter;
import com.publisnet.leyesconcejoscomunales.database.Articulo;
import com.publisnet.leyesconcejoscomunales.database.Categoria;
import com.publisnet.leyesconcejoscomunales.ui.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class CapituloFragment extends RealmBaseFragment {
    public Categoria capitulo;
    private Context context;
    public static final String ARG_SECTION_NUMBER = "1";
    private int sectionNumber;
    RecyclerView mRecyclerView;


    public static CapituloFragment newInstance(int param1) {
        CapituloFragment fragment = new CapituloFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public CapituloFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        if (getArguments() != null) {
            sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        SimpleAdapter mAdapter = new SimpleAdapter(context,null, capitulo.ley);

        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();


        for(Articulo a : capitulo.articulos){
            mAdapter.add(a,-1);
        }
        for( Categoria cat : capitulo.categorias){ //realm.where(Categoria.class).findAll()(sectionNumber)){
            sections.add(new SimpleSectionedRecyclerViewAdapter.Section(mAdapter.getItemCount(),cat.name,cat.descripcion));
            List<Articulo> articulos = cat.articulos;
            for(Articulo a : articulos){
                mAdapter.add(a,-1);
            }
        }


        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(context,R.layout.section,R.id.section_text,mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mSectionedAdapter);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_capitulo, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.listacapitulos);

        //Log.i(this.toString(),String.valueOf(sectionNumber));

        capitulo = realm.where(Categoria.class).equalTo("id",sectionNumber).findFirst();
        if(capitulo.descripcion.trim().length() > 1)
            ((TextView)rootView.findViewById(R.id.titulo)).setText(capitulo.descripcion );
        else
            ((TextView)rootView.findViewById(R.id.titulo)).setVisibility(View.GONE);

        return rootView;
    }

}

