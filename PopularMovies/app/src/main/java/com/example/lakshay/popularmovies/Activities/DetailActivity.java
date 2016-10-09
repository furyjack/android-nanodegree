package com.example.lakshay.popularmovies.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lakshay.popularmovies.Models.Movie;
import com.example.lakshay.popularmovies.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView TvTitle, Tvplot, Tvdate, Tvrating;
    ImageView IvPoster;
    String Title, plot, posterpath, date, rating;
    Toolbar mtoolbar;

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

        TvTitle = (TextView) findViewById(R.id.tv_title);
        Tvplot = (TextView) findViewById(R.id.tv_plot);
        Tvdate = (TextView) findViewById(R.id.tv_year);
        Tvrating = (TextView) findViewById(R.id.tv_rating);
        IvPoster = (ImageView) findViewById(R.id.im_de_pos);
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

    }
}
