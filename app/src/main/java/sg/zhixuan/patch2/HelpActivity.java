package sg.zhixuan.patch2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HelpActivity extends AppCompatActivity {

    ImageView btnOpenOnboarding;
    TextView helpguide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.help_tab_color));
        }

        helpguide = (TextView)findViewById(R.id.helpguide);
        btnOpenOnboarding = (ImageView)findViewById(R.id.btnOpenOnboarding);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String toolbartext = "";
        if (MainActivity.language.equals("Chinese")) {
            toolbartext = "用户使用手册";
        } else {
            toolbartext = "Help Guide";
        }
        toolbar.setTitle(toolbartext);
        helpguide.setText(toolbartext);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOpenOnboarding.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnOpenOnboarding.setBackgroundColor(getResources().getColor(R.color.tabpressed));
                        break;
                    case MotionEvent.ACTION_UP:
                        btnOpenOnboarding.setBackgroundColor(getResources().getColor(R.color.tab));
                        break;
                }
                return false;
            }
        });

        btnOpenOnboarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatchOnBoardingActivity.onboardinglanguage = MainActivity.language;
                startActivity(new Intent(HelpActivity.this, PatchOnBoardingActivity.class));
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        String tabmatchup = "Match-Up";
        String tabchats = "Chats";
        String tabappts = "Appointments";
        String tabprofile = "Profile";
        String tablanguage = "Language";
        String tabfriends = "Friends";
        String tabrequests = "Requests";

        if (MainActivity.language.equals("Chinese")) {
            tabmatchup = "配对";
            tabchats = "好友对话";
            tabappts = "预约";
            tabprofile = "个人资料";
            tablanguage = "语言";
            tabfriends = "好友";
            tabrequests = "好友请求";
        }

        tabLayout.addTab(tabLayout.newTab().setText(tabmatchup));
        tabLayout.addTab(tabLayout.newTab().setText(tabchats));
        tabLayout.addTab(tabLayout.newTab().setText(tabappts));
        tabLayout.addTab(tabLayout.newTab().setText(tabprofile));
        tabLayout.addTab(tabLayout.newTab().setText(tablanguage));
        tabLayout.addTab(tabLayout.newTab().setText(tabfriends));
        tabLayout.addTab(tabLayout.newTab().setText(tabrequests));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#bddce4"));
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#bddce4"));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final HelpPagerAdapter adapter = new HelpPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // This method ensures that tab selection events update the ViewPager and page changes update the selected tab.
        // tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}