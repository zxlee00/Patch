package sg.zhixuan.patch2;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateApptDetails extends AppCompatActivity {

    Button newApptDatePicker, newApptTimePicker, btnCompleteAppt, btnApptDetailsToApptActivity;
    int year, month, day;
    int hourOfDay, minute;
    TextView createappttext, dateTextView, timeTextView, txtNewApptName, txtNewApptLocation, txtNewApptParty;
    TextView apptname, apptdatetime, apptLocation;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    TimePickerDialog.OnTimeSetListener mTimeSetListener;

    FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String newApptKey;
    Integer lastAlarmCode;
    String apptPartyIDSelected;
    String apptPartyImageURLSelected;
    ImageView imgPartyPic;

    private NotificationManagerCompat notificationManager;
    NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appt_details);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        notificationHelper = new NotificationHelper(this);
        notificationManager = NotificationManagerCompat.from(this);

        txtNewApptParty = (TextView)findViewById(R.id.txtNewApptParty);
        txtNewApptLocation = (TextView)findViewById(R.id.txtNewApptLocation);
        btnApptDetailsToApptActivity = (Button)findViewById(R.id.btnApptDetailsToApptActivity);
        dateTextView = (TextView)findViewById(R.id.dateTextView);
        timeTextView = (TextView)findViewById(R.id.timeTextView);
        txtNewApptName = (TextView)findViewById(R.id.txtNewApptName);
        newApptDatePicker = (Button)findViewById(R.id.newApptDatePicker);
        newApptTimePicker = (Button)findViewById(R.id.newApptTimePicker);
        apptname = (TextView)findViewById(R.id.apptname);
        apptdatetime = (TextView)findViewById(R.id.apptdatetime);
        apptLocation = (TextView)findViewById(R.id.apptLocation);
        btnCompleteAppt = (Button)findViewById(R.id.btnCompleteAppt);
        createappttext = (TextView)findViewById(R.id.createappt);
        imgPartyPic = (ImageView)findViewById(R.id.imgPartyPic);

        setChineseLanguage();

        //Getting selected party id and imageURL
        apptPartyIDSelected = UserContactAdapter.apptPartyIDSelected;
        apptPartyImageURLSelected = UserContactAdapter.apptPartyImageURLSelected;

        //Setting selected party
        txtNewApptParty.setText(UserContactAdapter.apptPartySelected);
        Glide.with(CreateApptDetails.this)
                .load(apptPartyImageURLSelected)
                .apply(new RequestOptions()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(imgPartyPic);

        //getting current date
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        final String date = String.format("%02d/%02d/%d ", day,month + 1,year);
        dateTextView.setText(date);

        newApptDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(
                        CreateApptDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dateDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                month = selectedMonth;
                day = selectedDay;
                year = selectedYear;
                String date = String.format("%02d/%02d/%d ", day,month + 1,year);
                dateTextView.setText(date);
            }
        };

        newApptTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting current/previously selected time
                if (TextUtils.isEmpty(timeTextView.getText().toString())) {
                    hourOfDay = c.get(c.HOUR_OF_DAY); //Current Hour
                    minute = c.get(c.MINUTE); //Current Minute
                }

                TimePickerDialog timeDialog = new TimePickerDialog(
                        CreateApptDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        hourOfDay,
                        minute,
                        false
                );

                timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHourOfDay, int selectedMinute) {
                hourOfDay = selectedHourOfDay;
                minute = selectedMinute;

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String format = "hh:mm a";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
                String formatted_time = simpleDateFormat.format(calendar.getTime());

                timeTextView.setText(formatted_time);
            }
        };

        btnCompleteAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String error1 = "Please enter the appointment name!";
                String error2 = "Please select a time!";
                String error3 = "Please enter a location!";

                if (MainActivity.language.equals("Chinese")) {
                    error1 = "请输入预约名称！";
                    error2 = "请选择时间！";
                    error3 = "请输入预约地点！";
                }

                if (TextUtils.isEmpty(txtNewApptName.getText().toString()))
                    txtNewApptName.setError(error1);
                else if (timeTextView.getText().toString().matches("") || timeTextView.getText().toString().matches(error2)) {
                    timeTextView.setText(error2);
                }
                else if (TextUtils.isEmpty(txtNewApptLocation.getText().toString()))
                    txtNewApptLocation.setError(error3);
                else {
                    checkLastAlarmCode();
                    writeNewAppt(txtNewApptParty.getText().toString(), txtNewApptName.getText().toString(), dateTextView.getText().toString(), timeTextView.getText().toString(), txtNewApptLocation.getText().toString());
                    updateLastAlarmCode();

                    Log.d("ZZZ", "New Alarm Code: " + lastAlarmCode + 1);
                    setNotificationTime();

                    CreateApptDetails.this.finish();
                }
            }
        });

        btnApptDetailsToApptActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void writeNewAppt(String apptParty, String apptName, String apptDate, String apptTime, String apptLocation) {
        firebaseUser = mAuth.getCurrentUser();
        final String userID = firebaseUser.getUid();
        Appointment appointment = new Appointment(apptParty, apptName, apptDate, apptTime, apptLocation, lastAlarmCode + 1, apptPartyIDSelected, apptPartyImageURLSelected);
        newApptKey = mDatabase.child("appointments").child(userID).push().getKey();
        mDatabase.child("appointments").child(userID).child(newApptKey).setValue(appointment);
    }

    private void checkLastAlarmCode() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        lastAlarmCode = sharedPreferences.getInt("lastAlarmCode", 0);
    }

    private void updateLastAlarmCode() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastAlarmCode", lastAlarmCode + 1);
        editor.commit();
    }

    public void setNotificationTime() {
        Log.d("ZZZ", "SETTING TIME");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.HOUR_OF_DAY, -2);
        startAlarm(c);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, lastAlarmCode + 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void setChineseLanguage() {
        if(MainActivity.language.equals("Chinese")) {
            createappttext.setText("创造预约");
            apptname.setText("预约名称");
            apptdatetime.setText("日期 & 时间");
            apptLocation.setText("地点");
            newApptDatePicker.setText("日期");
            newApptTimePicker.setText("时间");
            btnCompleteAppt.setText("完成");
        }
    }
}
