package com.example.lakshay.popularmovies.Activities;

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

import com.example.lakshay.popularmovies.Models.Movie;
import com.example.lakshay.popularmovies.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView TvTitle, Tvplot, Tvdate, Tvrating;
    ImageView IvPoster;
    String Title, plot, posterpath, date, rating;
    Toolbar mtoolbar;
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

        rootview=(LinearLayout)findViewById(R.id.activity_detail);
        TvTitle = (TextView) findViewById(R.id.tv_title);
        Tvplot = (TextView) findViewById(R.id.tv_plot);
        Tvdate = (TextView) findViewById(R.id.tv_year);
        Tvrating = (TextView) findViewById(R.id.tv_rating);
        IvPoster = (ImageView) findViewById(R.id.im_de_pos);
        Cbfav=(CheckBox)findViewById(R.id.cb_fav);
        Movie obj = getIntent().getParcelableExtra("object");
        Title = obj.getTitle();
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

        LayoutInflater inflater=getLayoutInflater();
        for(int i=1;i<4;i++) {


            View view=inflater.inflate(R.layout.trailerlistitem,null);
            TextView t= (TextView) view.findViewById(R.id.tv_trailerno);
            String s="Trailer " + i;
            t.setText(s);
            rootview.addView(view);

        }

        


       // rootview.addView(trailer_list);



    }
}
