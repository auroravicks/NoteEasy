package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class create_pass extends AppCompatActivity
{
    EditText ed1, ed2;
    public static String pass,fpass;
    public String vpass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pass);

        Window window=this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
    }

    public void validate(View view)
    {
        pass = ed1.getText().toString();
        vpass = ed2.getText().toString();

        if (pass.equals(""))
        {
            ed1.requestFocus();
            ed1.setError("Empty field not acceptable");
        }
        else if (pass.length() < 6)
        {
            ed1.requestFocus();
            ed1.setError("Password needs to be at least six characters long");
        }
        else if (!pass.equals(vpass))
        {
            ed2.requestFocus();
            ed2.setError("Passwords don't match");
        }
        else
        {
            SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("password", pass);
            editor.apply();
            Toast.makeText(this, "Password set", Toast.LENGTH_LONG).show();
            Intent i = new Intent(create_pass.this, home.class);
            startActivity(i);
        }
    }
}
