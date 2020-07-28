package com.yajith.hand;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkPermission(Manifest.permission.SEND_SMS,100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent i=new Intent(Splash.this,MainActivity.class);
               finish();
               startActivity(i);
            }
        },1000);
    }

    public void checkPermission(String permission, int requestCode) {
        if(ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(Splash.this,new String[]{permission},requestCode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
