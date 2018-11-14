package sg.zhixuan.patch2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainAccountActivity extends AppCompatActivity {

    private static final String TAG = "MainAccountActivity";
    static FirebaseUser mUser;
    static FirebaseAuth mAuth;
    static User user = new User();
    String mUserKey;
    DatabaseReference mUserReference;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account);

        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    alertDialog = new AlertDialog.Builder(MainAccountActivity.this);
                    alertDialog.setTitle("Sign Out")
                            .setMessage("You are currently logged in to another account. If you want to proceed on with sign up, you will be logged out of your current account. Are you sure?")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth.getInstance().signOut();

                                    Intent intent = new Intent(MainAccountActivity.this, NameAgeActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();

                } else {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainAccountActivity.this, NameAgeActivity.class));
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                mUser = mAuth.getCurrentUser();

                if (mUser != null) {
                    mUserKey = mUser.getUid();
                    mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(mUserKey);

                    mUserReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            user = dataSnapshot.getValue(User.class);
                            finish();
                            Log.d(TAG, "onCreate: " + user.getName() + " " +  mUser.getUid());
                            Intent intent = new Intent(MainAccountActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: ", databaseError.toException());
                        }
                    });
                } else {
                    startActivity(new Intent(MainAccountActivity.this, LoginActivity.class));
                }
            }
        });
    }
}