package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class create extends AppCompatActivity
{
    EditText ed1,ed2;
    SQLiteDatabase db1;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ed1=(EditText) findViewById(R.id.ed1);
        ed2=(EditText) findViewById(R.id.ed2);
        db obj_database=new db(this);
        db1=obj_database.getWritableDatabase();
    }
    public void save(View view)
    {
        String title,note;
        title=ed1.getText().toString();
        note=ed2.getText().toString();
        if (title.isEmpty() || title.isBlank())
        {
            ed1.requestFocus();
            ed1.setError("Title cannot be empty.");
        }
        else if(doesTitleExist(title))
        {
            ed1.requestFocus();
            ed1.setError("This title is already in use.");
        }
        else
        {
            db obj_database=new db(this);

            ContentValues vl=new ContentValues();
            vl.put("Title",title);
            vl.put("Content",note);

            id=db1.insert("Notes",null,vl);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_LONG).show();
            obj_database.close();
            Intent i=new Intent(this,home.class);
            startActivity(i);
        }
    }
    public boolean doesTitleExist(String title)
    {
        db obj_database=new db(this);
        SQLiteDatabase db = obj_database.getReadableDatabase();

        Window window=this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        String query = "SELECT * FROM Notes WHERE Title = ?";
        Cursor cursor = db.rawQuery(query, new String[] { title });


        boolean exists = (cursor.getCount() > 0);

        cursor.close();
        //db.close();

        return exists;
    }
}