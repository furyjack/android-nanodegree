package com.example.lakshay.popularmovies.Utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCreator extends SQLiteOpenHelper

{

        public static final String TAG="LOGS";
        public static final String db_name="FAV_MOVIES";
        public static final int db_version=1;


        private static DatabaseCreator databaseCreator=null;

        public static SQLiteDatabase openReadableDatabse(Context C)
        {
            if(databaseCreator==null)
            {
                databaseCreator=new DatabaseCreator(C);
            }
            return databaseCreator.getReadableDatabase();


        }
        public static SQLiteDatabase openWriteableDatabse(Context C)
        {
            if(databaseCreator==null)
            {
                databaseCreator=new DatabaseCreator(C);
            }
            return databaseCreator.getWritableDatabase();


        }



        public DatabaseCreator(Context context) {
            super(context, db_name,null, db_version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MovieContract.TABLE_CREATE_CMD);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


}
