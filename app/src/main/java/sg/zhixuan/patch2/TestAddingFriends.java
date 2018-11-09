package sg.zhixuan.patch2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestAddingFriends extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference mDatabase;
    boolean found = false;
    TextView txtUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adding_friends);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        txtUserID = (TextView)findViewById(R.id.txtUserID);
        final String userID = firebaseUser.getUid();

        //Set a countdown of 5s inthe previous activity otherwise do notrun this activity
        mDatabase.child("temp").child(userID).setValue(userID);

        try
        {
            Thread.sleep(6000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        mDatabase.child("temp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Log.d("ZZZ", userID);
                    //Log.d("ZZZ", uid);
                    if (userSnapshot.getValue(String.class).equals(userID)) {
                        found = false;
                        Log.d("ZZZ", "NOT FOUND");
                    } else {
                        mDatabase.child("contacts").child(userID).child(userSnapshot.getValue(String.class)).child("type").setValue("User");
                        mDatabase.child("temp").child(userSnapshot.getValue(String.class)).removeValue();
                        txtUserID.setText(userSnapshot.getValue(String.class));
                        found = true;
                        TestAddingFriends.this.finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        if (found == false) {
            mDatabase.child("temp").removeValue();
            TestAddingFriends.this.finish();
        }
    }
}