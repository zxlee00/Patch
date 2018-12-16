package sg.zhixuan.patch2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnBoardingLanguageActivity extends AppCompatActivity {

    Button btnOnBoardingEnglish, btnOnBoardingChinese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_language);

        btnOnBoardingChinese = (Button)findViewById(R.id.btnOnBoardingChinese);
        btnOnBoardingEnglish = (Button)findViewById(R.id.btnOnBoardingEnglish);

        btnOnBoardingEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatchOnBoardingActivity.onboardinglanguage = "English";
                finish();
                startActivity(new Intent(OnBoardingLanguageActivity.this, PatchOnBoardingActivity.class));
            }
        });

        btnOnBoardingChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatchOnBoardingActivity.onboardinglanguage = "Chinese";
                finish();
                startActivity(new Intent(OnBoardingLanguageActivity.this, PatchOnBoardingActivity.class));
            }
        });
    }
}
