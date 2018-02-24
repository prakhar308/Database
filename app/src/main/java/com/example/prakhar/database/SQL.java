package com.example.prakhar.database;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.nio.file.Files;

public class SQL extends AppCompatActivity {

    String s;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    SQLiteDatabase sq;
    Button save,load;
    EditText t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        save=findViewById(R.id.button);
        t1=findViewById(R.id.editText);
        load=findViewById(R.id.load);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sq=openOrCreateDatabase("DATA",MODE_PRIVATE,null);
                s=t1.getText().toString();
                sq.execSQL("CREATE TABLE IF NOT EXISTS DATA(count VARCHAR);");
                sq.execSQL("INSERT INTO DATA(count) VALUES(\""+s+"\");");

                sp=getSharedPreferences("SP",MODE_PRIVATE);
                editor=sp.edit();
                editor.putString("sp1",s);
                editor.commit();
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor output=sq.rawQuery("SELECT * FROM DATA;",null);
                output.moveToFirst();
                for(int i=0;i<output.getCount();i++)
                {
                    Log.e("SQL",output.getInt(0)+"");
                    output.moveToNext();
                }

                s=sp.getString("sp1",s);
                Log.e("SHARED",s);

            }
        });

    }
}
