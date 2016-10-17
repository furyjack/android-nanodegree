package com.example.lakshay.popularmovies.Utils;

import android.provider.BaseColumns;

public class MovieContract  extends DBconstants{

    private MovieContract()
    {

    };




    public class MovieTable implements BaseColumns
    {

        public static final String Movie_Table_Name="FAV_MOVIES";
        public static final String COLUMN_ID="COL_ID";
        public static final String COLUMN_NAME="NAME";
        public static final String COLUMN_DATE="DATE";
        public static final String COLUMN_PICURL="PICURL";
        public static final String COLUMN_PLOT="PLOT";
        public static final String COLUMN_RATING="RATING";
        public static final String COLUMN_ISFAV="ISFAV";


    }


    public static final String TABLE_CREATE_CMD = "CREATE TABLE IF NOT EXISTS " + MovieTable.Movie_Table_Name
            + LBR + MovieTable._ID + TYPE_INT_PK_AUTO + COMMA
            + MovieTable.COLUMN_ID + TYPE_TEXT + COMMA
            + MovieTable.COLUMN_NAME + TYPE_TEXT + COMMA
            + MovieTable.COLUMN_DATE + TYPE_TEXT + COMMA
            + MovieTable.COLUMN_PICURL + TYPE_TEXT + COMMA
            + MovieTable.COLUMN_PLOT + TYPE_TEXT + COMMA
            + MovieTable.COLUMN_RATING + TYPE_TEXT + COMMA
            + MovieTable.COLUMN_ISFAV + TYPE_INT
            + RBR + ";";


}
