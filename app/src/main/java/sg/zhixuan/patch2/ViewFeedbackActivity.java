package sg.zhixuan.patch2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackActivity extends AppCompatActivity {

    RecyclerView rvFeedbacks;
    FeedbackAdapter feedbackAdapter;
    DatabaseReference feedbackRef;
    Button btnViewFeedbacksToFriendInfoActivity;
    static User selectedUser;
    List<String> feedbackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        btnViewFeedbacksToFriendInfoActivity = (Button)findViewById(R.id.btnViewFeedbacksToFriendInfoActivity);
        rvFeedbacks = (RecyclerView)findViewById(R.id.rvFeedbacks);

        feedbackList = new ArrayList<String>();
        feedbackRef = FirebaseDatabase.getInstance().getReference().child("ratingfeedback").child(selectedUser.uid).child("feedback");
        feedbackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feedbackList.clear();
                for (DataSnapshot feedbackSnapshot : dataSnapshot.getChildren()) {
                    feedbackList.add(feedbackSnapshot.getValue(String.class));
                }

                feedbackAdapter = new FeedbackAdapter(ViewFeedbackActivity.this, feedbackList);
                rvFeedbacks.setLayoutManager(new LinearLayoutManager(ViewFeedbackActivity.this));
                rvFeedbacks.setItemAnimator(new DefaultItemAnimator());
                rvFeedbacks.setAdapter(feedbackAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnViewFeedbacksToFriendInfoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
