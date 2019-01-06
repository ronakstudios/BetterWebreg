package faraday.betterwebreg;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.util.Date;


public class NotificationHandler extends BroadcastReceiver {
    CourseTime times ;
    Course courses ;
    Student schedule;
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            FileInputStream fileInputStream = context.openFileInput("webregschedule");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            schedule = (Student) inputStream.readObject();
            inputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        CourseTime[][] times2 = Student.getTimesOnSchedule(schedule);
        Course[][] courses2 = Student.getCoursesOnSchedule(schedule);


        Date currentTime = Calendar.getInstance().getTime();
        int j = 50;
        int ii=currentTime.getDay()-1;
        for(int l = 0; l < times2[ii].length;l++) {
            times2[ii][l].updateTime();
            if((times2[ii][l].timeComparator-(currentTime.getHours()+currentTime.getMinutes()/100.0))<=1.5)
                j=l;
        }

        if(j==50){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "webregnotif")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("One of Your Classes Starts in 1 Hour")
                    .setContentText("Due to an error, the system is unable to know which one.")
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setColor(Color.RED)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(3749, builder.build());

        }else {
            courses = courses2[ii][j];
            times = times2[ii][j];


            if (!(courses.courseCampus.equals(""))) {

                Intent intent2 = new Intent(context, BusViewActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.putExtra("bussesToTurnRed", NextBus.busName(courses.courseCampus));
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);

//        Intent snoozeIntent = new Intent(ScheduleViewerActivity.this,BusViewActivity.class);
//        snoozeIntent.setAction("ACTION_SNOOZE");
//        snoozeIntent.putExtra("webregnotif",0);
//        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(ScheduleViewerActivity.this,0,snoozeIntent,0);

                String busList = "";
                String[] buses = NextBus.busName(courses.courseCampus);
                for (int i = 0; i < buses.length; i++) {
                    if (i != buses.length - 1) {
                        busList += buses[i].toUpperCase() + ", ";
                    } else if (buses.length != 1) {
                        busList += "or " + buses[i].toUpperCase();
                    } else {
                        busList = buses[i].toUpperCase();
                    }
                }


//        Log.d("COURSE ARRAY",print(courses));
//        Log.d("COURSE ARRAY",print(times));

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "webregnotif")
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(courses.courseName + " Starts in 1 Hour")
                        .setContentText("Take bus " + busList + ". Click for more details")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(courses.toString()))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentIntent(pendingIntent)
                        .setColor(Color.RED)
                        .setAutoCancel(true);
                //.addAction(R.drawable.ic_notification,"SNOOZE",snoozePendingIntent);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(3749, builder.build());
            } else {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "webregnotif")
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(courses.courseName + " Starts in 1 Hour")
                        .setContentText("Remember to complete all necessary online coursework.")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(courses.toString()))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setColor(Color.RED)
                        .setAutoCancel(true);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(3749, builder.build());
            }
        }

    }
}
