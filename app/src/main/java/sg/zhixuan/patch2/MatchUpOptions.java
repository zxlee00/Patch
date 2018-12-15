package sg.zhixuan.patch2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MatchUpOptions extends AppCompatActivity {

    LinearLayout btnNewMatchUp, btnMatchUpConversations;
    ImageView matchupimg, conversationimg;
    TextView matchuptext, conversationtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_up_options);

        btnNewMatchUp = (LinearLayout)findViewById(R.id.btnNewMatchUp);
        btnMatchUpConversations = (LinearLayout)findViewById(R.id.btnMatchUpConversations);
        matchupimg = (ImageView)findViewById(R.id.matchupimg);
        conversationimg = (ImageView)findViewById(R.id.conversationimg);
        matchuptext = (TextView)findViewById(R.id.matchuptext);
        conversationtext = (TextView)findViewById(R.id.conversationtext);

        setChineseLanguage();

        btnNewMatchUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatchUpOptions.this, MatchUpActivity.class));
            }
        });

        btnNewMatchUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event, btnNewMatchUp, matchupimg, R.drawable.pressed_newmatchup, R.drawable.newmatchup, matchuptext);
            }
        });

        btnMatchUpConversations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatchUpOptions.this, MatchupConversationActivity.class));
            }
        });

        btnMatchUpConversations.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event, btnMatchUpConversations, conversationimg, R.drawable.pressedmatchupconversation, R.drawable.matchupconversation, conversationtext);
            }
        });

    }

    public boolean onTouchEvent(MotionEvent event, LinearLayout layout, ImageView imageView, int newImage, int oldImage, TextView text) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                imageView.setImageResource(newImage);
                text.setTextColor(Color.parseColor("#5e2a40"));
                if (layout == btnNewMatchUp)
                    layout.setBackgroundResource(R.drawable.pressed_bottomborder1);
                else
                    layout.setBackgroundColor(Color.parseColor("#dfb992"));
                break;
            case MotionEvent.ACTION_UP:
                imageView.setImageResource(oldImage);
                text.setTextColor(Color.parseColor("#203f58"));
                if (layout == btnNewMatchUp)
                    layout.setBackgroundResource(R.drawable.bottomborder);
                else
                    layout.setBackgroundColor(Color.parseColor("#f0dfbb"));
                break;
        }
        return false;
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            matchuptext.setText("新配对");
            conversationtext.setText("对话");
        }
    }
}
