package com.example.lakshay.popularmovies.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lakshay.popularmovies.Models.Movie;
import com.example.lakshay.popularmovies.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity {

    TextView TvTitle, Tvplot, Tvdate, Tvrating;
    String id;
    ImageView IvPoster;
    private static final String trailerreqp="https://api.themoviedb.org/3/movie/";
    private static final String trailerreqs="/videos?api_key=a444d7a7a662a5a702515b3735ee4f49&language=en-US";
    String Title, plot, posterpath, date, rating;
    Toolbar mtoolbar;
    RequestQueue queue;
    ArrayList<String>trailer_links;
    CheckBox Cbfav;
    LinearLayout rootview;
    private void SetToolbar() {
        mtoolbar = (Toolbar) findViewById(R.id.tb_detail);
        mtoolbar.canShowOverflowMenu();
        mtoolbar.setTitle("Movie Detail");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mtoolbar.setElevation(30);


    }


    private void setView() {

        queue= Volley.newRequestQueue(this);
        trailer_links=new ArrayList<>();
        rootview=(LinearLayout)findViewById(R.id.activity_detail);
        TvTitle = (TextView) findViewById(R.id.tv_title);
        Tvplot = (TextView) findViewById(R.id.tv_plot);
        Tvdate = (TextView) findViewById(R.id.tv_year);
        Tvrating = (TextView) findViewById(R.id.tv_rating);
        IvPoster = (ImageView) findViewById(R.id.im_de_pos);
        Cbfav=(CheckBox)findViewById(R.id.cb_fav);
        Movie obj = getIntent().getParcelableExtra("object");
        Title = obj.getTitle();
        id=obj.getMovie_id();
        plot = obj.getPlot();
        posterpath = obj.getimagepath();
        date = obj.getRelease_date().substring(0, 4);
        rating = obj.getRating() + "/10.0";
        TvTitle.setText(Title);
        Tvplot.setText(plot);
        Tvdate.setText(date);
        Tvrating.setText(rating);
        int ind=posterpath.indexOf("w");
        String prec=posterpath.substring(0,ind);
        prec=prec+"w320";
        posterpath=prec+posterpath.substring(ind+4);
        Picasso.with(this).load(posterpath).fit().into(IvPoster);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SetToolbar();
        setView();
        Cbfav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(DetailActivity.this, ""+isChecked, Toast.LENGTH_SHORT).show();
            }
        });


        final StringRequest request=new StringRequest(Request.Method.GET, trailerreqp + id + trailerreqs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject file=new JSONObject(response);
                    JSONArray results=file.getJSONArray("results");
                    for(int i=0;i<results.length();i++)
                    {
                        JSONObject obj=results.getJSONObject(i);
                        String type=obj.getString("type");
                        if(type.equals("Trailer") || type.equals("trailer"))
                        {
                            String key=obj.getString("key");
                            trailer_links.add("https://www.youtube.com/watch?v="+key);
                        }
                    }

                } catch (JSONException e) {
                    Toast.makeText(DetailActivity.this, "Json error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                LayoutInflater inflater=getLayoutInflater();
             for(int i=1;i<trailer_links.size()+1;i++) {


            View view=inflater.inflate(R.layout.trailerlistitem,null);
            TextView t= (TextView) view.findViewById(R.id.tv_trailerno);
            String s="Trailer " + i;
                 final int finalI = i;
                 view.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailer_links.get(finalI -1))));
                     }
                 });
            t.setText(s);
            rootview.addView(view);

        }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(DetailActivity.this, "volley error", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(request);


    }
}
