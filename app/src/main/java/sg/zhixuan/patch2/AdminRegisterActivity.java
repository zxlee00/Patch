package sg.zhixuan.patch2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminRegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPw, etCfmPw;
    Spinner spnGender;
    Button btnRegister;
    FirebaseAuth mAuth;
    String name, email, gender, password, cfmPassword;
    private static final String TAG = "AdminRegisterActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPw = findViewById(R.id.etPw);
        etCfmPw = findViewById(R.id.etCfmPw);
        spnGender = findViewById(R.id.spnGender);
        btnRegister = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();

        List genderList = new ArrayList<String>();
        genderList.add("Female");
        genderList.add("Male");

        ArrayAdapter<String> spnGenderAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, genderList);
        spnGender.setAdapter(spnGenderAdapter);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAdmin();
            }
        });
    }

    public void registerAdmin() {
        if (checkFields()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete:task " + task.getResult().getUser().getUid());
                        Log.d(TAG, "onComplete:mAuth " + mAuth.getCurrentUser().getUid());

                        AdminLoginActivity.adminUser = mAuth.getCurrentUser();
                        String uid = AdminLoginActivity.adminUser.getUid();
                        AdminLoginActivity.admin = new Admin(uid, name, email, gender);

                        Log.d(TAG, "onComplete:admin " + AdminLoginActivity.admin);
                        FirebaseDatabase.getInstance().getReference()
                                .child("admins")
                                .child(AdminLoginActivity.adminUser.getUid())
                                .setValue(AdminLoginActivity.admin);

                        mAuth.signOut();

                        Intent intent = new Intent(AdminRegisterActivity.this, AdminConfirmationActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(AdminRegisterActivity.this, "Invalid email or password.", Toast.LENGTH_LONG).show();
                    } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        etEmail.setError("Email already in use.");
                        etEmail.requestFocus();
                    } else {
                        Log.d(TAG, task.getException().toString());
                        Toast.makeText(AdminRegisterActivity.this, "Registration failed.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public boolean checkFields() {
        name = etName.getText().toString().trim();
        gender = spnGender.getSelectedItem().toString();
        email = etEmail.getText().toString().trim();
        password = etPw.getText().toString().trim();
        cfmPassword = etCfmPw.getText().toString();

        if (name.isEmpty()) {
            etName.setError("Please enter your name.");
            etName.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            etEmail.setError("Please enter your email.");
            etEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            etPw.setError("Please enter your password.");
            etPw.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            etPw.setError("Password should be at least 6 characters.");
            etPw.requestFocus();
            return false;
        }

        if (cfmPassword.isEmpty()) {
            etCfmPw.setError("Please enter your password again.");
            etCfmPw.requestFocus();
            return false;
        }

        if (!cfmPassword.equals(password)) {
            etCfmPw.setError("Passwords do not match.");
            etCfmPw.requestFocus();
            return false;
        }

        return true;
    }
}
