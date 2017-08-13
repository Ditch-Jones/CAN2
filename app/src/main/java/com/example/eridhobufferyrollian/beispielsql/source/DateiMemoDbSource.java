package com.example.eridhobufferyrollian.beispielsql.source;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import android.content.ContentValues;
import android.database.Cursor;

import com.example.eridhobufferyrollian.beispielsql.DateiMemoDbHelper;
import com.example.eridhobufferyrollian.beispielsql.model.DateiMemo;

import java.util.ArrayList;
import java.util.List;


public class DateiMemoDbSource {

    private static final String LOG_TAG = DateiMemoDbSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DateiMemoDbHelper dbHelper;
    private PeerDbSource peerDbSource;

    //neue Array String für Datei
    private String[] columns = {
            DateiMemoDbHelper.COLUMN_UID, //------------------------ Table Datei
            DateiMemoDbHelper.COLUMN_USERNAME,
            DateiMemoDbHelper.COLUMN_PASSWORD,
            DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHT,
            DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFT,
            DateiMemoDbHelper.COLUMN_CORNERTOPLEFT,
            DateiMemoDbHelper.COLUMN_CORNERTOPRIGHT,
            DateiMemoDbHelper.COLUMN_PUNKTX,
            DateiMemoDbHelper.COLUMN_PUNKTY,
            DateiMemoDbHelper.COLUMN_IP,
            DateiMemoDbHelper.COLUMN_COUNTPEERS,
            DateiMemoDbHelper.COLUMN_CHECKED
    };
//    //neue Array String für Peer

//    //neue Array String für Peer


