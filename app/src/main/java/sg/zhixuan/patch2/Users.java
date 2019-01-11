package sg.zhixuan.patch2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class Users extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    ArrayList<String> contactNameList = new ArrayList<String>();
    ArrayList<String> contactUIDList = new ArrayList<String>();
    int totalUsers = 0;
    ProgressDialog pd;
    String user = UserDetails.uid;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    LinearLayout btnHome;
    TextView textView, hometext;
    static String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        hometext = (TextView)findViewById(R.id.hometext);
        btnHome = (LinearLayout)findViewById(R.id.btnHome);
        textView = (TextView)findViewById(R.id.textView);
        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        uid = mAuth.getUid();
        Log.d("ZZZ", "UID" + uid);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("contacts").child(uid);

        setChineseLanguage();

        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

        doOnSuccess();

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = contactUIDList.get(position);
                UserDetails.contactname = contactNameList.get(position);
                Chat.friendname = contactNameList.get(position);
                startActivity(new Intent(Users.this, Chat.class));
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void doOnSuccess(){
        Log.d("ZZZ", "PASSED..");
        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                contactNameList.clear();
                contactUIDList.clear();
                for(com.google.firebase.database.DataSnapshot chatUserSnapshot : dataSnapshot.getChildren()) {
                    totalUsers++;
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(chatUserSnapshot.getKey());
                    dbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                            contactNameList.add(snapshot.child("name").getValue(String.class));
                            contactUIDList.add(snapshot.child("uid").getValue(String.class));
                            noUsersText.setVisibility(View.GONE);
                            usersList.setVisibility(View.VISIBLE);
                            usersList.setAdapter(new ArrayAdapter<String>(Users.this, android.R.layout.simple_list_item_1, contactNameList));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pd.dismiss();
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            textView.setText("好友");
            hometext.setText("主页");
            noUsersText.setText("您此刻在 Patch 还没交到任何朋友，请使用配对功能，交到朋友之后再使用此功能。");
        }
    }
}
