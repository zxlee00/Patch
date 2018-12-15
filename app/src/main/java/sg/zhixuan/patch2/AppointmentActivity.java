package sg.zhixuan.patch2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppointmentActivity extends AppCompatActivity {

    LinearLayout btnHome;
    ImageButton btnAddAppt;
    RecyclerView rvApptList;
    TextView txtAppt, hometext;
    private DatabaseReference mDatabase;
    private RecyclerView rvAppointments;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter<Appointment, AppointmentViewHolder> mFirebaseAdapter;
    String uid;
    static List<String> contactList;
    DatabaseReference databaseReference;
    private ProgressBar progressBar;
    static List<String> contactIDList;

    Integer alarmCodeToBeCancelled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        txtAppt = (TextView)findViewById(R.id.txtAppt);
        hometext = (TextView)findViewById(R.id.hometext);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        setChineseLanguage();

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        Log.d("ZZZ", uid);
        rvAppointments  = (RecyclerView)findViewById(R.id.rvApptList);
        mDatabase = FirebaseDatabase.getInstance().getReference("appointments/" + uid);
        btnAddAppt = (ImageButton) findViewById(R.id.btnAddAppt);
        btnHome = (LinearLayout)findViewById(R.id.btnHome);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("contacts").child(uid);

        rvAppointments.setLayoutManager(new LinearLayoutManager(this));

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentActivity.this.finish();
            }
        });

        btnAddAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppointmentActivity.this, CreateApptGroup.class));
            }
        });

        FirebaseRecyclerOptions<Appointment> options =
                new FirebaseRecyclerOptions.Builder<Appointment>()
                        .setQuery(mDatabase, Appointment.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Appointment, AppointmentViewHolder>(options) {
            @Override
            public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View item = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.apptitem,
                        parent,
                        false
                );

                return new AppointmentViewHolder(item);
            }

            @Override
            public void onDataChanged() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onBindViewHolder(@NonNull final AppointmentViewHolder holder, final int position, @NonNull final Appointment model) {
                final Appointment appointment = model;

                holder.txtApptLocation.setText(appointment.apptLocation);
                holder.txtApptTime.setText(appointment.apptTime);
                holder.txtApptDate.setText(appointment.apptDate);
                holder.txtApptTitle.setText(appointment.apptName);

                DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference().child("users").child(appointment.apptPartyID).child("name");
                userNameRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot nameSnap) {
                        holder.txtApptParty.setText(nameSnap.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference imageRef = FirebaseDatabase.getInstance().getReference().child("users").child(appointment.apptPartyID).child("profilePic");
                ValueEventListener imageListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("ZZZ", "HASBEENRUN");
                        Log.d("ZZZ", dataSnapshot.getKey());
                        Glide.with(AppointmentActivity.this)
                                .load(dataSnapshot.getValue(String.class))
                                .apply(new RequestOptions()
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE))
                                .into(holder.imgApptParty);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                imageRef.addListenerForSingleValueEvent(imageListener);

                holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                v.setBackgroundResource(R.drawable.pressedbottomborder);
                                break;
                            case MotionEvent.ACTION_UP:
                                v.setBackgroundResource(R.drawable.bottomborder);
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                v.setBackgroundResource(R.drawable.bottomborder);
                                break;
                        }
                        return false;
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AppointmentActivity.this);

                        String title = "Delete Appointment";
                        String message = "Are you sure you want to delete this appointment?" +
                                "\n\nWith: " + holder.txtApptParty.getText() +
                                "\nName: " + holder.txtApptTitle.getText() +
                                "\nLocation: " + holder.txtApptLocation.getText() +
                                "\nDate: " + holder.txtApptDate.getText() +
                                "\nTime: " + holder.txtApptTime.getText();
                        String posbtn = "CONFIRM";
                        String negbtn = "CANCEL";

                        if (MainActivity.language.equals("Chinese")) {
                            title = "删除预约";
                            message = "您确定要删除此约会吗？" +
                                    "\n\n与：" + holder.txtApptParty.getText() +
                                    "\n预约名称：" + holder.txtApptTitle.getText() +
                                    "\n地点：" + holder.txtApptLocation.getText() +
                                    "\n日期：" + holder.txtApptDate.getText() +
                                    "\n时间：" + holder.txtApptTime.getText();
                            posbtn = "确定";
                            negbtn = "取消";
                        }

                        alertDialog.setTitle(title);
                        alertDialog.setMessage(message);

                        alertDialog.setNegativeButton(negbtn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        alertDialog.setPositiveButton(posbtn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("ZZZ", Integer.toString(holder.getAdapterPosition()));
                                mFirebaseAdapter.getRef(holder.getAdapterPosition()).removeValue();
                                alarmCodeToBeCancelled = appointment.alarmCode;
                                Log.d("ZZZ", "Cancel Alarm Code: " + alarmCodeToBeCancelled);
                                cancelAlarm();
                                mFirebaseAdapter.notifyDataSetChanged();
                            }
                        });

                        alertDialog.show();
                        return true;
                    }
                });
            }
        };

        mFirebaseAdapter.notifyDataSetChanged();
        mFirebaseAdapter.startListening();
        rvAppointments.setAdapter(mFirebaseAdapter);

        contactList = new ArrayList<String>();
        contactIDList = new ArrayList<String>();
        ValueEventListener contactIDListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                contactList.clear();
                contactIDList.clear();
                for (final DataSnapshot contactIDSnapshot : dataSnapshot.getChildren()) {
                    contactIDList.add(contactIDSnapshot.getKey());
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("users").child(contactIDSnapshot.getKey());
                    ValueEventListener contactNameListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.d("ZZZ", "ID SNAPSHOT: " +contactIDSnapshot.getKey());
                                contactList.add(snapshot.child("name").getValue(String.class));
                                contactList.add(snapshot.child("profilePic").getValue(String.class));
                                Log.d("ZZZ", "ABCD");
                                Log.d("ZZZ", "NAME: " + snapshot.child("name").getValue(String.class));
                                Log.d("ZZZ", "PROFILE PIC URL: "+ snapshot.child("profilePic").getValue(String.class));
                                //Log.d("ZZZ", "WHAtEVER:"+nameSnapshot.getValue(String.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    mRef.addValueEventListener(contactNameListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(contactIDListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mFirebaseAdapter.stopListening();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mFirebaseAdapter.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.stopListening();
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmCodeToBeCancelled, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            txtAppt.setText("预约");
            hometext.setText("主页");
        }
    }
}