    public DateiMemoDbSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new DateiMemoDbHelper(context);
    }

    //mit getWritableDatabase öffnet man die Verbindung DB
    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }


    //
    //String username, String password, long uid, boolean checked,
    // double cornerTopRight, double cornerTopLeft, double cornerBottomRight,
    // double cornerBottomLeft, double punktX, double punktY, double IP, int countPeers
    //
    //----------------------------- Insert, delete, update, get values in Table ---------------------------------
    //
    //
    public long createDateiMemo(DateiMemo dateiMemo) {
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_USERNAME, dateiMemo.getUsername());
        values.put(DateiMemoDbHelper.COLUMN_PASSWORD, dateiMemo.getPassword());
        values.put(DateiMemoDbHelper.COLUMN_UID, dateiMemo.getUid());
        values.put(DateiMemoDbHelper.COLUMN_CHECKED, dateiMemo.isChecked());
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHT, dateiMemo.getCornerBottomRight());
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFT, dateiMemo.getCornerBottomLeft());
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFT, dateiMemo.getCornerTopLeft());
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHT, dateiMemo.getCornerTopRight());
        values.put(DateiMemoDbHelper.COLUMN_PUNKTX, dateiMemo.getPunktX());
        values.put(DateiMemoDbHelper.COLUMN_PUNKTY, dateiMemo.getPunktY());
        values.put(DateiMemoDbHelper.COLUMN_IP, dateiMemo.getIP());
        values.put(DateiMemoDbHelper.COLUMN_COUNTPEERS, peerDbSource.getData());

        //
        //insert row
        //
        long data_Id = database.insert(DateiMemoDbHelper.TABLE_DATEI_LIST, null, values);

        //        Cursor cursor = database.query(DateiMemoDbHelper.TABLE_DATEI_LIST,
        //                columns, DateiMemoDbHelper.COLUMN_UID + "=" + data_Id ,
        //                null, null, null, null);
        //
        //        cursor.moveToFirst();
        //        DateiMemo DateiMemo = cursorToDateiMemo(cursor);
        //        cursor.close();

        return data_Id;
    }

    public void deleteDateiMemo(DateiMemo dateiMemo) {
        long id = dateiMemo.getUid();

        database.delete(DateiMemoDbHelper.TABLE_DATEI_LIST,
                DateiMemoDbHelper.COLUMN_UID + "=" + id,
                null);

        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id + " Inhalt: " + dateiMemo.toString());
    }

    //Wir muessen noch ueberlegen, wie machen wir die Update-Methode fur die PeerID, NachbarID und Eckpunkt
    public DateiMemo updateDateiMemo(String newUsername, String newPassword, long uid, boolean newChecked,
                                     double newCornerTopRight, double newCornerTopLeft, double newCornerBottomRight,
                                     double newCornerBottomLeft, double newPunktX, double newPunktY, double newIP, int newCountPeers) {
        int intValueChecked = (newChecked)? 1 : 0;
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_USERNAME, newUsername);
        values.put(DateiMemoDbHelper.COLUMN_PASSWORD, newPassword);
        values.put(DateiMemoDbHelper.COLUMN_CHECKED, intValueChecked);
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHT, newCornerBottomRight);
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFT, newCornerBottomLeft);
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFT, newCornerTopLeft);
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHT, newCornerTopRight);
        values.put(DateiMemoDbHelper.COLUMN_PUNKTX, newPunktX);
        values.put(DateiMemoDbHelper.COLUMN_PUNKTY, newPunktY);
        values.put(DateiMemoDbHelper.COLUMN_IP, newIP);
        values.put(DateiMemoDbHelper.COLUMN_COUNTPEERS, peerDbSource.getData());
        values.put(DateiMemoDbHelper.COLUMN_UID, uid);


        database.update(DateiMemoDbHelper.TABLE_DATEI_LIST,
                values,
                DateiMemoDbHelper.COLUMN_UID + "=" + uid,
                null);

        Cursor cursor = database.query(DateiMemoDbHelper.TABLE_DATEI_LIST,
                columns, DateiMemoDbHelper.COLUMN_UID + "=" + uid,
                null, null, null, null);

        cursor.moveToFirst();
        DateiMemo dateiMemo = cursorToDateiMemo(cursor);
        cursor.close();

        return dateiMemo;
    }

    private DateiMemo cursorToDateiMemo(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_UID);
        int idUsername = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_USERNAME);
        int idPassword = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_PASSWORD);
        int idChecked = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CHECKED);
        int idTopRight = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHT);
        int idTopLeft = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPLEFT);
        int idBottomRight = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHT);
        int idBottomLeft = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFT);
        int idPunktX = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_PUNKTX);
        int idPunktY = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_PUNKTY);
        int idIP = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_IP);
        int idCountPeers = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_COUNTPEERS);


        String username = cursor.getString(idUsername);
        String password = cursor.getString(idPassword);
        long uid = cursor.getLong(idIndex);

        int intValueChecked = cursor.getInt(idChecked);
        boolean isChecked = (intValueChecked != 0);

        double cornerTopRight = cursor.getDouble(idTopRight);
        double cornerTopLeft = cursor.getDouble(idTopLeft);
        double cornerBottomRight = cursor.getDouble(idBottomRight);
        double cornerBottomLeft = cursor.getDouble(idBottomLeft);
        double punktX = cursor.getDouble(idPunktX);
        double punktY = cursor.getDouble(idPunktY);
        double IP = cursor.getDouble(idIP);

        int countPeers = cursor.getInt(idCountPeers);


        DateiMemo DateiMemo = new DateiMemo(username, password, uid, isChecked, cornerTopRight,
                cornerTopLeft, cornerBottomRight, cornerBottomLeft, punktX, punktY, IP, countPeers);

        return DateiMemo;
    }

    public List<DateiMemo> getAllDateiMemos() {
        List<DateiMemo> DateiMemoList = new ArrayList<>();

        Cursor cursor = database.query(DateiMemoDbHelper.TABLE_DATEI_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        DateiMemo DateiMemo;

        while(!cursor.isAfterLast()) {
            DateiMemo = cursorToDateiMemo(cursor);
            DateiMemoList.add(DateiMemo);
            Log.d(LOG_TAG, "ID: " + DateiMemo.getUid() + ", Inhalt: " + DateiMemo.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return DateiMemoList;
    }
}