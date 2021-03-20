package com.example.reminderapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



import java.sql.Time;
import java.util.Calendar;

//import static com.example.reminderapplication.Notifies.CHANNEL_1_ID;
//import static com.example.reminderapplication.Notifies.CHANNEL_2_ID;



public class SecondActivity extends AppCompatActivity {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";


    Button backbuttonView;
    private DatePicker datePicker;
    private TimePicker timePicker;

    private Calendar calendar;
    private TextView dateView;
    private TextView timeView;

    private TextView title;
    private int year, month, day, hour ,min ;

    private RadioButton high ;
    private RadioButton low ;
    private NotificationManagerCompat notificationManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);


        //notificationManager = NotificationManagerCompat.from(this);
        //NotificationManager manager = (NotificationManager) getSystemService()

        dateView = (TextView) findViewById(R.id.dateView);
        timeView = (TextView) findViewById(R.id.timeView);

        title = (TextView)  findViewById(R.id.title);

        high = (RadioButton)  findViewById(R.id.highPriority);
        low = (RadioButton)  findViewById(R.id.lowPriority);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);
        showDate(year, month+1, day);


        //Back button
        backbuttonView = (Button) findViewById(R.id.backbutton);
        backbuttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //show date dialog
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
            }
        });

        //show time dialog
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);


            }

        });
        createNotificationChannels();


    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        if (id ==0 ){
            return new TimePickerDialog(this,
                    timeListener, hour, min, false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private TimePickerDialog.OnTimeSetListener timeListener = new
           TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    showTime(hourOfDay,minute);
                }
           };





    public void showTime(int hour, int min) {
        String format;
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        timeView.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public void sendNotification(View v) {

    if(high.isChecked()){
        sendOnChannel1();
        //Toast.makeText(this,"hi",Toast.LENGTH_LONG).show();
    }

    if(low.isChecked()){
            sendOnChannel2();
            //Toast.makeText(this,"hi",Toast.LENGTH_LONG).show();
        }



    }

        public void sendOnChannel1( ) {
        //String title = editTextTitle.getText().toString();
       // String message = editTextMessage.getText().toString();
            Log.i("sendOnChannel1", "sendOnChannel1: ");
            String titlee = "hiii";
            String message = "yaaaay";

            Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
       // Notification notification = new NotificationCompat.Builder( this, CHANNEL_1_ID)
                //.setSmallIcon(R.drawable.ic_one)
                .setContentTitle(titlee)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
            Log.i("sendOnChannel1", String.valueOf(notification));
            notificationManager.notify(1, notification);
            //Log.e(sendOnChannel1, "sendOnChannel1: ", );
            Log.i("sendOnChannel1", "sendOnChannel1: 50 ");
    }

    public void sendOnChannel2( ) {
        //String title = editTextTitle.getText().toString();
       // String message = editTextMessage.getText().toString();
        Log.i("sendOnChannel1", "sendOnChannel1: ");
        String titlee = "hiii this is 2";
        String message = "yaaaay 222";
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                //.setSmallIcon(R.drawable.ic_one)
                .setContentTitle(titlee)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(2, notification);
    }

    private void createNotificationChannels(){

        Log.i("sendOnChannel1", "createNotificationChannels: 89 ");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,"Channel 1",
                    NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Reminder1");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,"Channel 2",
                    NotificationManager.IMPORTANCE_LOW);
            channel2.setDescription("Reminder2");
            Log.i("sendOnChannel1", String.valueOf(channel1));
            NotificationManager manager = (NotificationManager) getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }}

}