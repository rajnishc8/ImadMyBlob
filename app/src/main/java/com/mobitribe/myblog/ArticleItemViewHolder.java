package com.mobitribe.myblog;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.mobitribe.myblog.R.id.recyclerView;

/**
 * Created by TEST on 9/4/2017.
 */

public class ArticleItemViewHolder extends RecyclerView.ViewHolder{

    TextView articleName;
    CardView cardview;

    public ArticleItemViewHolder(View itemView) {
        super(itemView);

        articleName = (TextView) itemView.findViewById(R.id.articleName);
        cardview = (CardView) itemView.findViewById(R.id.cardView);

    }


}
