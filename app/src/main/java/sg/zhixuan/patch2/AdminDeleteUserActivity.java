package sg.zhixuan.patch2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.ArrayList;
import java.util.List;

public class AdminDeleteUserActivity extends AppCompatActivity {

    RecyclerView rvDeleteUser;
    DatabaseReference matchupRef, userRef;
    static List<User> userList = new ArrayList<>();
    static AdminDeleteUserAdapter adapter;

    private static final String TAG = "AdminDeleteUserActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_deleteuser);

        rvDeleteUser = findViewById(R.id.rvDeleteUser);

        userList.clear();

        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot allUserSnapshot) {

                matchupRef = FirebaseDatabase.getInstance().getReference().child("matchup").child("lookingForMatchup");
                matchupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot matchupSnapshot) {
                        for (DataSnapshot uidSnapshot: matchupSnapshot.getChildren()) {
                            String uid = uidSnapshot.getKey();

                            for (DataSnapshot userSnapshot: allUserSnapshot.getChildren()) {
                                String date = userSnapshot.child("lastSignedIn").getValue().toString();
                                User user = userSnapshot.getValue(User.class);

                                if (userSnapshot.getKey().contains(uid)) {
                                    if (checkDate(date)) {
                                        userList.add(user);
                                    }
                                }
                            }
                        }

                        adapter = new AdminDeleteUserAdapter(AdminDeleteUserActivity.this, userList);
                        rvDeleteUser.setLayoutManager(new LinearLayoutManager(AdminDeleteUserActivity.this));
                        rvDeleteUser.setAdapter(adapter);
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

    public boolean checkDate(String date) {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder()
                .appendDayOfMonth(2)
                .appendLiteral('/')
                .appendMonthOfYear(2)
                .appendLiteral('/')
                .appendYear(4, 4)
                .toFormatter();
        DateTime dt = fmt.parseDateTime(date);
        DateTime now = new DateTime();

        if (dt.plusMonths(6).isBefore(now)) {
            return true;
        } else {
            return false;
        }
    }
}
