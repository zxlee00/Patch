package sg.zhixuan.patch2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HobbyActivity extends AppCompatActivity {

    private static final String TAG = "HobbyActivity";
    Button btnBack, btnComplete;
    GridView gvHobby;
    String hobby;
    List<String> hobbies;
    TextView signup, signuphobby;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby);

        //TODO: List of hobbies into grid view(?) into firebase "hobby" node as a string

        btnBack = (Button) findViewById(R.id.btnBack);
        btnComplete = (Button) findViewById(R.id.btnComplete);
        gvHobby = (GridView) findViewById(R.id.gvHobby);
        signup = (TextView)findViewById(R.id.signup);
        signuphobby = (TextView)findViewById(R.id.signuphobby);

        setChineseLanguage();

        hobbies = new ArrayList<>();
        hobby = "";

        String[] hobbyList = new String[] {
                "Exercise",
                "Watch Videos / Shows",
                "Reading",
                "Cooking",
                "Talking / Chatting"
        };

        final List<String> hobbiesList = new ArrayList<String>(Arrays.asList(hobbyList));

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(HobbyActivity.this, android.R.layout.simple_list_item_1, hobbiesList);
        gvHobby.setAdapter(adapter);

        gvHobby.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (hobbies.isEmpty()) {
                    hobbies.add(selectedItem);
                    view.setBackgroundColor(Color.parseColor("#f2ddc9"));
                } else {
                    if (hobbies.contains(selectedItem)) {
                        hobbies.remove(selectedItem);
                        view.setBackgroundColor(Color.parseColor("#dfb992"));
                    } else {
                        hobbies.add(selectedItem);
                        view.setBackgroundColor(Color.parseColor("#f2ddc9"));
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < hobbies.size(); i++) {
                    if (hobby.isEmpty()) {
                        hobby += hobbies.get(i);
                    } else {
                        hobby += "," + hobbies.get(i);
                    }
                }
                MainAccountActivity.user.setHobby(hobby);
                VerificationActivity.mDatabase.child("users").child(MainAccountActivity.user.getUid()).setValue(MainAccountActivity.user);
                FirebaseDatabase.getInstance().getReference().child("ratingfeedback").child(MainAccountActivity.user.getUid()).child("rating").setValue("5.00");
                Log.d(TAG, "onClick: " + hobby);
                Intent intent = new Intent(HobbyActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            signup.setText("注册");
            signuphobby.setText("请选择你的兴趣/爱好（可以选择多过一个）：");
            btnBack.setText("上一步");
            btnComplete.setText("完成");
        }
    }
}