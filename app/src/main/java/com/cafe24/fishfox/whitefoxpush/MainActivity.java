package com.cafe24.fishfox.whitefoxpush;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static final int SMS_RECEIVE_PERMISSON=1;
    static final int SMS_SEND_PERMISSON=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        //권한이 부여되어 있는지 확인
        int permissonCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if(permissonCheck != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "SMS 수신권한 있음", Toast.LENGTH_SHORT).show();

            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.RECEIVE_SMS)){
                //이곳에 권한이 왜 필요한지 설명하는 Toast나 dialog를 띄워준 후, 다시 권한을 요청한다.
                Toast.makeText(getApplicationContext(), "SMS권한이 필요합니다", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.RECEIVE_SMS}, SMS_RECEIVE_PERMISSON);
        }

        permissonCheck = ContextCompat.checkSelfPermission( this, Manifest.permission.SEND_SMS);

        if(permissonCheck != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                Toast.makeText(getApplicationContext(), "SMS권한이 필요합니다", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.SEND_SMS},SMS_SEND_PERMISSON );
        }


        Log.d(TAG, " onCreate 시작" );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, " FirebaseMessaging getToken 시작" );

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);

                        Log.d(TAG,"token:["+ msg+"]");
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        Log.d(TAG, " FirebaseMessaging getToken 끝" );
    }






}