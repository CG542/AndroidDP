package com.mot.AndroidDP;

import android.app.*;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by bkmr38 on 6/14/2016.
 */
public class StatusListener extends Service {
    NotificationManager notifer;
    Intent statusChanged =null;
    Timer timer = null;
    @Override
    public void onCreate()
    {
        Log.i("StatusListener","onCreate");
        super.onCreate();
        notifer = (NotificationManager) getSystemService(Activity.NOTIFICATION_SERVICE);
        statusChanged=new Intent();
        timer=new Timer();
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        Log.i("StatusListener","onStart");
        super.onStart(intent, startId);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //发送广播
                ReceiveData();
            }
        }, 1000,1000);

    }

    @Override
    public void onDestroy()
    {
        Log.i("StatusListener","onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void ReceiveData(){
        String time = "2016-06-12 18:50:32";
        List<StatusData> dataList = HttpUtility.QueryDPStatus(time);
        if(dataList.size()>0) {
            SQLHelper sqlHeler = new SQLHelper(getApplicationContext());
            for(StatusData data : dataList) {

                sqlHeler.insert(data.DPName,data.Status,data.Time,data.Type);

                if(data.Type.equals("Warning")) {
                    SendNotification(data.DPName+":"+data.Status);
                }
            }

            SendStatusChangedBroadcast();
        }
    }

    private void SendStatusChangedBroadcast(){
        Log.i("StatusListener","SendStatusChangedBroadcast");
        statusChanged.setAction(GlobalPara.Status_Changed_Action);
        sendBroadcast(statusChanged);
    }

    private void SendNotification(String msg){
        Notification no = new Notification(R.drawable.ic_launcher,msg,System.currentTimeMillis());
        Intent intent=new Intent(this,FragementStatus.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        no.setLatestEventInfo(this,"AndroidDP",msg,pendingIntent);
        notifer.notify("Testinfo",R.drawable.ic_launcher,no);
    }


}
