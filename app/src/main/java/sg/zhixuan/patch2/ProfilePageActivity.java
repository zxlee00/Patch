package sg.zhixuan.patch2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfilePageActivity extends AppCompatActivity {
    TextView tvName, tvAge, tvHobbies, tvGender, profiletext, scrolldownprompt, hometext;
    ImageView ivProfile;
    Button btnEdit;
    LinearLayout btnHome;
    DatabaseReference imageRef;
    String imageURL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        tvName = (TextView) findViewById(R.id.tvName);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvHobbies = (TextView) findViewById(R.id.tvHobbies);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnHome = (LinearLayout) findViewById(R.id.btnHome);
        tvGender = (TextView)findViewById(R.id.tvGender);
        profiletext = (TextView)findViewById(R.id.profiletext);
        scrolldownprompt = (TextView)findViewById(R.id.scrolldownprompt);
        hometext = (TextView)findViewById(R.id.hometext1);

        setChineseLanguage();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imgRef = storage.getReferenceFromUrl(MainAccountActivity.user.getProfilePic());

        imageRef = FirebaseDatabase.getInstance().getReference();

        imageRef.child("users").child(MainActivity.uid).child("profilePic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageURL = dataSnapshot.getValue(String.class);

                Glide.with(ProfilePageActivity.this /* context */)
                        .load(imageURL)
                        .apply(new RequestOptions()
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE))
                        .into(ivProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tvName.setText(MainAccountActivity.user.getName());
        tvAge.append(Integer.toString(MainAccountActivity.user.getAge()));
        tvGender.append(MainAccountActivity.user.getGender());

        String hobbyList[] = MainAccountActivity.user.getHobby().split(",");
        for (int i = 0; i < hobbyList.length; i ++) {
            tvHobbies.append("\n" +  hobbyList[i]);
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ProfilePageActivity.this, MainActivity.class));
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePageActivity.this, ProfileEditActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Glide.with(ProfilePageActivity.this)
                .load(MainAccountActivity.user.profilePic)
                .apply(new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(ivProfile);
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            tvAge.setText("年龄：");
            tvGender.setText("性别：");
            tvHobbies.setText("兴趣/爱好：");
            btnEdit.setText("编辑");
            profiletext.setText("用户资料");
            scrolldownprompt.setText("请下拉查看更多");
            hometext.setText("主页");
        }
    }
}
