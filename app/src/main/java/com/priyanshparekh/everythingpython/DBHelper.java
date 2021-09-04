package com.priyanshparekh.everythingpython;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static String DB_NAME = "ProgramsDB.db";
    private SQLiteDatabase myDB;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private final Context context;

    public static final String PROGRAM_LIST = "Programs";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = context.getDatabasePath(DB_NAME).toString();
    }

    public void createDataBase() throws IOException {
        boolean dbExists = checkDataBase();

        if (dbExists) {

        }
        else {
            this.getWritableDatabase();
            try {
                copyDataBase();
            }
            catch (IOException e) {
                throw new Error("Error copying database!");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.e("message", "" + e);
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException{
        String myPath = DB_PATH;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (myDB != null) {
            myDB.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Program> getProgramList() {
        sqLiteOpenHelper = new DBHelper(context);
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        List<Program> list = new ArrayList<>();

        String query = "SELECT * FROM " + PROGRAM_LIST;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Program program = new Program();
                program.setId(Integer.parseInt(cursor.getString(0)));
                program.setProgramQuestion(cursor.getString(1));
                program.setProgram(cursor.getString(2));
                list.add(program);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
