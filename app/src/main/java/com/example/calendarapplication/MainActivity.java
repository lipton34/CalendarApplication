package com.example.calendarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView titleText;
    private Button prevButton, nextButton;
    private CslendarAdputer mCalenderAdapter;
    private GridView calenderGridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = findViewById(R.id.titleText);
        prevButton = findViewById(R.id.prev_btn);
        prevButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCalenderAdapter.prevMonth();
                titleText.setText(mCalenderAdapter.getTitle());
            }
        });
        nextButton = findViewById(R.id.next_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalenderAdapter.nextMonth();
                titleText.setText(mCalenderAdapter.getTitle());
            }
        });
        calenderGridView = findViewById(R.id.calendarGridView);
        mCalenderAdapter = new CslendarAdputer(this);
        calenderGridView.setAdapter(mCalenderAdapter);
        titleText.setText(mCalenderAdapter.getTitle());

        calenderGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.US);

                Intent intent = new Intent(MainActivity.this, TextwriteActivity.class);
                String strimg = format.format(mCalenderAdapter.getItem(position));
                intent.putExtra("key", strimg);
                startActivity(intent);

                SQLiteOpenHelper sqLiteOpenHelper = new MyOpenHelper(MainActivity.this);
                SQLiteDatabase database = null;
                ContentValues cv = new ContentValues();
                TextView cellTExt = findViewById(R.id.edit_cell);
                String st = "予定なし";

                try {
                    database = sqLiteOpenHelper.getWritableDatabase();
                    cv.put("DAY", strimg);
                    cv.put("MEMO",st);

                    database.insert("RSS_FEED", null, cv);
                } catch (Exception ex) {
                } finally {
                    if (database != null) {
                        database.close();
                    }
                }
            }
        });
    }
}
