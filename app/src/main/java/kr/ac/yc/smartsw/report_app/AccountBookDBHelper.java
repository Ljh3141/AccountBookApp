package kr.ac.yc.smartsw.report_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountBookDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "accountBook";
    public static final int DATABASE_VERSION = 1;

    public AccountBookDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataContract.mainEntry.SQL_CREATE_TABLE);
        db.execSQL(DataContract.updateEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataContract.mainEntry.SQL_DELETE_TABLE);
        db.execSQL(DataContract.updateEntry.SQL_DELETE_TABLE);
        onCreate(db);
    }

    void insertOnMainData(LogItem item){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(DataContract.mainEntry.COLUMN_USER, item.getUser());
        values.put(DataContract.mainEntry.COLUMN_DESCRIPTION, item.getDescription());
        values.put(DataContract.mainEntry.COLUMN_MONEY,item.getMoney());
        values.put(DataContract.mainEntry.COLUMN_KIND, item.getKind());
        values.put(DataContract.mainEntry.COLUMN_DATE, item.getUseDate());

        db.insert(DataContract.mainEntry.TABLE_NAME,null,values);

    }
    void insertOnUpdateData(UpdateItem item){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataContract.updateEntry.COLUMN_USER, item.getUser());
        values.put(DataContract.updateEntry.COLUMN_DESCRIPTION, item.getDescription());
        values.put(DataContract.updateEntry.COLUMN_DATE, item.getDate());

        db.insert(DataContract.updateEntry.TABLE_NAME, null, values);
    }

    public Cursor getMainData() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from "+ DataContract.mainEntry.TABLE_NAME +" order by dataNum DESC";
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }

    public Cursor getReadableUpdateData(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from "+ DataContract.updateEntry.TABLE_NAME+" order by dataNum DESC";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public void updateMainData(LogItem item){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE "+ DataContract.mainEntry.TABLE_NAME+" set "+
                DataContract.mainEntry.COLUMN_USER+" = '" +item.getUser()+"', "+
                DataContract.mainEntry.COLUMN_DESCRIPTION+" = '" +item.getDescription()+"', "+
                DataContract.mainEntry.COLUMN_MONEY+" = "+item.getMoney()+", "+
                DataContract.mainEntry.COLUMN_KIND+" = "+item.getKind()+" "+
                "where "+ DataContract.mainEntry.COLUMN_DATANUM+" = "+ item.getNum();
        db.execSQL(query);
        db.close();
    }

    public void deleteMainData(int index){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM "+ DataContract.mainEntry.TABLE_NAME+ " WHERE dataNum = "+String.format("%d", index);
        db.execSQL(query);
        db.close();
    }

    public void dropAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DataContract.updateEntry.SQL_DELETE_TABLE);
        db.execSQL(DataContract.mainEntry.SQL_DELETE_TABLE);
        onCreate(db);
        db.close();
    }
}
