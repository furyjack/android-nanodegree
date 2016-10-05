package com.example.lakshay.popularmovies.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.lakshay.popularmovies.Adapters.MovieAdapter;
import com.example.lakshay.popularmovies.Models.Movie;
import com.example.lakshay.popularmovies.R;
import com.example.lakshay.popularmovies.Utils.FetchMovieTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    GridView Gvpos;
    MovieAdapter adapter;
    ArrayList<Movie> mlist;
    Toolbar mtoolbar;
    ProgressBar progressBar;
    SharedPreferences sp;
    String query;
    String prev_pref = "1";
    Bundle save_state = null;

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public Boolean isOnline() {
        try {

            final Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");

            int returnVal = p1.waitFor();
            return (returnVal == 0);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    private void SetToolbar() {
        mtoolbar = (Toolbar) findViewById(R.id.tl_main);
        mtoolbar.canShowOverflowMenu();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mtoolbar.setElevation(30);
        setSupportActionBar(mtoolbar);

    }

    private void setListView() {
        Gvpos = (GridView) findViewById(R.id.gv_movpos);
        progressBar = (ProgressBar) findViewById(R.id.pbar);
        Gvpos.setVisibility(View.INVISIBLE);
        try {
            mlist = new FetchMovieTask().execute(query).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Gvpos.setVisibility(View.VISIBLE);
        adapter = new MovieAdapter(mlist, getApplicationContext());
        Gvpos.setAdapter(adapter);
        Gvpos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra("object", mlist.get(position));
                startActivity(i);


            }
        });
        progressBar.setVisibility(View.INVISIBLE);


    }

    private void setOldListView() {
        mlist = save_state.getParcelableArrayList("mlist");
        Gvpos = (GridView) findViewById(R.id.gv_movpos);
        adapter = new MovieAdapter(mlist, getApplicationContext());
        Gvpos.setAdapter(adapter);
        Gvpos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra("object", mlist.get(position));
                startActivity(i);


            }
        });


    }

    private String getPref() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String choice = sp.getString(this.getString(R.string.list_key), "1");

        if (choice.equals("2"))
            query = this.getString(R.string.query_top);
        else
            query = this.getString(R.string.query_pop);

        return choice;
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetToolbar();


    }



    @Override
    protected void onResume() {
        super.onResume();
        String choice = getPref();
        save_state = getIntent().getExtras();
        if (choice.equals(prev_pref) && save_state != null && save_state.containsKey("mlist")) {
            setOldListView();
            prev_pref = choice;
            return;
        }
        if (isNetworkAvailable() && isOnline()) {
            setListView();
            prev_pref = choice;
        } else {
            prev_pref = choice;
            showAlert();
        }

    }

    @Override
    protected void onPause() {
        getIntent().putExtra("mlist", mlist);
        super.onPause();

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
