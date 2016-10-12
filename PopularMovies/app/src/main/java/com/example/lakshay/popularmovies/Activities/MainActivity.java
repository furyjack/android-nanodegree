package com.example.lakshay.popularmovies.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.lakshay.popularmovies.R;

public class MainActivity extends AppCompatActivity  {


    Toolbar mtoolbar;

    private void SetToolbar() {
        mtoolbar = (Toolbar) findViewById(R.id.tl_main);
        mtoolbar.canShowOverflowMenu();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mtoolbar.setElevation(30);
        setSupportActionBar(mtoolbar);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetToolbar();

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
