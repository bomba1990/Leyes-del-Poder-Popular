package com.publisnet.leyesconcejoscomunales.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.publisnet.leyesconcejoscomunales.R;
import com.publisnet.leyesconcejoscomunales.activity.ArticuloActivity;
import com.publisnet.leyesconcejoscomunales.database.Articulo;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<Articulo> mData;
    private int lastPosition = -1;

    private int mLeyId;

    public void add(Articulo s,int position) {
        position = position == -1 ? getItemCount()  : position;
        mData.add(position,s);
        notifyItemInserted(position);

    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView description;
        public final RelativeLayout container;

        public SimpleViewHolder(View view) {
            super(view);
            container = (RelativeLayout) view.findViewById(R.id.simple_item_container);
            title = (TextView) view.findViewById(R.id.articulo_text);
            description = (TextView) view.findViewById(R.id.articulo_description);
        }
    }

    public SimpleAdapter(Context context, List<Articulo> data, int ley) {
        mContext = context;
        if (data != null)
            mData = data;
        else mData = new ArrayList<Articulo>();
        mLeyId = ley;
    }


    @Override
    public SimpleAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_item, null);

        // create ViewHolder

        SimpleViewHolder viewHolder = new SimpleViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.title.setText(mData.get(position).getTitulo());
        if(mData.get(position).getDescripcion().trim().length() > 0)
            holder.description.setText(mData.get(position).getDescripcion());
        else
            holder.description.setVisibility(View.GONE);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = mData.get(position).getId();
                Intent intent = new Intent( mContext,ArticuloActivity.class);
                intent.putExtra(ArticuloActivity.ARG_ARTICULO_NUMBER, id);
                intent.putExtra(ArticuloActivity.ARG_LEY_ID, mLeyId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        setAnimation(holder.container, position);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}