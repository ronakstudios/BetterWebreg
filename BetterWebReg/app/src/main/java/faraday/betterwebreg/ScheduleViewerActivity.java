package faraday.betterwebreg;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class ScheduleViewerActivity extends AppCompatActivity {

    static Student userSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_viewer);
        TextView textView = (TextView) findViewById(R.id.textView);
        userSchedule = ScheduleGetter.HTMLParser(ScheduleGetter.schedule);
        textView.setText(userSchedule.toString());
        Button button = (Button) findViewById(R.id.button4);
        try {
            FileOutputStream fileOutputStream = ScheduleViewerActivity.this.openFileOutput("webregschedule",Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(userSchedule);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("webregnotif", "webregnotifchannelname", NotificationManager.IMPORTANCE_HIGH);
                    channel.setDescription("Busing Notification");
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }
                notificationManager();

            }
        });
    }

//    public String print(Object[][] arr){
//        String u = "";
//        for(int i = 0; i <arr.length;i++) {
//            for (int j = 0; j < arr[i].length; j++)
//                u += arr[i][j].toString() + " ";
//            u+="\n";
//        }
//        return u;
//    }
    void notificationManager(){
        CourseTime[][] times = Student.getTimesOnSchedule(userSchedule);
        Course[][] courses = Student.getCoursesOnSchedule(userSchedule);


for(int i =0; i<times.length;i++) {
    for (int j = 0; j < times[i].length; j++) {
        Calendar calendar = Calendar.getInstance();
        int day = Calendar.MONDAY;
        switch (times[i][j].dayOfWeek.toLowerCase()) {
            case "monday":
                day = Calendar.MONDAY;
                break;
            case "tuesday":
                day = Calendar.TUESDAY;
                break;
            case "wednesday":
                day = Calendar.WEDNESDAY;
                break;
            case "thursday":
                day = Calendar.THURSDAY;
                break;
            case "friday":
                day = Calendar.FRIDAY;
                break;

        }
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.HOUR_OF_DAY, times[i][j].starttimehour - 1);
        calendar.set(Calendar.MINUTE, times[i][j].starttimemunuite);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(ScheduleViewerActivity.this, NotificationHandler.class);
        intent.putExtra("i", i + "");
        intent.putExtra("j", j + "");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ScheduleViewerActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
    }
}
    }
}
