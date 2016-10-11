package com.example.lakshay.popularmovies.Utils;


import android.os.AsyncTask;
import android.util.Log;

import com.example.lakshay.popularmovies.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class FetchMovieTask extends AsyncTask<String,Void,ArrayList<Movie>>{

    private String query;
    private String json=null;
    private ArrayList<Movie> mlist=new ArrayList<>();

    private  TaskFinishedListner response=null;

    public FetchMovieTask(TaskFinishedListner response) {
        this.response = response;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String[] params) {
        query=params[0];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try
        {
            URL url=new URL(query);
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
               return null;

            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line);
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            json = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("fetchmovietask", "Error closing stream", e);
                }
            }
        }
        //json data has been downloaded


        try {
            JSONObject file=new JSONObject(json);
            JSONArray root=file.getJSONArray("results");
            for(int i=0;i<root.length();i++)
            {
                JSONObject o=root.getJSONObject(i);
                String imgp="http://image.tmdb.org/t/p/w185//"+o.getString("poster_path");
                String title=o.getString("title");
                String rdate=o.getString("release_date");
                String rating=o.getString("vote_average");
                String plot=o.getString("overview");
                String id=Integer.toString(o.getInt("id"));

                Movie obj= new Movie(imgp,plot,title,rating,rdate);
                obj.setMovie_id(id);
                mlist.add(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




        return mlist;
    }

    public interface TaskFinishedListner {
        void processFinish(ArrayList<Movie> output);
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        response.processFinish(movies);
        super.onPostExecute(movies);

    }
}
