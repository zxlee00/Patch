package sg.zhixuan.patch2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                startActivity(new Intent(PhoneNumberActivity.this, NameAgeActivity.class));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = etPhoneNumber.getText().toString().trim();

                if (!phoneNumber.isEmpty() &&
                        phoneNumber.length() == 8 &&
                        TextUtils.isDigitsOnly(phoneNumber)
                        ) {
                    MainAccountActivity.user.setPhoneNumber("+65" + phoneNumber);
                } else {
                    etPhoneNumber.setError("Enter a valid phone number.");
                    etPhoneNumber.requestFocus();
                    return;
                }

                Intent intent = new Intent(PhoneNumberActivity.this, VerificationActivity.class);
                startActivity(intent);
            }
        });
    }
}
