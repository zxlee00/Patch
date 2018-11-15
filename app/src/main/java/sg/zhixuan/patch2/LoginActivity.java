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
    Boolean found = false;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = etPhoneNumber.getText().toString().trim();
                if (!phoneNumber.isEmpty() && phoneNumber.length() == 8 && TextUtils.isDigitsOnly(phoneNumber)) {

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                if (userSnapshot.child("phoneNumber").getValue(String.class).equals("+65"+phoneNumber)) {
                                    //user exists
                                    finish();
                                    Intent intent = new Intent(LoginActivity.this, LoginVerifyActivity.class);
                                    intent.putExtra("phoneNumber", "+65" + phoneNumber);

                                    startActivity(intent);
                                    Log.d(TAG, "onDataChange: user exists");
                                    found = true;
                                }
                            }

                            if (found == false) {
                                //user does not exist
                                etPhoneNumber.setError("Invalid phone number.");
                                etPhoneNumber.requestFocus();
                                Log.d(TAG, "onDataChange: user does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                        }
                    });


                } else {
                    etPhoneNumber.setError("Enter a valid phone number.");
                    etPhoneNumber.requestFocus();
                }
            }
        });
    }
}
