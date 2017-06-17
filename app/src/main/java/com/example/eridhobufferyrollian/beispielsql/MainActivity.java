package com.example.eridhobufferyrollian.beispielsql;



/**
 * Created by eridhobufferyrollian on 15.06.17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;


public class MainActivity extends AppCompatActivity {



    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private DateiMemoDbSource dataSource;



    //----------------  Man verbindet von hier zu der DBSource  -----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //------------------------------------Test Dummies------------------------------------
        DateiMemo testMemo = new DateiMemo("Wittman", "muster123", 1);
        Log.d(LOG_TAG, "Inhalt der Testmemo: " + testMemo.toString());
        //------------------------------------Test Dummies------------------------------------



        dataSource = new DateiMemoDbSource(this);

        //anfangen, um zu verbinden
        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();


        DateiMemo DateiMemo = dataSource.createDateiMemo("TestUser", "123123", 2);
        Log.d(LOG_TAG, "Es wurde der folgende Eintrag in die Datenbank geschrieben:");
        Log.d(LOG_TAG, "ID: " + DateiMemo.getNid() + ", Inhalt: " + DateiMemo.toString());

        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");
        showAllListEntries();

        //schliessen
        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }
    //----------------------------------------------------------------------------------------------
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }


    private void showAllListEntries () {
        List<DateiMemo> DateiMemoList = dataSource.getAllDateiMemos();

        ArrayAdapter<DateiMemo> DateiMemoArrayAdapter = new ArrayAdapter<> (
                this,
                android.R.layout.simple_list_item_multiple_choice,
                DateiMemoList);

        ListView DateiMemosListView = (ListView) findViewById(R.id.listview_shopping_memos);
        DateiMemosListView.setAdapter(DateiMemoArrayAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}