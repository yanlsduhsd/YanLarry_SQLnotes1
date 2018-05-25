package com.example.yanl1393.mycontactapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editAddress, editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editAddress = findViewById(R.id.editText_address);
        editPhone = findViewById(R.id.editText_phone);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated myDb");
    }

    public void addData(View view) {
        Log.d("MyContactApp", "MainActivity: add contact button pressed");

        boolean isInserted = myDb.insertData(editName.getText().toString(), editAddress.getText().toString(), editPhone.getText().toString());

        if (isInserted) {
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "FAILED - contact not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void viewData(View view) {
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity: recieved cursor");

        if (res.getCount()==0) {
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {
            //append res.column 0,1,2,3 to buffer
            //delimit each append with \n

            buffer.append("ID: " + res.getString(0)+"\n");
            buffer.append("Name: " + res.getString(1)+"\n");
            buffer.append("Address: " + res.getString(2)+"\n");
            buffer.append("Phone #: " + res.getString(3)+"\n\n");

        }

        showMessage("Data:", buffer.toString());
    }

    private void showMessage(String title, String message) {
        Log.d("MyContactApp", "MainActivity: showMessage: assembling AlertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
