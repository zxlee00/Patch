package sg.zhixuan.patch2;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    LinearLayout btnAppt, btnMatchup, btnChats, btnProfile;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference mDatabase;
    DatabaseReference userRef;
    TextView txtMatchup, txtAppt, txtChat, txtProfile;
    ImageView imgProfile, imgMatchup, imgChat, imgAppt;
    Button btnSignOut;
    static String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        btnSignOut = (Button)findViewById(R.id.btnSignOut);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        uid = mAuth.getUid();

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
                startActivity(new Intent(MainActivity.this, TestAddingFriends.class));
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
                startActivity(new Intent(MainActivity.this, UploadImage.class));
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

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Sign Out")
                        .setMessage("Are you sure you want to sign out?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth.signOut();

                                Intent intent = new Intent(MainActivity.this, MainAccountActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
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
}
