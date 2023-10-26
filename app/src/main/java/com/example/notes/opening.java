package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class opening extends AppCompatActivity
{

    String pass, cpass;
    EditText ed1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        pass = preferences.getString("password", "");

        Window window=this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
    }

    public void submit(View view)
    {
        ed1 = findViewById(R.id.ed1);
        cpass = ed1.getText().toString();
        if (cpass.equals(pass))
        {
            Intent i = new Intent(opening.this, home.class);
            startActivity(i);
        }
        else
        {
            ed1.requestFocus();
            ed1.setError("Wrong password");
        }
    }
}
