package sg.zhixuan.patch2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;

public class NewFeedbackActivity extends AppCompatActivity {

    SeekBar ratingSeekBar;
    TextView txtReportUserName, txtSeekbarValue, txtFeedback, feedback, userRating, feedbackprompt;
    static User feedbackUser;
    Button btnSubmitReport, btnNewFeedbackToFriendsActivity;
    DatabaseReference ratingRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feedback);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ratingSeekBar = (SeekBar)findViewById(R.id.ratingSeekBar);
        txtReportUserName = (TextView)findViewById(R.id.txtReportUserName);
        txtSeekbarValue = (TextView)findViewById(R.id.txtSeekbarValue);
        txtFeedback = (TextView)findViewById(R.id.txtFeedback);
        btnSubmitReport = (Button)findViewById(R.id.btnSubmitReport);
        ratingRef = FirebaseDatabase.getInstance().getReference().child("ratingfeedback");
        btnNewFeedbackToFriendsActivity = (Button)findViewById(R.id.btnNewFeedbackToFriendsActivity);
        feedback = (TextView)findViewById(R.id.feedback);
        userRating = (TextView)findViewById(R.id.userRating) ;
        feedbackprompt = (TextView)findViewById(R.id.feedbackprompt);

        txtReportUserName.setText(feedbackUser.name);

        setChineseLanguage();

        ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtSeekbarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtFeedback.getText())) {

                    String error = "Please enter a feedback on this user.";
                    if (MainActivity.language.equals("Chinese")) {
                        error = "请输入有关此用户的反馈。";
                    }

                    txtFeedback.setError(error);
                } else {
                    ratingRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            BigDecimal currentRating = new BigDecimal(dataSnapshot.child(feedbackUser.uid).child("rating").getValue(String.class));
                            Log.d("ZZZ", "CURRENT RATING:" + currentRating.toString());

                            BigDecimal feedbackRating = new BigDecimal(txtSeekbarValue.getText().toString());
                            BigDecimal newRating = (currentRating.add(feedbackRating)).divide(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                            Log.d("ZZZ","NEW RATING:" + newRating.toString());

                            ratingRef.child(feedbackUser.uid).child("rating").setValue(newRating.toPlainString());

                            ratingRef.child(feedbackUser.uid).child("feedback").push().setValue(txtFeedback.getText().toString());

                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        btnNewFeedbackToFriendsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            feedbackprompt.setText("您对此用户有什么反馈呢？");
            feedback.setText("反馈");
            userRating.setText("评分");
            btnSubmitReport.setText("提交");
        }
    }
}
