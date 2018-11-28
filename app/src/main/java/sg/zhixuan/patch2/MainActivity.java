package sg.zhixuan.patch2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import junit.framework.Test;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    LinearLayout btnAppt, btnMatchup, btnChats, btnProfile;
    FirebaseDatabase database;
    static FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference mDatabase;
    TextView txtMatchup, txtAppt, txtChat, txtProfile, txtHome;
    ImageView imgProfile, imgMatchup, imgChat, imgAppt;
    Button btnMore;
    static String uid;
    static Activity activity;
    static String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("ZZZ", "LANGUAGE:" + language);

        activity = this;
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        btnAppt = (LinearLayout)findViewById(R.id.btnAppt);
        btnChats = (LinearLayout)findViewById(R.id.btnChats);
        btnMatchup = (LinearLayout)findViewById(R.id.btnMatchup);
        btnProfile = (LinearLayout)findViewById(R.id.btnProfile);
        txtMatchup = (TextView)findViewById(R.id.txtMatchup);
        txtAppt = (TextView)findViewById(R.id.txtAppt);
        txtChat = (TextView)findViewById(R.id.txtChat);
        txtProfile = (TextView)findViewById(R.id.txtProfile);
        imgAppt = (ImageView)findViewById(R.id.imgAppt);
        imgChat = (ImageView)findViewById(R.id.imgChat);
        imgMatchup = (ImageView)findViewById(R.id.imgMatchup);
        imgProfile = (ImageView)findViewById(R.id.imgProfile);
        btnMore = (Button)findViewById(R.id.btnMore);
        txtHome = (TextView)findViewById(R.id.txtHome);

        if (MainActivity.language.equals("Chinese"))
            setChineseLanguage();

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        uid = mAuth.getUid();

        FirebaseDatabase.getInstance().getReference().child("matchup").child("lookingForMatchup").child(uid).setValue("");
        Calendar today = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todaydate = simpleDateFormat.format(new Date());
        FirebaseDatabase.getInstance().getReference().child("users").child(MainActivity.uid).child("lastSignedIn").setValue(todaydate);

        btnAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AppointmentActivity.class));
            }
        });

        btnAppt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event, btnAppt, imgAppt, R.drawable.pressed_appt, R.drawable.appt, txtAppt);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfilePageActivity.class));
            }
        });

        btnProfile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event, btnProfile, imgProfile, R.drawable.pressed_profile, R.drawable.profile, txtProfile);
            }
        });

        btnMatchup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MatchUpOptions.class));
            }
        });

        btnMatchup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event, btnMatchup, imgMatchup, R.drawable.pressed_matchup, R.drawable.matchup, txtMatchup);
            }
        });

        btnChats.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event, btnChats, imgChat, R.drawable.pressed_chat, R.drawable.chat, txtChat);
            }
        });

        btnChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Users.class));
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MoreOptionsActivity.class));
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event, LinearLayout layout, ImageView imageView, int newImage, int oldImage, TextView text) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                imageView.setImageResource(newImage);
                text.setTextColor(Color.parseColor("#5e2a40"));
                if (layout == btnAppt || layout == btnMatchup)
                    layout.setBackgroundResource(R.drawable.pressed_rightborder);
                else
                    layout.setBackgroundColor(Color.parseColor("#dfb992"));
                break;
            case MotionEvent.ACTION_UP:
                imageView.setImageResource(oldImage);
                text.setTextColor(Color.parseColor("#203f58"));
                if (layout == btnAppt || layout == btnMatchup)
                    layout.setBackgroundResource(R.drawable.rightborder);
                else
                    layout.setBackgroundColor(Color.parseColor("#f0dfbb"));
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public static void finishMainActivity() {
        activity.finish();
    }

    public void setChineseLanguage() {
        txtProfile.setText("个人资料");
        txtChat.setText("好友对话");
        txtAppt.setText("预约");
        txtMatchup.setText("配对");
        txtHome.setText("主页");
        btnMore.setText("更多");
    }
}
