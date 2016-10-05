package com.example.lakshay.popularmovies.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.lakshay.popularmovies.Models.Movie;
import com.example.lakshay.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter{

   private ArrayList<Movie> mlist;
    private Context mcontext;

    public MovieAdapter(ArrayList<Movie> list,Context c) {
        mlist=list;
        mcontext=c;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mcontext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
        convertView=inflater.inflate(R.layout.list_item_view,parent,false);

        ImageView impos=(ImageView)convertView.findViewById(R.id.Immov_pos);
        Picasso.with(mcontext).load(mlist.get(position).getimagepath()).into(impos);


        return convertView;
    }
}
