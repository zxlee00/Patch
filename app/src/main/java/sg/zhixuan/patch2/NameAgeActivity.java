package sg.zhixuan.patch2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NameAgeActivity extends AppCompatActivity {

    private static final String TAG = "NameAgeActivity";
    EditText etName;
    Spinner spnAge, spnGender;
    Button btnBack, btnNext;
    TextView nametext, signuptext1, agetext, gendertext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_age);

        etName = (EditText) findViewById(R.id.etName);
        spnAge = (Spinner) findViewById(R.id.spnAge);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        spnGender = (Spinner)findViewById(R.id.spnGender);
        nametext = (TextView)findViewById(R.id.nametext);
        agetext = (TextView)findViewById(R.id.agetext);
        gendertext = (TextView)findViewById(R.id.gendertext);
        signuptext1 = (TextView)findViewById(R.id.signuptext1);

        setChineseLanguage();

        List age = new ArrayList<Integer>();
        for (int i = 55; i <= 100; i++) {
            age.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spnArrayAdapter = new ArrayAdapter<Integer>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, age);
        spnAge.setAdapter(spnArrayAdapter);

        List gender = new ArrayList<String>();

        if (MainActivity.language.equals("Chinese")) {
            gender.add("女");
            gender.add("男");
        } else if (MainActivity.language.equals("English")) {
            gender.add("Female");
            gender.add("Male");
        }

        ArrayAdapter<String> spnGenderAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, gender);
        spnGender.setAdapter(spnGenderAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                Integer age = Integer.parseInt(spnAge.getSelectedItem().toString());
                String gender = spnGender.getSelectedItem().toString();

                if (gender == "女")
                    gender = "Female";
                else if (gender == "男")
                    gender = "Male";

                if (!name.isEmpty() &&
                        name.matches("^[a-zA-Z\\s]*$")) {
                    MainAccountActivity.user.setName(name);
                } else {
                    String error1 = "";

                    if (MainActivity.language.equals("Chinese")) {
                        error1 = "请您输入一个有效的姓名 （只接受英文字母）。";
                    } else if (MainActivity.language.equals("English")) {
                        error1 = "Enter a valid name.";
                    }

                    etName.setError(error1);
                    etName.requestFocus();
                    return;
                }

                MainAccountActivity.user.setAge(age);
                MainAccountActivity.user.setGender(gender);
                Intent intent = new Intent(NameAgeActivity.this, PhoneNumberActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            gendertext.setText("性别");
            agetext.setText("年龄");
            signuptext1.setText("注册");
            nametext.setText("姓名 （英文字母）");
            btnBack.setText("回去");
            btnNext.setText("下一步");
        }
    }
}
