package com.cafe24.fishfox.whitefoxpush;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONException;
import org.json.JSONObject;

public class MyWorker extends Worker {

    private static final String TAG = "MyWorker";

    public MyWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Performing long running task in scheduled job");
        // TODO(developer): add long running task here.


        Data data = getInputData();
        String strData = data.getString("data");

        Log.d("SimpleWorker", "SimpleWorker : ["+strData+"]");

        JSONObject json = null;
        try {
            json = new JSONObject(strData);
            String title = json.getString("title");
            String phone = json.getString("phone");

            String content  = json.getString("content");

            if(title.equals("사용자")){
                sendSMS(phone,content);
            }else{
                sendSMS(phone,content);
            }
            SystemClock.sleep(1000);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Result.success();
    }


    private void sendSMS(String phoneNumber, String message) {

        PendingIntent pi = PendingIntent.getActivity(this.getApplicationContext(),0,new Intent(this.getApplicationContext(),MainActivity.class),0);

        SmsManager sms = SmsManager.getDefault();
        
        sms.sendTextMessage(phoneNumber,null,message,pi,null);

        Toast.makeText(this.getApplicationContext(), "메시지가 전송 되었습니다.",Toast.LENGTH_LONG).show();

    }

}
