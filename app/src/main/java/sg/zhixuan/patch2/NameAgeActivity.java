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

import java.util.ArrayList;
import java.util.List;

public class NameAgeActivity extends AppCompatActivity {

    private static final String TAG = "NameAgeActivity";
    EditText etName;
    Spinner spnAge;
    Button btnBack, btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_age);

        etName = (EditText) findViewById(R.id.etName);
        spnAge = (Spinner) findViewById(R.id.spnAge);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);

        List age = new ArrayList<Integer>();
        for (int i = 55; i <= 100; i++) {
            age.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spnArrayAdapter = new ArrayAdapter<Integer>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, age);
        spnAge.setAdapter(spnArrayAdapter);

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

                if (!name.isEmpty() &&
                        name.matches("^[a-zA-Z\\s]*$")) {
                    MainAccountActivity.user.setName(name);
                } else {
                    etName.setError("Enter a valid name.");
                    etName.requestFocus();
                    return;
                }

                MainAccountActivity.user.setAge(age);
                Intent intent = new Intent(NameAgeActivity.this, PhoneNumberActivity.class);
                startActivity(intent);
            }
        });
    }
}
