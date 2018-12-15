package sg.zhixuan.patch2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchupConversationActivity extends AppCompatActivity {

    DatabaseReference matchedupUsersRef, userRef;
    List<MatchUpUser> matchedUpUsersList;
    RecyclerView rvMatchedUpList;
    MatchUpListAdapter matchUpListAdapter;
    Button btnCheckRequest;
    TextView conversation, txtfoundbyyou, txtotherpartyfoundyou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchup_conversation);

        matchedUpUsersList = new ArrayList<MatchUpUser>();
        rvMatchedUpList = (RecyclerView)findViewById(R.id.rvMatchUpList);
        btnCheckRequest = (Button)findViewById(R.id.btnCheckRequests);
        conversation = (TextView)findViewById(R.id.conversation);
        txtfoundbyyou = (TextView)findViewById(R.id.txtfoundbyyou);
        txtotherpartyfoundyou = (TextView)findViewById(R.id.txtotherpartyfoundyou);

        setChineseLanguage();

        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        matchedupUsersRef = FirebaseDatabase.getInstance().getReference().child("matchup").child(MainActivity.uid);
        matchedupUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                matchedUpUsersList.clear();

                for (final DataSnapshot matchupSnapshot : dataSnapshot.getChildren()) {
                    userRef.child(matchupSnapshot.getKey()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            matchedUpUsersList.add(new MatchUpUser(snapshot.getValue(String.class),
                                    matchupSnapshot.getKey(),
                                    matchupSnapshot.child("type").getValue(String.class)));
                            Log.d("ZZZ", "CHECK ID:" + snapshot.getValue(String.class));
                            Log.d("ZZZ", "TYPE:" + matchupSnapshot.child("type").getValue(String.class));
                            Log.d("ZZZ", "KEY:" + matchupSnapshot.getKey());

                            matchUpListAdapter = new MatchUpListAdapter(MatchupConversationActivity.this, matchedUpUsersList);
                            rvMatchedUpList.setLayoutManager(new LinearLayoutManager(MatchupConversationActivity.this));
                            rvMatchedUpList.setItemAnimator(new DefaultItemAnimator());
                            rvMatchedUpList.setAdapter(matchUpListAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnCheckRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MatchupConversationActivity.this, RequestActivity.class));
            }
        });
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            conversation.setText("对话");
            btnCheckRequest.setText("查看好友请求");
            txtfoundbyyou.setText("您寻找到的用户");
            txtotherpartyfoundyou.setText("对方寻找到你的用户");
        }
    }
}
