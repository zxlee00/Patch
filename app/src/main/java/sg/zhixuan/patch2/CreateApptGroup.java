package sg.zhixuan.patch2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class CreateApptGroup extends AppCompatActivity {

    RecyclerView rvApptPartyList;
    TextView createappttext, txtInstructions;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    String uid;
    UserContactAdapter userContactAdapter;
    List<String> contactList;
    List<String> contactIDList;
    Button btnApptGroupToApptActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appt_group);

        createappttext = (TextView)findViewById(R.id.createappt1);
        txtInstructions = (TextView)findViewById(R.id.txtInstructions);
        setChineseLanguage();

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        uid = firebaseUser.getUid();

        rvApptPartyList = (RecyclerView)findViewById(R.id.rvApptPartyList);
        contactList = AppointmentActivity.contactList;
        contactIDList = AppointmentActivity.contactIDList;
        btnApptGroupToApptActivity = (Button)findViewById(R.id.btnApptGroupToApptActivity);

        final List<User> contactNameList = new ArrayList<User>();

        int i = 0;
        int x = 0;
        while (i < contactList.size()) {
            Log.d("ZZZ", contactList.get(i));
            Log.d("ZZZ", contactList.get(i + 1));
            Log.d("ZZZ", Integer.toString(contactList.size()));
            Log.d("ZZZ", contactIDList.get(x));
            User user = new User(contactList.get(i), contactList.get(i + 1), contactIDList.get(x));
            contactNameList.add(user);
            i = i + 2;
            x = x + 1;
        }

        userContactAdapter = new UserContactAdapter(this, this, contactNameList);
        rvApptPartyList.setLayoutManager(new LinearLayoutManager(this));
        rvApptPartyList.setItemAnimator(new DefaultItemAnimator());
        rvApptPartyList.setAdapter(userContactAdapter);

        btnApptGroupToApptActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setChineseLanguage() {
        if(MainActivity.language.equals("Chinese")) {
            txtInstructions.setText("请选择与哪位朋友创造预约：");
            createappttext.setText("创造预约");
        }
    }
}
