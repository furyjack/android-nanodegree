package com.example.lakshay.popularmovies.Models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Movie implements Parcelable{

    private String imagepath;
    private String plot;
    private String Title;
    private String rating;
    private String release_date;
    private String movie_id;
    private ArrayList<String>trailers;
    private ArrayList<String>Reviews;

    public ArrayList<String> getReviews() {
        return Reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        Reviews = reviews;
    }

    public ArrayList<String> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<String> trailers) {
        this.trailers = trailers;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    private Movie(Parcel in)
    {
        read_parcel(in);
    }

    public Movie(String imagepath, String plot, String title, String rating, String release_date) {
        this.imagepath = imagepath;
        this.plot = plot;
        Title = title;
        this.rating = rating;
        this.release_date = release_date;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getimagepath() {
        return imagepath;
    }

    public void setimagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagepath);
        dest.writeString(plot);
        dest.writeString(Title);
        dest.writeString(rating);
        dest.writeString(release_date);
        dest.writeString(movie_id);

    }

    private void read_parcel(Parcel in)
    {

        imagepath=in.readString();
        plot=in.readString();
        Title=in.readString();
        rating=in.readString();
        release_date=in.readString();
        movie_id=in.readString();

    }
}
