package jp.ac.asojuku.st.batterywarning;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //複数のインテントを受信する場合はif文を使う
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int scale = intent.getIntExtra("scale", 0);
                int level = intent.getIntExtra("level", 0);

                if(level == 15 ) {
                    NotificationManager myNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification.Builder builder = new Notification.Builder(context);
                    int notificationId = intent.getIntExtra("notificationId",0);
                    builder.setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("バッテリーが１５％以下になりました")
                            .setPriority(Notification.PRIORITY_HIGH);
                    myNotification.notify(notificationId,builder.build());
                }

                    final Calendar calendar = Calendar.getInstance();
                    final int hour = calendar.get(calendar.HOUR_OF_DAY);
                    final int minute = calendar.get(calendar.MINUTE);

                    String title = "Battery Watch";
                    String msg = " " + hour + ":" + minute + ":" + "second" + " " +
                            " " + level + "/" + scale;
                    Log.v(title, msg);

            }
        }
    }
}
