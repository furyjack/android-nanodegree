package com.example.lakshay.popularmovies.Fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.lakshay.popularmovies.Utils.DatabaseCreator;
import com.example.lakshay.popularmovies.Utils.MovieContract;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DetailFragment extends Fragment {

    private  static class Review
    {
        String author,review;

        public Review(String author, String review) {
            this.author = author;
            this.review = review;
        }
    }


    TextView TvTitle, Tvplot, Tvdate, Tvrating;
    String id;
    ImageView IvPoster;
    private static final String trailerreqp="https://api.themoviedb.org/3/movie/";
    private static final String trailerreqs="/videos?api_key=a444d7a7a662a5a702515b3735ee4f49&language=en-US";
    private static final String reviews="/reviews?api_key=a444d7a7a662a5a702515b3735ee4f49&language=en-US";
    String Title, plot, posterpath, date, rating;
    Toolbar mtoolbar;
    RequestQueue queue;
    ArrayList<String> trailer_links;
    ArrayList<Review> Reviews;
    CheckBox Cbfav;
    LinearLayout rootview;
    View base;
    Bundle extra=null;
    onAttachListner listner=null;

    public void setAttachListner(onAttachListner listner)
    {
        this.listner=listner;

    }

    public void setArgument(Bundle args)
    {

        extra=args;
        setView();

        if(extra!=null) {
            final StringRequest request = new StringRequest(Request.Method.GET, trailerreqp + id + trailerreqs, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject file = new JSONObject(response);
                        JSONArray results = file.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject obj = results.getJSONObject(i);
                            String type = obj.getString("type");
                            if (type.equals("Trailer") || type.equals("trailer")) {
                                String key = obj.getString("key");
                                trailer_links.add("https://www.youtube.com/watch?v=" + key);
                            }
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Json error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    for (int i = 1; i < trailer_links.size() + 1; i++) {


                        View view = inflater.inflate(R.layout.trailerlistitem, null);
                        TextView t = (TextView) view.findViewById(R.id.tv_trailerno);
                        String s = "Trailer " + i;
                        final int finalI = i;
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailer_links.get(finalI - 1))));
                            }
                        });
                        t.setText(s);
                        rootview.addView(view);

                    }

                    rootview.addView(inflater.inflate(R.layout.reviewtext,null));


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getContext(), "volley error", Toast.LENGTH_SHORT).show();

                }
            });

            final StringRequest rev_req = new StringRequest(Request.Method.GET, trailerreqp + id + reviews, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject file = new JSONObject(response);
                        JSONArray results = file.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject obj = results.getJSONObject(i);
                            String author = obj.getString("author");
                            String revi=obj.getString("content");
                            Reviews.add(new Review(author,revi));


                        }

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Json error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    for (int i = 1; i < Reviews.size() + 1; i++) {


                        View view = inflater.inflate(R.layout.reviewlistitem, null);
                        TextView author = (TextView) view.findViewById(R.id.tv_rev_name);
                        TextView Tvrev=(TextView)view.findViewById(R.id.tv_rev_text);

                        author.setText(Reviews.get(i-1).author+":");
                        Tvrev.setText(Reviews.get(i-1).review);
                        rootview.addView(view);

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getContext(), "volley error", Toast.LENGTH_SHORT).show();

                }
            });


            queue.add(request);
            queue.add(rev_req);


        }

    }


    private void setView() {

        queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        trailer_links=new ArrayList<>();
        Reviews=new ArrayList<>();
        rootview=(LinearLayout)base.findViewById(R.id.activity_detail);
        TvTitle = (TextView)base.findViewById(R.id.tv_title);
        Tvplot = (TextView) base.findViewById(R.id.tv_plot);
        Tvdate = (TextView) base.findViewById(R.id.tv_year);
        Tvrating = (TextView) base.findViewById(R.id.tv_rating);
        IvPoster = (ImageView) base.findViewById(R.id.im_de_pos);
        Cbfav=(CheckBox)base.findViewById(R.id.cb_fav);
        if(extra!=null) {
            Movie obj =extra.getParcelable("object");
            Title = obj.getTitle();
            id = obj.getMovie_id();
            plot = obj.getPlot();
            posterpath = obj.getimagepath();
            date = obj.getRelease_date().substring(0, 4);
            rating = obj.getRating() + "/10.0";
            TvTitle.setText(Title);
            Tvplot.setText(plot);
            Tvdate.setText(date);
            Tvrating.setText(rating);
            int ind = posterpath.indexOf("w");
            String prec = posterpath.substring(0, ind);
            prec = prec + "w320";
            posterpath = prec + posterpath.substring(ind + 4);
            Picasso.with(getContext()).load(posterpath).fit().into(IvPoster);
            SQLiteDatabase db= DatabaseCreator.openReadableDatabse(getContext());
            String projection[]={MovieContract.MovieTable.COLUMN_ID};
            String selection = MovieContract.MovieTable.COLUMN_ID + " = ?";
            String selection_Args[]={id};

            Cursor c=db.query(MovieContract.MovieTable.Movie_Table_Name,projection,selection,selection_Args,null,null,null);
            if(c.getCount()!=0)
                Cbfav.setChecked(true);
            c.close();

            Cbfav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    SQLiteDatabase db= DatabaseCreator.openReadableDatabse(getContext());
                    String projection[]={MovieContract.MovieTable.COLUMN_ID};
                    String selection = MovieContract.MovieTable.COLUMN_ID + " = ?";
                    String selection_Args[]={id};

                    Cursor c=db.query(MovieContract.MovieTable.Movie_Table_Name,projection,selection,selection_Args,null,null,null);

                    if(isChecked)
                    {

                        if(c.getCount()==0)
                        {
                            c.close();
                            SQLiteDatabase dbw=DatabaseCreator.openWriteableDatabse(getContext());
                            ContentValues values=new ContentValues();
                            values.put(MovieContract.MovieTable.COLUMN_ID,id);
                            values.put(MovieContract.MovieTable.COLUMN_NAME,Title);
                            values.put(MovieContract.MovieTable.COLUMN_DATE,date);
                            values.put(MovieContract.MovieTable.COLUMN_PICURL,posterpath);
                            values.put(MovieContract.MovieTable.COLUMN_PLOT,plot);
                            String r=rating.substring(0,rating.indexOf("/"));
                            values.put(MovieContract.MovieTable.COLUMN_RATING,r);
                            values.put(MovieContract.MovieTable.COLUMN_ISFAV,1);

                            dbw.insert(MovieContract.MovieTable.Movie_Table_Name,null,values);
                            Toast.makeText(getContext(), "database insert success", Toast.LENGTH_SHORT).show();


                        }

                        //add movie to fav if not yet added

                    }
                    else
                    {
                        if(c.getCount()!=0)
                        {

                            SQLiteDatabase dbw=DatabaseCreator.openWriteableDatabse(getContext());
                            dbw.delete(MovieContract.MovieTable.Movie_Table_Name,selection,selection_Args);
                            Toast.makeText(getContext(), "database delete success", Toast.LENGTH_SHORT).show();



                        }

                        //delete movie from database if already present

                    }

                }
            });


        }
        else
        {
            Tvplot.setText("Select an item from the list");
        }

    }
    
    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        base=inflater.inflate(R.layout.fragment_detail,container,false);
        if(listner!=null)
        listner.onattach();
        if(savedInstanceState!=null)
        {
            this.setArgument(savedInstanceState.getBundle("extra"));

        }


        return base;
     
    }

    public interface onAttachListner
    {
         void onattach();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putBundle("extra",extra);
        super.onSaveInstanceState(outState);
    }

}
