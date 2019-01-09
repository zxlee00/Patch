package sg.zhixuan.patch2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainAccountActivity extends AppCompatActivity {

    private static final String TAG = "MainAccountActivity";
    static FirebaseUser mUser;
    static FirebaseAuth mAuth;
    static User user = new User();
    String mUserKey;
    DatabaseReference mUserReference;
    AlertDialog.Builder alertDialog;
    boolean isFirstTimeUser;
    Button btnSignUp, btnLogin;
    Button btnSetLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account);

        checkLastSetLanguage();
        checkFirstTimeUse();
        if (isFirstTimeUser == true) {
            Intent intent = new Intent(this, OnBoardingLanguageActivity.class);
            startActivity(intent);

            setNotFirstTimeUser();
        }

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSetLanguage = (Button)findViewById(R.id.btnSetLanguage);

        setChineseLanguage();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    alertDialog = new AlertDialog.Builder(MainAccountActivity.this);
                    String text1 = "Sign Out";
                    String text2 = "You are currently logged in to another account. If you want to proceed on with sign up, you will be logged out of your current account. Are you sure?";
                    String text3 = "Cancel";
                    String text4 = "Confirm";

                    if (MainActivity.language.equals("Chinese")) {
                        text1 = "退出账户";
                        text2 = "您目前已经在 Patch 登录了。如果您想注册新的账户，您将会被退出目前登录的账户。确定要注册新的账户吗？";
                        text3 = "取消";
                        text4 = "确定";
                    }

                    alertDialog.setTitle(text1)
                            .setMessage(text2)
                            .setNegativeButton(text3, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setPositiveButton(text4, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth.getInstance().signOut();

                                    Intent intent = new Intent(MainAccountActivity.this, NameAgeActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();

                } else {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainAccountActivity.this, NameAgeActivity.class));
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                mUser = mAuth.getCurrentUser();

                if (mUser != null) {
                    mUserKey = mUser.getUid();
                    mUserReference = FirebaseDatabase.getInstance().getReference().child("users").child(mUserKey);

                    mUserReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            user = dataSnapshot.getValue(User.class);
                            finish();
                            Log.d(TAG, "onCreate: " + user.getName() + " " +  mUser.getUid());
                            Intent intent = new Intent(MainAccountActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: ", databaseError.toException());
                        }
                    });
                } else {
                    startActivity(new Intent(MainAccountActivity.this, LoginActivity.class));
                }
            }
        });

        btnSetLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainAccountActivity.this, LanguageActivity.class));
            }
        });
    }

    //check if user is a first time user
    public void checkFirstTimeUse() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        isFirstTimeUser = sharedPreferences.getBoolean("IsFirstTimeUser", true);
    }

    //call this method after the first time the user opens the app
    public void setNotFirstTimeUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IsFirstTimeUser", false);
        editor.commit();
    }

    private void checkLastSetLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        MainActivity.language = sharedPreferences.getString("language", "English");
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            btnSignUp.setText("注册");
            btnLogin.setText("登录");
        }
    }
}