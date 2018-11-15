package sg.zhixuan.patch2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PhoneNumberActivity extends AppCompatActivity {

    private static final String TAG = "PhoneNumberActivity";
    EditText etPhoneNumber;
    Button btnBack, btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneNumber = etPhoneNumber.getText().toString().trim();

                if (!phoneNumber.isEmpty() && phoneNumber.length() == 8 && TextUtils.isDigitsOnly(phoneNumber)) {

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                    mDatabase.orderByChild("phoneNumber").equalTo("+65" + phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                //user exists
                                etPhoneNumber.setError("Phone number already in use.");
                                etPhoneNumber.requestFocus();
                                Log.d(TAG, "onDataChange: user exists");
                            } else {
                                //user does not exist
                                MainAccountActivity.user.setPhoneNumber("+65" + phoneNumber);
                                Intent intent = new Intent(PhoneNumberActivity.this, VerificationActivity.class);
                                startActivity(intent);
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
