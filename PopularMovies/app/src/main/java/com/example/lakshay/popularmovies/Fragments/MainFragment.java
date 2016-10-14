package com.example.lakshay.popularmovies.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.lakshay.popularmovies.Adapters.MovieAdapter;
import com.example.lakshay.popularmovies.Models.Movie;
import com.example.lakshay.popularmovies.R;
import com.example.lakshay.popularmovies.Utils.FetchMovieTask;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements FetchMovieTask.TaskFinishedListner{

    GridView Gvpos;
    MovieAdapter adapter;
   public ArrayList<Movie> mlist;
    Toolbar mtoolbar;
    ProgressBar progressBar;
    SharedPreferences sp;
    String query;
    String prev_pref = "1";
    Bundle save_state = null;
    View rootview;
    onMovieclickListner mlistner=null;
    int mposition=-1;


    public void setListner(onMovieclickListner listner)
    {
        mlistner=listner;
    }


    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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

    private void setListView() {
        Gvpos = (GridView) rootview.findViewById(R.id.gv_movpos);
        adapter = new MovieAdapter(mlist, getContext());
        Gvpos.setAdapter(adapter);
        Gvpos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               mlistner.onMovieClicked(mlist.get(position));
                mposition=position;


            }
        });

        progressBar.setVisibility(View.INVISIBLE);

    }

    private void setOldListView() {
        mlist = save_state.getParcelableArrayList("mlist");
        Gvpos = (GridView) rootview.findViewById(R.id.gv_movpos);
        adapter = new MovieAdapter(mlist,getContext());
        Gvpos.setAdapter(adapter);
        Gvpos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mlistner.onMovieClicked(mlist.get(position));
                mposition=position;

            }
        });
        progressBar.setVisibility(View.INVISIBLE);
        Gvpos.smoothScrollToPosition(mposition);


    }

    private String getPref() {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        String choice = sp.getString(this.getString(R.string.list_key), "1");

        if (choice.equals("2"))
            query = this.getString(R.string.query_top);
        else
            query = this.getString(R.string.query_pop);

        return choice;
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                getActivity().finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }




    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview= inflater.inflate(R.layout.fragment_main, container, false);
        progressBar = (ProgressBar) rootview.findViewById(R.id.pbar);
        prev_pref=getPref();
        save_state=savedInstanceState;
        if(save_state!=null)
            mposition=save_state.getInt("pos");
        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        String choice = getPref();
        progressBar.setVisibility(View.VISIBLE);
        save_state = getActivity().getIntent().getExtras();
        if (choice.equals(prev_pref) && save_state != null && save_state.containsKey("mlist")) {
            setOldListView();
            prev_pref = choice;
            return;
        }
        if (isNetworkAvailable() && isOnline()) {
            new FetchMovieTask(this).execute(query);

            prev_pref = choice;
        } else {
            prev_pref = choice;
            showAlert();
        }

    }

    @Override
    public void onPause() {
        getActivity().getIntent().putExtra("mlist", mlist);
        super.onPause();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        save_state=savedInstanceState;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("mlist",mlist);
        outState.putInt("pos",mposition);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void processFinish(ArrayList<Movie> output) {
        mlist=output;
        setListView();

    }

   public interface onMovieclickListner
    {
        void onMovieClicked(Movie obj);

    }
}
