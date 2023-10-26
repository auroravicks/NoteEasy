package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

public class home extends AppCompatActivity
{
    SQLiteDatabase db1;
    long ids;
    public static String sample="id";
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Window window=this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        String[] arr=createArrayOfTitles();
        lv=findViewById(R.id.lv);
        ArrayAdapter<String> a= new ArrayAdapter<>(this, R.layout.uk, R.id.tv, arr);
        lv.setAdapter(a);

        lv.setOnItemClickListener((parent, view, position, id) ->
        {
            String title=arr[position];
            ids=findIdByTitle(title);
            Intent i=new Intent(home.this,update.class);
            i.putExtra(sample,ids);
            startActivity(i);
        });
    }
    public Map<Long, String> retrieveAllNotes()
    {
        Map<Long, String> notesMap = new HashMap<>();

        // Get a readable database instance
        db obj_database=new db(this);
        db1=obj_database.getReadableDatabase();

        // Define the columns to be retrieved (ID and Title)
        String[] projection = { "ID", "Title" };

        // Query the database to retrieve all records
        Cursor cursor = db1.query("Notes", projection, null, null, null, null, null);

        // Iterate through the cursor and add data to the map
        while (cursor.moveToNext())
        {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("ID"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("Title"));

            notesMap.put(id, title);
        }

        // Close the cursor and the database
        cursor.close();
        //db.close();

        return notesMap;
    }
    public String[] createArrayOfTitles()
    {
        Map<Long, String> notesMap = retrieveAllNotes();

        // Create an array to store the titles
        String[] titlesArray = new String[notesMap.size()];

        // Extract the titles and store them in the array
        int index = 0;
        for (String title : notesMap.values())
        {
            titlesArray[index] = title;
            index++;
        }

        return titlesArray;
    }
    public long findIdByTitle(String titleToFind)
    {
        Map<Long, String> notesMap = retrieveAllNotes();

        for (Map.Entry<Long, String> entry : notesMap.entrySet())
        {
            if (entry.getValue().equals(titleToFind)) {
                return entry.getKey(); // Return the ID associated with the title
            }
        }
        return 0;
    }
    public  void create_note(View view)
    {
        Intent i=new Intent(home.this,create.class);
        startActivity(i);
    }
}