package com.publisnet.leyesconcejoscomunales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.publisnet.leyesconcejoscomunales.R;
import com.publisnet.leyesconcejoscomunales.database.Ley;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


/**
 * Created by mariano on 02/10/16.
 */

public class LeyesViewAdapter extends RealmRecyclerViewAdapter<Ley, LeyesViewAdapter.MyViewHolder> {
    private final Context context;
    private ClickListener clickListener;

    public LeyesViewAdapter(Context context, OrderedRealmCollection<Ley> data) {
        super(context ,data, false);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row_ley, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Ley obj = getData().get(position);
        holder.data = obj;


        holder.title.setText(obj.titulo);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //implements View.OnLongClickListener
        public TextView title;
        public Ley data;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(),view);
        }

//        @Override
//        public boolean onLongClick(View v) {
//            activity.deleteItem(data);
//            return true;
//        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}
