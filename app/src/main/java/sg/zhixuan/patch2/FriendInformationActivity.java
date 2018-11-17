package sg.zhixuan.patch2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendInformationActivity extends AppCompatActivity {

    TextView friendname, friendgender, friendage, friendhobby, friendrating;
    Button btnFeedback, btnBlock, btnDeleteFriend, btnViewFeedbacks, btnFriendInfoToFriend;
    ImageView friendimg;
    static String name, gender, age, hobby = "", imgurl;
    static User selectedFriend;
    DatabaseReference contactRef;
    DatabaseReference ratingRef;
    DatabaseReference blacklistRef;
    DatabaseReference apptRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_information);

        friendage = (TextView)findViewById(R.id.friendage);
        friendgender = (TextView)findViewById(R.id.friendgender);
        friendhobby = (TextView)findViewById(R.id.friendhobby);
        friendname = (TextView)findViewById(R.id.friendname);
        friendimg = (ImageView)findViewById(R.id.friendimg);
        btnBlock = (Button)findViewById(R.id.btnBlock);
        btnFeedback = (Button)findViewById(R.id.btnFeedback);
        btnDeleteFriend = (Button)findViewById(R.id.btnDeleteFriend);
        btnViewFeedbacks = (Button)findViewById(R.id.btnViewFeedbacks);
        btnFriendInfoToFriend = (Button)findViewById(R.id.btnFriendInfoToFriend);
        friendrating = (TextView)findViewById(R.id.friendrating);

        contactRef = FirebaseDatabase.getInstance().getReference().child("contacts");
        blacklistRef = FirebaseDatabase.getInstance().getReference().child("blacklist");
        apptRef = FirebaseDatabase.getInstance().getReference().child("appointments");

        friendgender.append(gender);
        friendname.setText(name);
        friendage.append(age);
        friendhobby.setText("Hobby:" + hobby);

        Glide.with(this)
                .load(imgurl)
                .apply(new RequestOptions()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(friendimg);

        ratingRef = FirebaseDatabase.getInstance().getReference().child("ratingfeedback").child(selectedFriend.uid).child("rating");
        ratingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendrating.append(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnFriendInfoToFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDeleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FriendInformationActivity.this);
                alertDialog.setTitle("Delete Friend")
                        .setMessage("Are you sure you want to delete this friend?\nYou will not be able to contact him/her anymore.")
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                contactRef.child(MainActivity.uid).child(selectedFriend.uid).removeValue();
                                contactRef.child(selectedFriend.uid).child(MainActivity.uid).removeValue();

                                apptRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d("ZZZ", "CHECKING APPT");
                                        for (DataSnapshot apptSnapshot : dataSnapshot.child(MainActivity.uid).getChildren()) {
                                            if (apptSnapshot.child("apptPartyID").getValue(String.class).equals(selectedFriend.uid)) {
                                                Log.d("ZZZ", "APPT FOUND!");
                                                apptRef.child(MainActivity.uid).child(apptSnapshot.getKey()).removeValue();
                                            }
                                        }

                                        for(DataSnapshot apptSnapshot : dataSnapshot.child(selectedFriend.uid).getChildren()) {
                                            if (apptSnapshot.child("apptPartyID").getValue(String.class).equals(MainActivity.uid)) {
                                                Log.d("ZZZ", "APPT FOUND!");
                                                apptRef.child(selectedFriend.uid).child(apptSnapshot.getKey()).removeValue();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                FriendsActivity.friendsList.remove(FriendsActivity.friendsList.indexOf(selectedFriend));
                                FriendsActivity.refreshList();
                                finish();
                            }
                        }).show();
            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewFeedbackActivity.feedbackUser = selectedFriend;
                finish();
                startActivity(new Intent(FriendInformationActivity.this, NewFeedbackActivity.class));
            }
        });

        btnViewFeedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewFeedbackActivity.selectedUser = selectedFriend;
                startActivity(new Intent(FriendInformationActivity.this, ViewFeedbackActivity.class));
            }
        });

        btnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FriendInformationActivity.this);
                alertDialog.setTitle("Block User")
                        .setMessage("Are you sure you want to block this user?\n\nOnce blocked, you will not be able to match up with this user in the future and" +
                                " he/she will be removed from your friend list.\n\nAlso, you will not be able to make appointments with this user or chat with him/her anymore.")
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                blacklistRef.child(MainActivity.uid).child(selectedFriend.uid).setValue("");
                                blacklistRef.child(selectedFriend.uid).child(MainActivity.uid).setValue("");
                                contactRef.child(MainActivity.uid).child(selectedFriend.uid).removeValue();
                                contactRef.child(selectedFriend.uid).child(MainActivity.uid).removeValue();

                                apptRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d("ZZZ", "CHECKING APPT");
                                        for (DataSnapshot apptSnapshot : dataSnapshot.child(MainActivity.uid).getChildren()) {
                                            if (apptSnapshot.child("apptPartyID").getValue(String.class).equals(selectedFriend.uid)) {
                                                Log.d("ZZZ", "APPT FOUND!");
                                                apptRef.child(MainActivity.uid).child(apptSnapshot.getKey()).removeValue();
                                            }
                                        }

                                        for(DataSnapshot apptSnapshot : dataSnapshot.child(selectedFriend.uid).getChildren()) {
                                            if (apptSnapshot.child("apptPartyID").getValue(String.class).equals(MainActivity.uid)) {
                                                Log.d("ZZZ", "APPT FOUND!");
                                                apptRef.child(selectedFriend.uid).child(apptSnapshot.getKey()).removeValue();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                FriendsActivity.friendsList.remove(FriendsActivity.friendsList.indexOf(selectedFriend));
                                FriendsActivity.refreshList();
                                finish();
                            }
                        })
                        .show();
            }
        });
    }
}
