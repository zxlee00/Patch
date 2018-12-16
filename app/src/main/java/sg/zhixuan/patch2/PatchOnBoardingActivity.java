package sg.zhixuan.patch2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.Toast;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class PatchOnBoardingActivity extends AhoyOnboarderActivity {

    static String onboardinglanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title1 = "Match-Up";
        String desc1 = "Find strangers who share similar hobbies with you, and make new friends!";
        String title2 = "Chats";
        String desc2 = "Chat with your new friends and bond further with one another. Never be lonely again!";
        String title3 = "Appointments";
        String desc3 = "Make appointments with your friends! No fear of forgetting your appointments because they will all be in Patch! You will even get push notifications within 2 hours of your appointment!";
        String title4 = "Help";
        String desc4 = "Worried that you will not know how to use Patch? Don't worry, we have provided an extensive user guide in case you do not know how to use Patch. It can be found at 'MORE' and then 'HELP'!";
        String title5 = "One-Time Password";
        String desc5 = "No more remembering of user names and passwords! All you need to remember is your phone number and we will do the rest for you!";
        String title6 = "Profile Management";
        String desc6 = "Edit your profile anytime, anywhere. Recently gained a new interest? Add it to your profile and we will find match-ups for you!";
        String title7 = "Colours & Font Size";
        String desc7 = "We are using colours that will suit your needs and extra large font size so that you can see everything clearly without squinting your eyes.";

        if (onboardinglanguage.equals("Chinese")) {
            title1 = "配对";
            desc1 = "与跟您有相同兴趣/爱好的陌生人配对，认识新的朋友！";
            title2 = "好友对话";
            desc2 = "与 Patch 结识的好友们对话，增进彼此的感情。您再也不用担心会孤独了！";
            title3 = "预约";
            desc3 = "与 Patch 结识的好友们预约！不用再担心会忘了与好友们的约会因为 Patch 会帮您记下这些！更何况，在预约的两个钟头内，您将会收到通知哦！";
            title4 = "帮助";
            desc4 = "担心您不会使用 Patch 吗？不用怕，我们为您提供了一个详细的用户使用手册哦！只要去‘更多’，然后‘帮助’就能看到这个手册了！";
            title5 = "一次性密码";
            desc5 = "再也不用记任何用户名或密码了！您只需记住自己的电话号码，剩下的就交给我们吧！";
            title6 = "个人资料管理";
            desc6 = "随时，随地地更改您的个人资料！最近多了什么兴趣/爱好吗？赶快把它加进您的个人资料，这样我们才能为您寻找更多的配对！";
            title7 = "颜色 & 字体大小";
            desc7 = "我们使用的颜色都是根据您的需要哦！此外，为了您的舒适，我们的字体都是偏大的，这样您就可以清清楚楚的看到所有东西了！";
        }

        AhoyOnboarderCard onboarderCard1 = new AhoyOnboarderCard(title1, desc1, R.drawable.matchup);
        AhoyOnboarderCard onboarderCard2 = new AhoyOnboarderCard(title2, desc2, R.drawable.chat);
        AhoyOnboarderCard onboarderCard3 = new AhoyOnboarderCard(title3, desc3, R.drawable.appt);
        AhoyOnboarderCard onboarderCard4 = new AhoyOnboarderCard(title4, desc4, R.drawable.help);
        AhoyOnboarderCard onboarderCard5 = new AhoyOnboarderCard(title5, desc5, R.drawable.lock);
        AhoyOnboarderCard onboarderCard6 = new AhoyOnboarderCard(title6, desc6, R.drawable.profile);
        AhoyOnboarderCard onboarderCard7 = new AhoyOnboarderCard(title7, desc7, R.drawable.color);

        onboarderCard1.setBackgroundColor(R.color.white);
        onboarderCard2.setBackgroundColor(R.color.white);
        onboarderCard3.setBackgroundColor(R.color.white);
        onboarderCard4.setBackgroundColor(R.color.white);
        onboarderCard5.setBackgroundColor(R.color.white);
        onboarderCard6.setBackgroundColor(R.color.white);
        onboarderCard7.setBackgroundColor(R.color.white);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(onboarderCard1);
        pages.add(onboarderCard2);
        pages.add(onboarderCard3);
        pages.add(onboarderCard4);
        pages.add(onboarderCard5);
        pages.add(onboarderCard6);
        pages.add(onboarderCard7);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.black);
            page.setDescriptionColor(R.color.black);
        }

        String finishtext = "Finish";
        if (onboardinglanguage.equals("Chinese"))
            finishtext = "关闭";
        setFinishButtonTitle(finishtext);
        showNavigationControls(true);

        List<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.solid_one);
        colorList.add(R.color.solid_two);
        colorList.add(R.color.solid_three);
        colorList.add(R.color.solid_four);
        colorList.add(R.color.solid_five);
        colorList.add(R.color.solid_six);
        colorList.add(R.color.solid_seven);

        setColorBackground(colorList);

        Typeface face = ResourcesCompat.getFont(this, R.font.arimo);
        setFont(face);

        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {
        finish();
    }
}
