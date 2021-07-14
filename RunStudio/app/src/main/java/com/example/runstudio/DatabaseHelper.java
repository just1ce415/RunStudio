package com.roflanRun.CulComf;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

class DatabaseHelper extends SQLiteOpenHelper implements DatabaseHelperI{
    private static String DB_PATH = "/data/data/" + SavedData.APP_PACKEGE + "/databases/";
    private static String DB_NAME = "database.db";
    private static final int SCHEMA = 1;
    static final String TABLE_NAME = "table_boots";
    // названия столбцов
    static private final String COLUMN_ID = "_id";
    static private final String COLUMN_PRONATION = "pronation";
    static private final String COLUMN_RW = "rw";
    static private final String COLUMN_NAME = "name";
    static private final String COLUMN_GENDER = "gender";
    static private final String COLUMN_PURPOSE = "purpose";
    static private final String COLUMN_WEIGHT = "Weight";
    static private final String COLUMN_DESCRIPTION = "Desriptoin";
    static private final String COLUMN_IMAGES = "images";
    private SQLiteDatabase mDataBase;
    private Context myContext;

    DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, SCHEMA);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
    }

    public void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();

        if(!dbExist){
            this.getReadableDatabase();

            try {
                copyDataBase();
            }catch (IOException e){
                throw new Error("Error copying data base");
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e){

        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutPut = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutPut.write(buffer, 0, length);
        }

        myOutPut.flush();
        myOutPut.close();
        myInput.close();
    }

    public void openDataBase () throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public synchronized void close() {
        if (mDataBase != null){
            mDataBase.close();
        }
        super.close();
    }


    @Override
    public Boot getBoot(int id) {

        Cursor cursor = mDataBase.query(TABLE_NAME, new String[] { COLUMN_ID,
                        COLUMN_GENDER, COLUMN_NAME, COLUMN_PRONATION, COLUMN_RW, COLUMN_PURPOSE, COLUMN_WEIGHT, COLUMN_DESCRIPTION, COLUMN_IMAGES}, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        return new Boot(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                 Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)),
                cursor.getString(5),
                Float.parseFloat(cursor.getString(6)),
                cursor.getString(7), cursor.getString(8));
    }

    @Override
    public List<Boot> getAllBoots() {
        List<Boot> bootList = new ArrayList<Boot>();
        String selectQuery = "SELECT  * FROM "  + TABLE_NAME;

        Cursor cursor = mDataBase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Boot boot = new Boot();
                boot.set_id(Integer.parseInt(cursor.getString(0)));
                boot.setName(cursor.getString(1));
                boot.setGender(cursor.getString(2));
                boot.setPronation(Integer.parseInt(cursor.getString(3)));
                boot.setRw(Integer.parseInt(cursor.getString(4)));
                boot.setPurpose(cursor.getString(5));
                boot.setWeight(Float.parseFloat(cursor.getString(6)));
                boot.setDescription(cursor.getString(7));
                boot.setImages(cursor.getString(8));

                bootList.add(boot);
            } while (cursor.moveToNext());
        }
        return bootList;
    }

    @Override
    public int getBootsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = mDataBase.rawQuery(countQuery, null);
        int c = cursor.getCount();
        cursor.close();

        return c;
    }
}