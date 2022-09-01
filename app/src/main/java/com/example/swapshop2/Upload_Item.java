package com.example.swapshop2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class Upload_Item extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    //Initializing variables
    Button btUploadpic;
    RecyclerView recyclerView;

    ArrayList<Uri> arrayList = new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_item);

        //Assign variable
        btUploadpic = findViewById(R.id.bt_uploadpic);
        recyclerView = findViewById(R.id.recycler_view);

        //Set listener on button
        btUploadpic.setOnClickListener(view ->{
            //Define camera & storage permissions
            String[] strings = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
            //Check condition
            if (EasyPermissions.hasPermissions(this, strings)){
                //When permissions are already granted
                //Create method
                imagePicker();
            }else{
                //When permission not granted
                //Request permission
                EasyPermissions.requestPermissions(
                        this,
                        "To upload item image please provide app access to camera and storage",
                        100,
                        strings
                );
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Handles the request result
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Check condition
        if (resultCode == RESULT_OK && data != null){
            //When activity contains the data
            //Check condition
            if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO){
                //When you request for a photo
                //Initialize array list
                //This is for future reference when we want to upload multiple photos, rn its only set at max 1 photo cuz looks un neat
                arrayList = data.getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA);
                //Set layout manager
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                //Set adapter
                recyclerView.setAdapter(new Upload_Item_Adapter(arrayList));
            }
        }
    }

    private void imagePicker(){
        //Open the picker and set selections
        FilePickerBuilder.getInstance()
                .setActivityTitle("Select Image")
                .setSpan(FilePickerConst.SPAN_TYPE.FOLDER_SPAN,3)
                .setSpan(FilePickerConst.SPAN_TYPE.DETAIL_SPAN, 4)
                .setMaxCount(1)
                .setSelectedFiles(arrayList)
                .setActivityTheme(R.style.CustomTheme)
                .pickPhoto(this);

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //Check condition
        if (requestCode == 100 && perms.size() == 2){
            //When permissions are granted
            //Call method
            imagePicker();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //Check condition
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            //When permissions denied multiple times
            //Open app setting
            new AppSettingsDialog.Builder(this).build().show();
        }else{
            //When permission denied once
            //Display toast
            Toast.makeText(getApplicationContext(),
                    "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}