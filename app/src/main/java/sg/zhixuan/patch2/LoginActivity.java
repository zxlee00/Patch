package sg.zhixuan.patch2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    EditText etPhoneNumber;
    Button btnBack, btnNext;
    String phoneNumber;
    String mUserKey;
    DatabaseReference mDatabase, mUserReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        MainAccountActivity.mUser = mAuth.getCurrentUser();
        if (MainAccountActivity.mUser != null) {
            mUserKey = MainAccountActivity.mUser.getUid();
            mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(mUserKey);

            ValueEventListener userListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    MainAccountActivity.user = dataSnapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "onCancelled: ", databaseError.toException());
                }
            };
            mUserReference.addValueEventListener(userListener);
            Log.d(TAG, "onCreate: " + MainAccountActivity.user + MainAccountActivity.mUser.getUid());
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainAccountActivity.class));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = etPhoneNumber.getText().toString().trim();
                if (!phoneNumber.isEmpty() &&
                        phoneNumber.length() == 8 &&
                        TextUtils.isDigitsOnly(phoneNumber)
                        ) {
                    Intent intent = new Intent(LoginActivity.this, LoginVerifyActivity.class);
                    intent.putExtra("phoneNumber", "+65" + phoneNumber);

                    startActivity(intent);
                } else {
                    etPhoneNumber.setError("Enter a valid phone number.");
                    etPhoneNumber.requestFocus();
                    return;
                }
            }
        });
    }


}
