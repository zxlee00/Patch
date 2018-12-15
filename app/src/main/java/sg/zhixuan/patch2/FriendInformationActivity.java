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

    TextView friendname, friendgender, friendage, friendhobby, friendrating, friends;
    Button btnFeedback, btnBlock, btnDeleteFriend, btnViewFeedbacks, btnFriendInfoToFriend;
    ImageView friendimg;
    static String name, gender, age, hobby = "", imgurl;
    static User selectedFriend;
    DatabaseReference contactRef;
    DatabaseReference ratingRef;
    DatabaseReference blacklistRef;
    DatabaseReference apptRef;
    DatabaseReference messagesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_information);

        friendage = (TextView)findViewById(R.id.friendage);
        friendgender = (TextView)findViewById(R.id.friendgender);
        friendhobby = (TextView)findViewById(R.id.friendhobby);
        friendname = (TextView)findViewById(R.id.friendname);
        friendimg = (ImageView)findViewById(R.id.friendimg);
        friends = (TextView)findViewById(R.id.friends);
        btnBlock = (Button)findViewById(R.id.btnBlock);
        btnFeedback = (Button)findViewById(R.id.btnFeedback);
        btnDeleteFriend = (Button)findViewById(R.id.btnDeleteFriend);
        btnViewFeedbacks = (Button)findViewById(R.id.btnViewFeedbacks);
        btnFriendInfoToFriend = (Button)findViewById(R.id.btnFriendInfoToFriend);
        friendrating = (TextView)findViewById(R.id.friendrating);

        setChineseLanguage();

        contactRef = FirebaseDatabase.getInstance().getReference().child("contacts");
        blacklistRef = FirebaseDatabase.getInstance().getReference().child("blacklist");
        apptRef = FirebaseDatabase.getInstance().getReference().child("appointments");
        messagesRef = FirebaseDatabase.getInstance().getReference().child("messages");

        friendgender.append(gender);
        friendname.setText(name);
        friendage.append(age);
        String hobbystr = "Hobby:";
        if (MainActivity.language.equals("Chinese"))
            hobbystr = "兴趣/爱好：";
        friendhobby.setText(hobbystr + hobby);

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

                String title = "Delete Friend";
                String message = "Are you sure you want to delete this friend?\nYou will not be able to contact him/her anymore.";
                String negbtn = "CANCEL";
                String posbtn = "CONFIRM";

                if (MainActivity.language.equals("Chinese")) {
                    title = "删除好友";
                    message = "确定删除这位好友吗？\n请注意，如果确定的话，您将无法与这位用户联系了。";
                    negbtn = "取消";
                    posbtn = "确定";
                }

                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setNegativeButton(negbtn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(posbtn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                contactRef.child(MainActivity.uid).child(selectedFriend.uid).removeValue();
                                contactRef.child(selectedFriend.uid).child(MainActivity.uid).removeValue();
                                messagesRef.child(MainActivity.uid + "_" + selectedFriend.uid).removeValue();
                                messagesRef.child(selectedFriend.uid + "_" + MainActivity.uid).removeValue();

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

                String title = "Block User";
                String message = "Are you sure you want to block this user?\n\nOnce blocked, you will not be able to match up with this user in the future and" +
                        " he/she will be removed from your friend list.\n\nAlso, you will not be able to make appointments with this user or chat with him/her anymore.";
                String negbtn = "CANCEL";
                String posbtn = "CONFIRM";

                if(MainActivity.language.equals("Chinese")) {
                    title = "拉黑用户";
                    message = "您确定拉黑这位用户吗？\n\n一旦拉黑了，您将无法与这用户配对了。" +
                            "他/她将会从您的好友列表中除去。\n\n此外，您再也不能跟他/她预约或对话了。";
                    negbtn = "取消";
                    posbtn = "确定";
                }

                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setNegativeButton(negbtn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(posbtn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                blacklistRef.child(MainActivity.uid).child(selectedFriend.uid).setValue("");
                                blacklistRef.child(selectedFriend.uid).child(MainActivity.uid).setValue("");
                                contactRef.child(MainActivity.uid).child(selectedFriend.uid).removeValue();
                                contactRef.child(selectedFriend.uid).child(MainActivity.uid).removeValue();
                                messagesRef.child(MainActivity.uid + "_" + selectedFriend.uid).removeValue();
                                messagesRef.child(selectedFriend.uid + "_" + MainActivity.uid).removeValue();

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

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            friendgender.setText("性别：");
            friendage.setText("年龄：");
            friendrating.setText("评分：");
            friends.setText("好友个人资料");
            btnBlock.setText("拉黑");
            btnDeleteFriend.setText("删除好友");
            btnViewFeedbacks.setText("查看反馈");
            btnFeedback.setText("反馈");
        }
    }
}
