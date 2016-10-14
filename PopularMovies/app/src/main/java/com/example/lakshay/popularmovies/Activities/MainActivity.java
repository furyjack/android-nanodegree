package com.example.lakshay.popularmovies.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.lakshay.popularmovies.Fragments.DetailFragment;
import com.example.lakshay.popularmovies.Fragments.MainFragment;
import com.example.lakshay.popularmovies.Fragments.placeholderfrag;
import com.example.lakshay.popularmovies.Models.Movie;
import com.example.lakshay.popularmovies.R;

public class MainActivity extends AppCompatActivity  {


    Toolbar mtoolbar;
    boolean mtwopane;
    MainFragment mainfrag;
    MainFragment.onMovieclickListner listner;

    private void SetToolbar() {
        mtoolbar = (Toolbar) findViewById(R.id.tl_main);
        mtoolbar.canShowOverflowMenu();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mtoolbar.setElevation(30);
        setSupportActionBar(mtoolbar);

    }



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetToolbar();
        mainfrag=(MainFragment)getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        listner=new MainFragment.onMovieclickListner() {
            @Override
            public void onMovieClicked(Movie obj) {

                if(mtwopane)
                {
                  final  DetailFragment d=new DetailFragment();
                  final  Bundle b=new Bundle();
                    d.setAttachListner(new DetailFragment.onAttachListner() {
                        @Override
                        public void onattach() {
                            d.setArgument(b);

                        }
                    });
                    b.putParcelable("object",obj);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail,d).commit();


                }
                else
                {
                    startActivity(new Intent(getApplicationContext(),DetailActivity.class).putExtra("object",obj));

                }


            }
        };
        mainfrag.setListner(listner);


        if(findViewById(R.id.fragment_detail)!=null)
        {
            mtwopane=true;


            if(savedInstanceState==null)
            {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail,new placeholderfrag()).commit();
            }



        }
        else
        {


            mtwopane=false;
            
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.setting) {
            startActivity(new Intent(this, PrefActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }



}
