package sg.zhixuan.patch2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

    DatabaseReference userReference, matchupReference, contactReference;
    List<User> selectedUsersList, sameHobbyUsersList;
    List<String> userHobbyList, findingMatchupList, alreadyMatchedupList, contactList;
    String userHobbies;
    User matchedUpUser;
    ProgressBar progress_bar;
    String sameHobbies = "";
    TextView txtSameHobbies, txtMatchedUpUserHobby, txtSuccessful, txtQn, txtName, matchup;
    Button btnYes, btnNo;
    String userName;
    Boolean matched = false;
    LinearLayout failure;
    Button btnSure;

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
        matchup = (TextView)findViewById(R.id.matchup);
        txtName = (TextView)findViewById(R.id.txtName);
        btnNo = (Button)findViewById(R.id.btnNo);
        btnYes = (Button)findViewById(R.id.btnYes);
        progress_bar = (ProgressBar)findViewById(R.id.progress_bar);
        findingMatchupList = new ArrayList<String>();
        selectedUsersList = new ArrayList<User>();
        sameHobbyUsersList = new ArrayList<User>();
        alreadyMatchedupList = new ArrayList<String>();
        contactList = new ArrayList<String>();
        matchupReference = FirebaseDatabase.getInstance().getReference().child("matchup");

        userReference = FirebaseDatabase.getInstance().getReference().child("users");
        contactReference = FirebaseDatabase.getInstance().getReference().child("contacts");
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

                contactReference.child(MainActivity.uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                        for (DataSnapshot contactSnapshot : snapshot2.getChildren()) {
                            contactList.add(contactSnapshot.getKey());
                            Log.d("ZZZ", "CONTACT:" + contactSnapshot.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                for (final DataSnapshot userSnapshot : dataSnapshot.child("lookingForMatchup").getChildren()) {
                    if (!userSnapshot.getKey().equals(MainActivity.uid)) {
                        Log.d("ZZZ", "CHECK SNAP:" + userSnapshot.getKey());

                        findingMatchupList.add(userSnapshot.getKey());
                        Log.d("ZZZ", "PASSED2");
                    }
                }

                userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userHobbies = snapshot.child(MainActivity.uid).child("hobby").getValue(String.class);
                        userName = snapshot.child(MainActivity.uid).child("name").getValue(String.class);
                        userHobbyList = new ArrayList<String>(Arrays.asList(userHobbies.split(",")));
                        Log.d("ZZZ", "PASSED1");

                        for (int y = 0; y < findingMatchupList.size();y++) {
                            selectedUsersList.add(snapshot.child(findingMatchupList.get(y)).getValue(User.class));
                        }

                        for (int x = 0; x < userHobbyList.size(); x++) {
                            Log.d("ZZZ", "HOBBY:" + userHobbyList.get(x));
                            for (int i = 0; i < selectedUsersList.size(); i++) {
                                Log.d("ZZZ", "SELECTED USER:" + selectedUsersList.get(i).name);
                                if (selectedUsersList.get(i).hobby.contains(userHobbyList.get(x))) {
                                    if(alreadyMatchedupList.contains(selectedUsersList.get(i).uid) || contactList.contains(selectedUsersList.get(i).uid)) {
                                        Log.d("ZZZ", "FRIEND OR ALREAD MATCHED UP:" + selectedUsersList.get(i).name);
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
                                txtSuccessful.setVisibility(View.VISIBLE);
                                txtQn.setVisibility(View.VISIBLE);
                                btnNo.setVisibility(View.VISIBLE);
                                btnYes.setVisibility(View.VISIBLE);
                                txtName.setVisibility(View.VISIBLE);
                                txtMatchedUpUserHobby.setVisibility(View.VISIBLE);
                                txtSameHobbies.setVisibility(View.VISIBLE);
                                matchup.setVisibility(View.VISIBLE);

                                sameHobbies = "";
                                for (int c = 0; c < userHobbyList.size(); c++) {
                                    Log.d("ZZZ", "EACH HOBBY:" + userHobbyList.get(c));
                                    if (matchedUpUser.hobby.contains(userHobbyList.get(c))) {
                                        Log.d("ZZZ", "SAME HOBBY:" + userHobbyList.get(c));
                                        if (sameHobbies != "")
                                            sameHobbies = sameHobbies + ", " + userHobbyList.get(c);
                                        else
                                            sameHobbies = userHobbyList.get(c);
                                    }
                                }
                                txtName.setText(matchedUpUser.name);
                                txtSameHobbies.setText("Same Hobbies:\n" + sameHobbies);
                                txtMatchedUpUserHobby.setText("Matched up user has these hobbies:\n" + matchedUpUser.hobby);
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

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                matchupReference.child(MainActivity.uid).child(matchedUpUser.uid).child("name").setValue(matchedUpUser.name);
                matchupReference.child(MainActivity.uid).child(matchedUpUser.uid).child("type").setValue("foundByUser");
  //              matchupReference.child(matchedUpUser.uid).child(MainActivity.uid).child("name").setValue(userName);
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
    }
}
