package com.example.permission;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    static final int RESULT_LOAD_IMAGE=321 ;
    static final int REQUEST_IMAGE_PERMISSION= 111;
    private StorageReference mStorageRef;
    private ImageView imageView;
    private Uri selectedImage;
    private Button btn_select , btn_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        btn_select = findViewById(R.id.btn_select);
        btn_upload = findViewById(R.id.btn_upload);
        imageView = findViewById(R.id.imageView);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStorageRef.child("Image/"+ UUID.randomUUID()+".jpg").putFile(selectedImage)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        //เรียกใช้
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_IMAGE_PERMISSION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){

            selectedImage = data.getData();
            imageView.setImageURI(selectedImage);

        }
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