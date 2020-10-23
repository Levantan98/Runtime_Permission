package com.example.runtime_permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_CODE = 1;
    Button btn_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_check = findViewById(R.id.btn_check_perm);
        // xin quyen
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                + ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // khi quyen bi tu choi
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                // tao 1 dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Granted Perm");
                builder.setMessage("Read_contact,Camera,Callphone");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(
                                MainActivity.this, new String[]
                                        {
                                                Manifest.permission.CALL_PHONE,
                                                Manifest.permission.READ_CONTACTS,
                                                Manifest.permission.CAMERA

                                        }, REQUEST_CODE
                        );
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                ActivityCompat.requestPermissions(
                        MainActivity.this, new String[]
                                {
                                        Manifest.permission.CALL_PHONE,
                                        Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.CAMERA

                                }, REQUEST_CODE
                );
            }
        } else {
            Toast.makeText(this, "ok perm", Toast.LENGTH_SHORT).show();
        }
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                startActivity(new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(uri));


            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if ((grantResults.length > 0) && (grantResults[0] + grantResults[1]
                    + grantResults[2] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Perm...Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}