package sg.zhixuan.patch2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MatchUpActivity extends AppCompatActivity {

    DatabaseReference userReference, matchupReference, contactReference, blacklistRef, ratingRef;
    List<User> selectedUsersList, sameHobbyUsersList;
    List<String> userHobbyList, findingMatchupList, alreadyMatchedupList, contactList, blackList;
    String userHobbies;
    User matchedUpUser;
    ProgressBar progress_bar;
    String sameHobbies = "";
    TextView txtSameHobbies, txtMatchedUpUserHobby, txtSuccessful, txtQn, txtName, matchup, txtAge, txtGender, txtRating, viewfeedback, txtFailure;
    Button btnYes, btnNo;
    String userName;
    Boolean matched = false;
    LinearLayout failure;
    ScrollView scrollView;
    RelativeLayout matchupdetails;
    Button btnSure, btnMatchUpViewFeedbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_up);

        btnSure = (Button)findViewById(R.id.btnSure);
        failure = (LinearLayout)findViewById(R.id.failure);
        txtSameHobbies = (TextView)findViewById(R.id.txtSameHobbies);
        txtMatchedUpUserHobby = (TextView)findViewById(R.id.txtMatchedUpUserHobby);
        txtSuccessful = (TextView)findViewById(R.id.txtSuccessful);
        txtQn = (TextView)findViewById(R.id.txtQn);
        txtAge = (TextView)findViewById(R.id.txtAge);
        txtGender = (TextView)findViewById(R.id.txtGender);
        txtRating = (TextView)findViewById(R.id.txtRating);
        matchup = (TextView)findViewById(R.id.matchup);
        txtName = (TextView)findViewById(R.id.txtName);
        btnNo = (Button)findViewById(R.id.btnNo);
        btnYes = (Button)findViewById(R.id.btnYes);
        txtFailure = (TextView)findViewById(R.id.txtFailure);
        btnMatchUpViewFeedbacks = (Button)findViewById(R.id.btnMatchUpViewFeedbacks);
        progress_bar = (ProgressBar)findViewById(R.id.progress_bar);
        scrollView = (ScrollView)findViewById(R.id.scrollview);
        matchupdetails = (RelativeLayout)findViewById(R.id.matchupdetails);
        viewfeedback = (TextView)findViewById(R.id.viewfeedback);
        findingMatchupList = new ArrayList<String>();
        selectedUsersList = new ArrayList<User>();
        sameHobbyUsersList = new ArrayList<User>();
        alreadyMatchedupList = new ArrayList<String>();
        blackList = new ArrayList<String>();
        contactList = new ArrayList<String>();
        matchupReference = FirebaseDatabase.getInstance().getReference().child("matchup");

        setChineseLanguage();

        userReference = FirebaseDatabase.getInstance().getReference().child("users");
        contactReference = FirebaseDatabase.getInstance().getReference().child("contacts");
        blacklistRef = FirebaseDatabase.getInstance().getReference().child("blacklist").child(MainActivity.uid);
        ratingRef = FirebaseDatabase.getInstance().getReference().child("ratingfeedback");
        matchupReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("AAA", "DATA CHANGED");
                findingMatchupList.clear();
                selectedUsersList.clear();
                sameHobbyUsersList.clear();
                alreadyMatchedupList.clear();
                contactList.clear();
                matched = false;
                matchedUpUser = null;

                for (DataSnapshot matchedupedSnapshot : dataSnapshot.child(MainActivity.uid).getChildren()) {
                    alreadyMatchedupList.add(matchedupedSnapshot.getKey());
                    Log.d("ZZZ", "ALREADY MATCHED UP:" + matchedupedSnapshot.getKey());
                }

                for (final DataSnapshot userSnapshot : dataSnapshot.child("lookingForMatchup").getChildren()) {
                    if (!userSnapshot.getKey().equals(MainActivity.uid)) {
                        Log.d("ZZZ", "CHECK SNAP:" + userSnapshot.getKey());

                        findingMatchupList.add(userSnapshot.getKey());
                        Log.d("ZZZ", "PASSED2");
                    }
                }

                userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot snapshot) {
                        userHobbies = snapshot.child(MainActivity.uid).child("hobby").getValue(String.class);
                        userName = snapshot.child(MainActivity.uid).child("name").getValue(String.class);
                        userHobbyList = new ArrayList<String>(Arrays.asList(userHobbies.split(",")));
                        Log.d("ZZZ", "PASSED1");

                        contactReference.child(MainActivity.uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot snapshot2) {
                                contactList.clear();
                                blackList.clear();

                                blacklistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snaps) {
                                        for (DataSnapshot blacklistSnapshot : snaps.getChildren()) {
                                            blackList.add(blacklistSnapshot.getKey());
                                            Log.d("ZZZ", "BLACKLISTED USErS:" + blacklistSnapshot.getKey());
                                        }

                                        for (DataSnapshot contactSnapshot : snapshot2.getChildren()) {
                                            contactList.add(contactSnapshot.getKey());
                                            Log.d("ZZZ", "CONTACT:" + contactSnapshot.getKey());
                                        }

                                        for (int y = 0; y < findingMatchupList.size();y++) {
                                            selectedUsersList.add(snapshot.child(findingMatchupList.get(y)).getValue(User.class));
                                        }

                                        for (int x = 0; x < userHobbyList.size(); x++) {
                                            Log.d("ZZZ", "HOBBY:" + userHobbyList.get(x));
                                            for (int i = 0; i < selectedUsersList.size(); i++) {
                                                Log.d("ZZZ", "SELECTED USER:" + selectedUsersList.get(i).name);
                                                if (selectedUsersList.get(i).hobby.contains(userHobbyList.get(x))) {
                                                    if(alreadyMatchedupList.contains(selectedUsersList.get(i).uid) || contactList.contains(selectedUsersList.get(i).uid) || blackList.contains(selectedUsersList.get(i).uid)) {
                                                        Log.d("ZZZ", "FRIEND OR ALREADY MATCHED UP OR BLOCKED:" + selectedUsersList.get(i).name);
                                                    } else {
                                                        Log.d("ZZZ", "OK FRIENDS:" + selectedUsersList.get(i).name);
                                                        sameHobbyUsersList.add(selectedUsersList.get(i));
                                                    }
                                                }
                                            }
                                        }

                                        if (sameHobbyUsersList.size() == 0) {
                                            Log.d("ZZZ", "No Users Found");
                                        } else {
                                            matchedUpUser = null;
                                            matchedUpUser = sameHobbyUsersList.get(new Random().nextInt(sameHobbyUsersList.size()));
                                            Log.d("ZZZ", "MATCHED USER:" + matchedUpUser.name);
                                        }

                                        //matchupReference.child("lookingForMatchup").child(MainActivity.uid).removeValue();

                                        if (progress_bar != null) {
                                            progress_bar.setVisibility(View.GONE);

                                            if (matchedUpUser == null) {
                                                failure.setVisibility(View.VISIBLE);
                                                matchup.setVisibility(View.VISIBLE);
                                            } else {
                                                matchupdetails.setVisibility(View.VISIBLE);
                                                scrollView.setVisibility(View.VISIBLE);
                                                matchup.setVisibility(View.VISIBLE);

                                                sameHobbies = "";
                                                for (int c = 0; c < userHobbyList.size(); c++) {
                                                    if (matchedUpUser.hobby.contains(userHobbyList.get(c))) {
                                                        if (sameHobbies != "")
                                                            sameHobbies = sameHobbies + ", " + userHobbyList.get(c);
                                                        else
                                                            sameHobbies = userHobbyList.get(c);
                                                    }
                                                }

                                                ratingRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        String rating = "Rating: ";
                                                        if (MainActivity.language.equals("Chinese")) {
                                                            rating = "评分：";
                                                        }
                                                        txtRating.setText(rating + dataSnapshot.child(matchedUpUser.uid).child("rating").getValue(String.class));
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                                txtName.setText(matchedUpUser.name);
                                                txtAge.append(String.valueOf(matchedUpUser.age));
                                                txtGender.append(String.valueOf(matchedUpUser.gender));

                                                String strSameHobbies = "Same Hobbies:\n";
                                                String strMatchedUpUserHobbies = "Matched up user has these hobbies:\n";
                                                if (MainActivity.language.equals("Chinese")) {
                                                    strSameHobbies = "相同的爱好：\n";
                                                    strMatchedUpUserHobbies = "配对到的用户有这些爱好：\n";
                                                }

                                                txtSameHobbies.setText(strSameHobbies + sameHobbies);
                                                txtMatchedUpUserHobby.setText(strMatchedUpUserHobbies + matchedUpUser.hobby);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchupReference.child(MainActivity.uid).child(matchedUpUser.uid).child("type").setValue("foundByUser");
                matchupReference.child(matchedUpUser.uid).child(MainActivity.uid).child("type").setValue("userFoundByOthers");
                MatchUpActivity.this.finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMatchUpViewFeedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewFeedbackActivity.selectedUser = matchedUpUser;
                startActivity(new Intent(MatchUpActivity.this, ViewFeedbackActivity.class));
            }
        });
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            matchup.setText("配对");
            txtSuccessful.setText("根据您的爱好，我们为您找到了一位用户。");
            txtAge.setText("年龄：");
            txtGender.setText("性别：");
            txtRating.setText("评分");
            viewfeedback.setText("在接受之前，您也许会想要查看有关此用户的一些反馈。");
            btnMatchUpViewFeedbacks.setText("查看反馈");
            txtQn.setText("与这位用户配对吗？");
            btnYes.setText("同意");
            btnNo.setText("拒绝");
            txtFailure.setText("此刻暂时没有其他用户在寻找配对，请稍后再重试。");
            btnSure.setText("知道了！");
        }
    }
}
