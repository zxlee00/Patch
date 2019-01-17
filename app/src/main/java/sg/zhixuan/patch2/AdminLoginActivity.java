package sg.zhixuan.patch2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class AdminLoginActivity extends AppCompatActivity {

    EditText etEmail, etPw;
    Button btnLogin;
    TextView tvRegister;
    String loginID, password;
    FirebaseAuth mAuth;
    static Admin admin;
    static FirebaseUser adminUser;
    private static final String TAG = "AdminLoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etEmail = findViewById(R.id.etEmail);
        etPw = findViewById(R.id.etPw);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        mAuth = FirebaseAuth.getInstance();


        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()) { adminLogin(); }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLoginActivity.this, AdminRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean checkFields() {
        loginID = etEmail.getText().toString().trim();
        password = etPw.getText().toString().trim();

        if (loginID.isEmpty()) {
            etEmail.setError("Please enter your email.");
            etEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            etPw.setError("Please enter your password.");
            etPw.requestFocus();
            return false;
        }

        return true;
    }

    public void adminLogin() {

        mAuth.signInWithEmailAndPassword(loginID, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();

                    Intent intent = new Intent(AdminLoginActivity.this, AdminMenuActivity.class);
                    startActivity(intent);
                    finish();
                } else if (task.getException() instanceof FirebaseAuthInvalidUserException |
                        task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(AdminLoginActivity.this, "Invalid email or password.", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, task.getException().toString());
                    Toast.makeText(AdminLoginActivity.this, "Login failed.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
