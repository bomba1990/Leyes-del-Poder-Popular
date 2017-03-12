package com.publisnet.leyesconcejoscomunales.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.publisnet.leyesconcejoscomunales.R;
import com.publisnet.leyesconcejoscomunales.database.Articulo;
import com.publisnet.leyesconcejoscomunales.ui.MyTagHandler;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mariano on 03/01/17.
 */

public class ArticuloFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    Articulo articulo;
    int artid;
    private Realm realm;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ArticuloFragment newInstance(int sectionNumber) {
        ArticuloFragment fragment = new ArticuloFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ArticuloFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Log.i("test",html);
        html = html.replaceAll("(\\\\r?\\\\n)+","<br>");
        Log.i("test2",html);
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY,null,new MyTagHandler());
        } else {
            result = Html.fromHtml(html, null, new MyTagHandler());
        }
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_articulo, container, false);

        artid = getArguments().getInt(ARG_SECTION_NUMBER);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();

        realm = Realm.getInstance(realmConfiguration);
        articulo = realm.where(Articulo.class).equalTo("id",artid).findFirst();//.getArticulo(artid+1,"id");

        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName(articulo.getTitulocorto())
                .putContentType("Articulo")
                .putContentId(String.valueOf(articulo.getId())));

        ((TextView)rootView.findViewById(R.id.arttitle)).setText(articulo.getTitulo());
        ((TextView)rootView.findViewById(R.id.artdescription)).setText(articulo.getDescripcion());

        Spanned spanned = fromHtml(articulo.getContenido());
        TextView art = (TextView)rootView.findViewById(R.id.artcontent);
        art.setText(spanned);
        art.setTextIsSelectable(true);
        return rootView;
    }
}