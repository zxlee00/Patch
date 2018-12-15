package sg.zhixuan.patch2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MatchUpUserInformation extends AppCompatActivity {

    static String selectedUID;
    TextView txtMatchUpUserInfoName, txtMatchUpUserInfoAge, txtMatchUpUserInfoGender, txtMatchUpUserInfoRating, txtMatchUpUserInfoHobby, matchupuserinformation;
    Button btnMatchUpUserFeedback, btnMatchUpUserViewFeedback, btnMatchUpUserInfoToMatchUpConversation;
    DatabaseReference userRef, ratingRef;
    User selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_up_user_information);

        txtMatchUpUserInfoName = (TextView)findViewById(R.id.txtMatchedUpUserInfoName);
        txtMatchUpUserInfoHobby = (TextView)findViewById(R.id.txtMatchedUpUserInfoHobby);
        txtMatchUpUserInfoGender = (TextView)findViewById(R.id.txtMatchedUpUserInfoGender);
        txtMatchUpUserInfoAge = (TextView)findViewById(R.id.txtMatchedUpUserInfoAge);
        txtMatchUpUserInfoRating = (TextView)findViewById(R.id.txtMatchedUpUserInfoRating);
        matchupuserinformation = (TextView)findViewById(R.id.matchupuserinformation);

        btnMatchUpUserFeedback = (Button)findViewById(R.id.btnMatchUpUserFeedback);
        btnMatchUpUserInfoToMatchUpConversation = (Button)findViewById(R.id.btnMatchupUserInfoToMatchUpConversation);
        btnMatchUpUserViewFeedback = (Button)findViewById(R.id.btnMatchUpUserViewFeedback);

        setChineseLanguage();

        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                selectedUser = null;
                selectedUser = dataSnapshot.child(selectedUID).getValue(User.class);

                txtMatchUpUserInfoName.setText(selectedUser.name);

                String age = "AGE: ";
                String gender = "GENDER: ";
                String hobbies = "HOBBIES:\n";

                if (MainActivity.language.equals("Chinese")) {
                    age = "年龄：";
                    gender = "性别：";
                    hobbies = "爱好：";
                }

                txtMatchUpUserInfoAge.setText(age + Integer.toString(selectedUser.age));
                txtMatchUpUserInfoGender.setText(gender + selectedUser.gender);
                txtMatchUpUserInfoHobby.setText(hobbies + selectedUser.hobby);

                ratingRef = FirebaseDatabase.getInstance().getReference().child("ratingfeedback").child(selectedUID).child("rating");
                ratingRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String rating = "RATING: ";
                        if (MainActivity.language.equals("Chinese")) {
                            rating = "评分：";
                        }

                        txtMatchUpUserInfoRating.setText("");
                        txtMatchUpUserInfoRating.setText(rating + dataSnapshot.getValue(String.class));
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

        btnMatchUpUserInfoToMatchUpConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMatchUpUserViewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewFeedbackActivity.selectedUser = selectedUser;
                startActivity(new Intent(MatchUpUserInformation.this, ViewFeedbackActivity.class));
            }
        });

        btnMatchUpUserFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewFeedbackActivity.feedbackUser = selectedUser;
                startActivity(new Intent(MatchUpUserInformation.this, NewFeedbackActivity.class));
            }
        });
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            matchupuserinformation.setText("配对用户个人资料");
            btnMatchUpUserViewFeedback.setText("查看反馈");
            btnMatchUpUserFeedback.setText("反馈");
        }
    }
}
