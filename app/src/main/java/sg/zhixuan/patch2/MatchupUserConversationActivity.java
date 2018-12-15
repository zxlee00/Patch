package sg.zhixuan.patch2;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MatchupUserConversationActivity extends AppCompatActivity {

    static String MatchUpUserName, MatchUpUserHobbies, MatchUpUserUID;
    LinearLayout matchuplayout1;
    RelativeLayout matchuplayout2;
    TextView txtMatchupUser, prompt;
    Button btnSendRequest;
    DatabaseReference requestRef;
    SimpleDateFormat sdf;
    ScrollView matchupScrollView;
    ImageView sendButton;
    Firebase reference1, reference2;
    EditText messageArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchup_user_conversation);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        sdf = new SimpleDateFormat("EEE, MMM d 'AT' HH:mm a");

        requestRef = FirebaseDatabase.getInstance().getReference().child("requests").child(MatchUpUserUID);
        btnSendRequest = (Button)findViewById(R.id.btnSendRequest);
        prompt = (TextView)findViewById(R.id.prompt);
        txtMatchupUser = (TextView)findViewById(R.id.txtMatchupUser);
        txtMatchupUser.setText(MatchUpUserName);
        matchuplayout1 = (LinearLayout)findViewById(R.id.matchuplayout1);
        matchuplayout2 = (RelativeLayout)findViewById(R.id.matchuplayout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        matchupScrollView = (ScrollView)findViewById(R.id.matchupscrollView);

        setChineseLanguage();

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://patchtesting2-3eb1a.firebaseio.com/matchup/messages/" + MainActivity.uid + "_" + MatchUpUserUID);
        reference2 = new Firebase("https://patchtesting2-3eb1a.firebaseio.com/matchup/messages/" + MatchUpUserUID + "_" + MainActivity.uid);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    String currentDateandTime = sdf.format(new Date());
                    map.put("message", messageText);
                    map.put("user", MainActivity.uid);
                    map.put("time", currentDateandTime);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                String time = map.get("time").toString();

                if(userName.equals(MainActivity.uid)){
                    addMessageBox(message,time, 1);
                }
                else{
                    addMessageBox(message,time, 2);
                }

                matchupScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        matchupScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MatchupUserConversationActivity.this);
                String title = "Send Request";
                String message = "Confirm to send request? This action cannot be undone.";
                String posbtn = "CONFIRM";
                String negbtn = "CANCEL";
                if (MainActivity.language.equals("Chinese")) {
                    title = "发送好友请求";
                    message = "确定发送好友请求吗？请注意，一旦发送了，就无法撤销此操作了。";
                    posbtn = "确定";
                    negbtn = "取消";
                }
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(posbtn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestRef.child(MainActivity.uid).setValue(MainAccountActivity.user.name);
                            }
                        })
                        .setNegativeButton(negbtn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        messageArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchupScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        matchupScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                },100);
            }
        });
    }

    public void addMessageBox(String message,String time, int type){

        TextView textmsg = new TextView(MatchupUserConversationActivity.this);
        TextView texttime = new TextView(MatchupUserConversationActivity.this);

        textmsg.setText(message);
        textmsg.setTextSize(20);
        textmsg.setTextColor(Color.parseColor("#000000"));
        textmsg.setTypeface(ResourcesCompat.getFont(MatchupUserConversationActivity.this, R.font.titillium));
        textmsg.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        texttime.setText(time);
        texttime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.RIGHT;
            lp3.gravity = Gravity.RIGHT;
            lp3.bottomMargin = 10;
            lp2.leftMargin = 120;
            textmsg.setBackgroundResource(R.drawable.matchup_text_in);
        }
        else{
            lp2.gravity = Gravity.LEFT;
            lp3.gravity = Gravity.LEFT;
            lp2.rightMargin = 120;
            lp3.bottomMargin = 10;
            textmsg.setBackgroundResource(R.drawable.matchup_text_out);
        }

        textmsg.setLayoutParams(lp2);
        texttime.setLayoutParams(lp3);

        matchuplayout1.addView(textmsg);
        matchuplayout1.addView(texttime);
        matchupScrollView.fullScroll(View.FOCUS_DOWN);
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            prompt.setText("想成为好友吗？");
            btnSendRequest.setText("发送好友请求");
            messageArea.setHint("输入信息。。。");
        }
    }
}
