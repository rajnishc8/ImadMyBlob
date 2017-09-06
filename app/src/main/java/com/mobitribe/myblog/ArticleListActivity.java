package com.mobitribe.myblog;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobitribe.myblog.R.id.recyclerView;

public class ArticleListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArticleListAdapter articleListAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        articleListAdapter = new ArticleListAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //Step 1 -create an adapter
        //recyclerView.setAdapter(new ArticleListAdapter());
        recyclerView.setAdapter(articleListAdapter);
        //Step 2 - set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog =  new ProgressDialog(this) ;
        progressDialog.setMessage("Fetching articles");
        progressDialog.show();

        ApiManager.getApiInterface().getArticles().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                //showProgressDialog(false);
                progressDialog.dismiss();
                if(response.isSuccessful()) {
                    articleListAdapter.setData(response.body());
                } else {
                    //show alert
                    Toast.makeText(ArticleListActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                //showProgressDialog(false);
                progressDialog.dismiss();
                Toast.makeText(ArticleListActivity.this, "Failed-2", Toast.LENGTH_LONG).show();
            }
        });

    }

    public class ArticleListAdapter extends RecyclerView.Adapter<ArticleItemViewHolder>{

        String[] articleListOld = {
                "article1",
                "article2",
                "article3",
                "article4",
                "article5",
        };
        List<Article> articleList =  new ArrayList<>();

        @Override
        public ArticleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating the view - layout_article.xml
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article, parent, false);

            return new ArticleItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleItemViewHolder holder, final int position) {
            //Setting the view
            //holder.articleName.setText(articleListOld[position]);
            holder.articleName.setText(articleList.get(position).getHeading());
            holder.cardview.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(ArticleListActivity.this, "Article Clicked : " +articleList.get(position).getHeading() , Toast.LENGTH_LONG).show();
                }
            });

        }

        @Override
        public int getItemCount() {
            //return articleListOld.length;
            return articleList.size();
        }

        public void setData(List<Article> data)
        {
            this.articleList = data;
            this.notifyDataSetChanged();
        }
    }
}
