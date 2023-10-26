package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class update extends AppCompatActivity
{

    SQLiteDatabase db1;
    Long id;
    String title,content,t,c;
    EditText ed1,ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Window window=this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        Intent i=getIntent();
        id=i.getLongExtra(home.sample,0);

        db obj_database=new db(this);
        db1=obj_database.getReadableDatabase();

        retrieveNote(id);

        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);

        ed1.setText(title);
        ed2.setText(content);
    }
    public void delete(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(update.this);
        builder.setTitle("Alert Dialog Title")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "OK" button click
                        db obj_database=new db(update.this);
                        db1=obj_database.getReadableDatabase();

                        String table = "Notes";

                        String selection = "ID = ?";
                        String[] selectionArgs = {String.valueOf(id)};

                        int deletedRows = db1.delete(table, selection, selectionArgs);

                        if (deletedRows > 0)
                        {
                            Toast.makeText(update.this, "Note deleted", Toast.LENGTH_LONG).show();
                            Intent i=new Intent(update.this,home.class);
                            startActivity(i);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "Cancel" button click
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
    public void save(View view)
    {
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);

        String t = ed1.getText().toString();
        String c = ed2.getText().toString();

        if (t.isEmpty() || t.isBlank())
        {
            ed1.requestFocus();
            ed1.setError("Title cannot be empty.");
        }
        else
        {
            db obj_database = new db(this);
            SQLiteDatabase db1 = obj_database.getWritableDatabase(); // Use getWritableDatabase for updates

            ContentValues values = new ContentValues();
            values.put("Title", t); // Set the new title
            values.put("Content", c); // Set the new content

            String whereClause = "ID=?";
            String[] whereArgs = new String[]{String.valueOf(id)}; // You should have 'id' declared and set

            int rowsUpdated = db1.update("Notes", values, whereClause, whereArgs);

            Toast.makeText(this, "Changes saved", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, home.class);
            startActivity(i);
        }
    }
    @SuppressLint("Range")
    public void retrieveNote(long id)
    {
        db obj_database=new db(this);
        db1=obj_database.getReadableDatabase();

        String table = "Notes";

        String[] columns = {"Title", "Content"};

        String selection = "ID = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db1.query(table, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst())
        {
            title = cursor.getString(cursor.getColumnIndex("Title"));
            content = cursor.getString(cursor.getColumnIndex("Content"));
        }
        cursor.close();
    }
    public void onDestroy(Bundle savedInstanceState)
    {
        db1.close();
    }
    public boolean doesTitleExist(String title)
    {
        db obj_database=new db(this);
        SQLiteDatabase db = obj_database.getReadableDatabase();


        String query = "SELECT * FROM Notes WHERE Title = ?";
        Cursor cursor = db.rawQuery(query, new String[] { title });


        boolean exists = (cursor.getCount() > 0);

        cursor.close();
        //db.close();

        return exists;
    }
}