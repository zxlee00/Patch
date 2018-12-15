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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PhoneNumberActivity extends AppCompatActivity {

    private static final String TAG = "PhoneNumberActivity";
    EditText etPhoneNumber;
    Button btnBack, btnNext;
    TextView signuptext2, txtphoneno2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        signuptext2 = (TextView)findViewById(R.id.signuptext2);
        txtphoneno2 = (TextView)findViewById(R.id.txtphoneno2);

        setChineseLanguage();

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
                                String error1 = "";
                                if(MainActivity.language.equals("Chinese")) {
                                    error1 = "您输入的电话号码已经在使用中了，请重新输入。";
                                } else if (MainActivity.language.equals("English")) {
                                    error1 = "Phone number already in use.";
                                }
                                etPhoneNumber.setError(error1);
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
                    String error2 = "";
                    if(MainActivity.language.equals("Chinese")) {
                        error2 = "请您输入一个有效的电话号码。";
                    } else if (MainActivity.language.equals("English")) {
                        error2 = "Enter a valid phone number.";
                    }
                    etPhoneNumber.setError(error2);
                    etPhoneNumber.requestFocus();
                }
            }
        });
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            signuptext2.setText("注册");
            txtphoneno2.setText("电话号码");
            btnBack.setText("上一步");
            btnNext.setText("下一步");
        }
    }
}
