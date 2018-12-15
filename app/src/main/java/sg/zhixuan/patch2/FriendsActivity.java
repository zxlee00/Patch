package sg.zhixuan.patch2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {

    RecyclerView rvFriends;
    DatabaseReference userRef;
    TextView friends1, hometext;
    LinearLayout btnFriendsToHome;
    static List<User> friendsList = new ArrayList<User>();
    static FriendsAdapter friendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        friends1 = (TextView)findViewById(R.id.friends1);
        hometext = (TextView)findViewById(R.id.hometext2);
        rvFriends = (RecyclerView)findViewById(R.id.rvFriends);
        btnFriendsToHome = (LinearLayout)findViewById(R.id.btnFriendsToHome);

        setChineseLanguage();

        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                friendsList.clear();

                DatabaseReference contactRef = FirebaseDatabase.getInstance().getReference().child("contacts").child(MainActivity.uid);
                contactRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot contactSnapshot : snapshot.getChildren()) {
                            friendsList.add(dataSnapshot.child(contactSnapshot.getKey()).getValue(User.class));
                        }

                        friendsAdapter = new FriendsAdapter(FriendsActivity.this, friendsList);
                        rvFriends.setLayoutManager(new LinearLayoutManager(FriendsActivity.this));
                        rvFriends.setItemAnimator(new DefaultItemAnimator());
                        rvFriends.setAdapter(friendsAdapter);
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

        btnFriendsToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void refreshList() {
        friendsAdapter.changeList(friendsList);
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            hometext.setText("主页");
            friends1.setText("好友列表");
        }
    }
}
