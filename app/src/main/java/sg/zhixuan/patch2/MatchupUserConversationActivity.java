package sg.zhixuan.patch2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatchupUserConversationActivity extends AppCompatActivity {

    static String MatchUpUserName, MatchUpUserHobbies, MatchUpUserUID;
    TextView txtMatchupUser;
    Button btnSendRequest;
    DatabaseReference requestRef;

    //Todo: When one side presses a button to add other party
    //Todo: Other side will receive a message prompting them to accept or reject the request
    //Todo: The prompt should be some sort of key word

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchup_user_conversation);

        requestRef = FirebaseDatabase.getInstance().getReference().child("requests").child(MatchUpUserUID);
        btnSendRequest = (Button)findViewById(R.id.btnSendRequest);
        txtMatchupUser = (TextView)findViewById(R.id.txtMatchupUser);
        txtMatchupUser.setText(MatchUpUserName);

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MatchupUserConversationActivity.this);
                builder.setTitle("Send Request")
                        .setMessage("Confirm to send request? This action cannot be undone.")
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestRef.child(MainActivity.uid).setValue(MainAccountActivity.user.name);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
    }
}
