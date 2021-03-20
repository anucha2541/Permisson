package com.example.permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int RESULT_LOAD_IMAGE=321 ;
    static final int REQUEST_IMAGE_PERMISSION= 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //เรียกใช้
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_IMAGE_PERMISSION);
    }

    //Function to check
    public boolean checkPermission(String permission, int requesCode) {
        //checking if permission
        if (ContextCompat.checkSelfPermission(
                MainActivity.this,permission)
        == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{permission},requesCode);
            return false;
        }else {
            Toast.makeText(MainActivity.this,
                    "Permission already granted",Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length>0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this,"Storage Permission Dented",
                    Toast.LENGTH_SHORT).show();
        }

    }
};