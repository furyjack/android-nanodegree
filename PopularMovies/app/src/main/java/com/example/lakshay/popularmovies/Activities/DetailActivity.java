package com.example.lakshay.popularmovies.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.lakshay.popularmovies.Fragments.DetailFragment;
import com.example.lakshay.popularmovies.R;


public class DetailActivity extends AppCompatActivity {

   Toolbar mtoolbar;
    DetailFragment Dfrag;


    private void SetToolbar() {
        mtoolbar = (Toolbar) findViewById(R.id.tb_detail);
        mtoolbar.canShowOverflowMenu();
        mtoolbar.setTitle("Movie Detail");
        setSupportActionBar(mtoolbar);
        if(getSupportActionBar()!=null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mtoolbar.setElevation(30);


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SetToolbar();
        Dfrag= (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail);
        Dfrag.setArgument(getIntent().getExtras());

    }

}