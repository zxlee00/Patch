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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    EditText etPhoneNumber;
    Button btnBack, btnNext;
    String phoneNumber;
    Boolean found = false;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    TextView logintext1, txtPhoneNo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        logintext1 = (TextView)findViewById(R.id.logintext1);
        txtPhoneNo1 = (TextView)findViewById(R.id.txtphoneno1);
        mAuth = FirebaseAuth.getInstance();

        setChineseLanguage();

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
                if (phoneNumber.equals("901234567890123456789")) {
                    startActivity(new Intent(LoginActivity.this, AdminLoginActivity.class));
                    finish();
                }
                else if (!phoneNumber.isEmpty() && phoneNumber.length() == 8 && TextUtils.isDigitsOnly(phoneNumber)) {

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
                                String error1 = "";
                                if (MainActivity.language.equals("English")) {
                                    error1 = "Invalid phone number.";
                                } else if (MainActivity.language.equals("Chinese")) {
                                    error1 = "您输入的电话号码是无效的，请重新输入。";
                                }

                                //user does not exist
                                etPhoneNumber.setError(error1);
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
                    String error2 = "";
                    if (MainActivity.language.equals("English")) {
                        error2 = "Enter a valid phone number.";
                    } else if (MainActivity.language.equals("Chinese")) {
                        error2 = "请您输入一个有效的电话号码。";
                    }
                    etPhoneNumber.setError(error2);
                    etPhoneNumber.requestFocus();
                }
            }
        });
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            logintext1.setText("登录");
            txtPhoneNo1.setText("电话号码");
            btnBack.setText("回去");
            btnNext.setText("下一步");
        }
    }
}
