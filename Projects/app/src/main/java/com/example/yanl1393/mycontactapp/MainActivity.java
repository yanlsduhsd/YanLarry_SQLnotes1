package com.example.yanl1393.mycontactapp;

import android.content.Intent;
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
            for (int i = 0; i < 4; i++)
                buffer.append(String.format("%s: %s\n", res.getColumnName(i), res.getString(i)));
//            buffer.append("ID: " + res.getString(0)+"\n");
//            buffer.append("Name: " + res.getString(1)+"\n");
//            buffer.append("Address: " + res.getString(2)+"\n");
//            buffer.append("Phone #: " + res.getString(3)+"\n\n");
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

    public static final String EXTRA_MESSAGE = "com.example.yanl1393.mycontactapp.MESSAGE";
    public void searchRecord(View view) {
        Log.d("MyContactApp", "MainActivity: launching SearchActivity");
        Intent intent = new Intent(this, SearchActivity.class);

        intent.putExtra(EXTRA_MESSAGE, getRecords());
        startActivity(intent);
    }

    private String getRecords() {
        Cursor res = myDb.getAllData();

        StringBuffer buffer = new StringBuffer();
        int counter = 0;

        while (res.moveToNext()) {

            if (res.getString(1).equals(editName.getText().toString())) {
                for (int i = 0; i < 4; i++)
                  buffer.append(String.format("%s: %s\n", res.getColumnName(i), res.getString(i)));
                counter++;
//                buffer.append("ID: " + res.getString(0)+"\n");
//                buffer.append("Name: " + res.getString(1)+"\n");
//                buffer.append("Address: " + res.getString(2)+"\n");
//                buffer.append("Phone #: " + res.getString(3)+"\n\n");
//                counter++;
            }

        }

        if (counter==0) {
            return "No entries found with the name \"" + editName.getText().toString() + "\" .";
        } else {
            buffer.insert(0, counter + " entries found for the name \"" + editName.getText().toString() + "\" .\n\n");
            return buffer.toString();
        }
    }
}
