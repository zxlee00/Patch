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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LoginVerifyActivity extends AppCompatActivity {

    private static final String TAG = "LoginVerifyActivity";
    EditText etCode;
    Button btnBack, btnNext;
    String phoneNumber, code, verificationCode;
    String mUserKey;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase, mUserReference;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verify);

        etCode = (EditText) findViewById(R.id.etCode);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(LoginVerifyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        sendCode(phoneNumber);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginVerifyActivity.this, LoginActivity.class));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = etCode.getText().toString().trim();
                if (!code.isEmpty() &&
                        code.length() == 6 &&
                        TextUtils.isDigitsOnly(code)) {
                    verifyCode(code);
                } else {
                    etCode.setError("Enter a valid code.");
                    etCode.requestFocus();
                }
            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, code);
        if (credential == null) {
            Log.d("ZZZ", "CREDENTIAL IS NULL");
        }
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    MainAccountActivity.mUser = task.getResult().getUser();
                    mUserKey = MainAccountActivity.mUser.getUid();
                    mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(mUserKey);

                    mUserReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            MainAccountActivity.user = dataSnapshot.getValue(User.class);
                            Log.d(TAG, "onComplete: " + MainAccountActivity.user.getName() + MainAccountActivity.mUser.getPhoneNumber());

                            finish();
                            Intent intent = new Intent(LoginVerifyActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: ", databaseError.toException());
                        }
                    });
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        etCode.setError("Invalid code, new code will be sent.");
                        etCode.requestFocus();
                        sendCode(phoneNumber);
                    } else {
                        Toast.makeText(LoginVerifyActivity.this, "Wrong verification code, please try again.", Toast.LENGTH_LONG).show();
                        //Log.d(TAG, "onComplete: ", task.getException());
                    }
                }
            }
        });
    }

    private void sendCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallback);
    }
}