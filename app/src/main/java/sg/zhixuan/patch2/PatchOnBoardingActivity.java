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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard onboarderCard1 = new AhoyOnboarderCard("Match-Up", "Find strangers who share similar hobbies with you, and make new friends!", R.drawable.matchup);
        AhoyOnboarderCard onboarderCard2 = new AhoyOnboarderCard("Chats", "Chat with your new friends and bond further with one another. Never be lonely again!", R.drawable.chat);
        AhoyOnboarderCard onboarderCard3 = new AhoyOnboarderCard("Appointments", "Make appointments with your friends! No fear of forgetting your appointments because they will all be in Patch! You will even get push notifications within 2 hours of your appointment!", R.drawable.appt);
        AhoyOnboarderCard onboarderCard4 = new AhoyOnboarderCard("Help", "Worried that you will not know how to use Patch? Don't worry, we have provided an extensive user guide in case you do not know how to use Patch. It can be found at 'MORE' and then 'HELP'!", R.drawable.help);
        AhoyOnboarderCard onboarderCard5 = new AhoyOnboarderCard("One-Time Password", "No more remembering of user names and passwords! All you need to remember is your phone number and we will do the rest for you!", R.drawable.lock);
        AhoyOnboarderCard onboarderCard6 = new AhoyOnboarderCard("Profile Management", "Edit your profile anytime, anywhere. Recently gained a new interest? Add it to your profile and we will find match-ups for you!", R.drawable.profile);
        AhoyOnboarderCard onboarderCard7 = new AhoyOnboarderCard("Colours & Font Size", "We are using colours that will suit your needs and extra large font size so that you can see everything clearly without squinting your eyes.", R.drawable.color);

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

        setFinishButtonTitle("Finish");
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
