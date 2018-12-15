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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    private static final String TAG = "VerificationActivity";
    EditText etCode;
    Button btnBack, btnNext;
    String verificationCode;
    TextView signuptext3, txtVerificationCode2;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    static DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        etCode = (EditText) findViewById(R.id.etCode);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        txtVerificationCode2 = (TextView)findViewById(R.id.txtVerificationCode2);
        signuptext3 = (TextView)findViewById(R.id.signuptext3);

        MainAccountActivity.mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setChineseLanguage();

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Log.d(TAG, "onCodeSent: success " + verificationCode);
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
                Toast.makeText(VerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        String phoneNumber = MainAccountActivity.user.getPhoneNumber();
        sendCode(phoneNumber);
        Log.d(TAG, "onCreate: " + phoneNumber);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = etCode.getText().toString().trim();
                if (!code.isEmpty() &&
                        code.length() == 6 &&
                        TextUtils.isDigitsOnly(code)) {
                    verifyCode(code);
                } else {
                    String error1 = "";
                    if(MainActivity.language.equals("Chinese")) {
                        error1 = "请输入一个有效的验证码。";
                    } else if (MainActivity.language.equals("English")) {
                        error1 = "Enter a valid code.";
                    }
                    etCode.setError(error1);
                    etCode.requestFocus();
                    return;
                }
            }
        });
    }

    private void writeNewUser(User user) {
        mDatabase.child("users").child(user.getUid()).setValue(user);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        MainAccountActivity.mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(VerificationActivity.this, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    MainAccountActivity.mUser = task.getResult().getUser();
                    MainAccountActivity.user.setUid(MainAccountActivity.mUser.getUid());
                    writeNewUser(MainAccountActivity.user);

                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                            .setDisplayName(MainAccountActivity.user.getName())
                            .build();

                    MainAccountActivity.mUser.updateProfile(profileUpdate)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "onComplete: updated display name");
                                    }
                                }
                            });

                    startActivity(intent);
                } else {
                    String error2 = "";
                    if(MainActivity.language.equals("Chinese")) {
                        error2 = "您输入的验证码是不正确的，请重新试一下。";
                    } else if (MainActivity.language.equals("English")) {
                        error2 = "Wrong verification code, please try again.";
                    }
                    Toast.makeText(VerificationActivity.this, error2, Toast.LENGTH_LONG).show();
                    //Log.d(TAG, "onComplete: " + task.getException().getMessage());
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

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            signuptext3.setText("注册");
            txtVerificationCode2.setText("验证码");
            btnBack.setText("上一步");
            btnNext.setText("下一步");
        }
    }
}