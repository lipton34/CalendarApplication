package com.example.calendarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TextwriteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textwrite);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        TextView text = findViewById(R.id.edit_cell);
        text.setText(key);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int ColumnIndex;
        int Column;
        TextView cellTExt = findViewById(R.id.edit_cell);
        String day = cellTExt.getText().toString();
        String[] from = {day};
        Cursor cursor = null;
        SQLiteOpenHelper sqLiteOpenHelper = new MyOpenHelper(this);
        SQLiteDatabase database = null;
        EditText text = findViewById(R.id.edit_memo);
        try {
            database = sqLiteOpenHelper.getReadableDatabase();
            cursor = database.query("RSS_FEED", null, "DAY=?", from, null, null, null);
            cursor.moveToFirst();
            ColumnIndex = cursor.getColumnIndex("MEMO");
            String memo = cursor.getString(ColumnIndex);
            text.setText(memo);
        } catch (Exception ex) {
            Log.i("error", ex.getLocalizedMessage());
            Log.d("Count", String.valueOf(cursor.getCount()));

        } finally {
            if (database != null) {
                database.close();
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        final Button saveBtn = findViewById(R.id.save_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cellTExt = findViewById(R.id.edit_cell);
                String day = cellTExt.getText().toString();
                String[] from = {day};
                ContentValues cv = new ContentValues();
                SQLiteOpenHelper sqLiteOpenHelper = new MyOpenHelper(TextwriteActivity.this);
                SQLiteDatabase database = null;
                EditText editText = findViewById(R.id.edit_memo);
                String st = editText.getText().toString();
                try {
                    database = sqLiteOpenHelper.getWritableDatabase();
                    cv.put("MEMO", st);
                    database.update("RSS_FEED",cv,"DAY=?",from);
                } catch (Exception ex) {
                    Log.i("error3", ex.getLocalizedMessage());
                } finally {
                    database.close();
                    finish();
                }
            }
        });
    }
}
