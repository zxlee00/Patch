package sg.zhixuan.patch2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoreOptionsActivity extends AppCompatActivity {

    Button btnBack;
    LinearLayout btnSignOut, btnFriends, btnHelp, btnLanguage;
    TextView txtSignOut, txtFriends, txtHelp, txtLanguage, txtMore;
    ImageView imgSignOut, imgFriends, imgHelp, imgLanguage;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_options);

        btnBack = (Button)findViewById(R.id.btnBack);
        btnSignOut = (LinearLayout) findViewById(R.id.btnSignOut);
        btnFriends = (LinearLayout)findViewById(R.id.btnFriends);
        btnHelp = (LinearLayout) findViewById(R.id.btnHelp);
        btnLanguage = (LinearLayout)findViewById(R.id.btnLanguage);
        txtFriends = (TextView)findViewById(R.id.txtFriends);
        txtHelp = (TextView)findViewById(R.id.txtHelp);
        txtLanguage = (TextView)findViewById(R.id.txtLanguage);
        txtSignOut = (TextView)findViewById(R.id.txtSignOut);
        imgFriends = (ImageView)findViewById(R.id.imgFriends);
        imgHelp = (ImageView)findViewById(R.id.imgHelp);
        imgLanguage = (ImageView)findViewById(R.id.imgLanguage);
        imgSignOut = (ImageView)findViewById(R.id.imgSignOut);
        txtMore = (TextView)findViewById(R.id.txtMore);

        if (MainActivity.language.equals("Chinese"))
            setChineseLanguage();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signout = "";
                String confirmationmsg = "";
                String cancel = "";
                String confirm = "";

                if (MainActivity.language.equals("Chinese")) {
                    signout = "退出账户";
                    confirmationmsg = "确定退出账户吗？";
                    cancel = "取消";
                    confirm = "确定";
                } else if (MainActivity.language.equals("English")) {
                    signout = "Sign Out";
                    confirmationmsg = "Are you sure you want to sign out?";
                    cancel = "Cancel";
                    confirm = "Confirm";
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MoreOptionsActivity.this);
                alertDialog.setTitle(signout)
                        .setMessage(confirmationmsg)
                        .setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.mAuth.signOut();
                                MainActivity.finishMainActivity();
                                finish();
                                Intent intent = new Intent(MoreOptionsActivity.this, MainAccountActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreOptionsActivity.this, FriendsActivity.class));
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loadingtext = "Loading, please wait...";
                if (MainActivity.language.equals("Chinese")) {
                    loadingtext = "加载中，请稍等。。。";
                }
                progressdialog = ProgressDialog.show(MoreOptionsActivity.this, "", loadingtext, true);
                startActivity(new Intent(MoreOptionsActivity.this, HelpActivity.class));
            }
        });

        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreOptionsActivity.this, LanguageActivity.class));
            }
        });

        btnFriends.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event, btnFriends, imgFriends, R.drawable.pressedfriends, R.drawable.friends, txtFriends);
            }
        });

        btnLanguage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event, btnLanguage, imgLanguage, R.drawable.pressedlanguage, R.drawable.language, txtLanguage);
            }
        });

        btnHelp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event, btnHelp, imgHelp, R.drawable.pressedhelp, R.drawable.help, txtHelp);
            }
        });

        btnSignOut.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event, btnSignOut, imgSignOut, R.drawable.pressedsignout, R.drawable.signout, txtSignOut);
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event, LinearLayout layout, ImageView imageView, int newImage, int oldImage, TextView text) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                imageView.setImageResource(newImage);
                text.setTextColor(Color.parseColor("#5e2a40"));
                if (layout == btnFriends || layout == btnHelp)
                    layout.setBackgroundResource(R.drawable.pressed_rightborder);
                else
                    layout.setBackgroundColor(Color.parseColor("#dfb992"));
                break;
            case MotionEvent.ACTION_UP:
                imageView.setImageResource(oldImage);
                text.setTextColor(Color.parseColor("#203f58"));
                if (layout == btnFriends || layout == btnHelp)
                    layout.setBackgroundResource(R.drawable.rightborder);
                else
                    layout.setBackgroundColor(Color.parseColor("#f0dfbb"));
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(progressdialog != null && progressdialog.isShowing())
        {
            progressdialog.dismiss();
        }
    }

    public void setChineseLanguage() {
        btnBack.setText("返回");
        txtMore.setText("更多");
        txtSignOut.setText("退出账户");
        txtLanguage.setText("语言");
        txtHelp.setText("帮助");
        txtFriends.setText("好友");
    }
}
