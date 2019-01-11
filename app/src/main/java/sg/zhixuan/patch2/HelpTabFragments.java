package sg.zhixuan.patch2;

import android.app.ProgressDialog;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HelpTabFragments {

    static public class MatchUpHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_matchup_tab, container, false);

            ImageView imghelpmatchup1,imghelpmatchup2,imghelpmatchup3,imghelpmatchup4,imghelpmatchup5,imghelpmatchup6,
                    imghelpmatchup7,imghelpmatchup8,imghelpmatchup9,imghelpmatchup10,imghelpmatchup11,imghelpmatchup12,imghelpmatchup13,imghelpmatchup14;

            imghelpmatchup1 = (ImageView)view.findViewById(R.id.imghelpmatchup1);
            imghelpmatchup2 = (ImageView)view.findViewById(R.id.imghelpmatchup2);
            imghelpmatchup3 = (ImageView)view.findViewById(R.id.imghelpmatchup3);
            imghelpmatchup4 = (ImageView)view.findViewById(R.id.imghelpmatchup4);
            imghelpmatchup5 = (ImageView)view.findViewById(R.id.imghelpmatchup5);
            imghelpmatchup6 = (ImageView)view.findViewById(R.id.imghelpmatchup6);
            imghelpmatchup7 = (ImageView)view.findViewById(R.id.imghelpmatchup7);
            imghelpmatchup8 = (ImageView)view.findViewById(R.id.imghelpmatchup8);
            imghelpmatchup9 = (ImageView)view.findViewById(R.id.imghelpmatchup9);
            imghelpmatchup10 = (ImageView)view.findViewById(R.id.imghelpmatchup10);
            imghelpmatchup11 = (ImageView)view.findViewById(R.id.imghelpmatchup11);
            imghelpmatchup12 = (ImageView)view.findViewById(R.id.imghelpmatchup12);
            imghelpmatchup13 = (ImageView)view.findViewById(R.id.imghelpmatchup13);
            imghelpmatchup14 = (ImageView)view.findViewById(R.id.imghelpmatchup14);

            String text1 = "The Match-Up function allows you to interact with other users in Patch based on your hobbies.\nTo access the match-up function, go to 'MATCH-UP' in the Home page.\n\n" +
                    "Please note that the users you interact with in match-up are not automatically part of your friend list. Both parties have to approve before becoming friends (to be able to use the Appointments, Friends and Chats functions).\n\n" +
                    "On the other hand, match-up does not require approval from both sides. We will talk more about it in this guide so continue reading on!";
            String text2 = "Then, you will see two buttons. Click on the button 'NEW MATCH UP' to proceed on with the match-up.";
            String text3 = "As you can see here, we have found you another Patch user, Peter Lim.\nInformation such as the user's age, gender, rating will be displayed.\n" +
                    "To maintain the user's privacy, during the match-up, we will keep the user's picture private.\nThe max rating a user can get is 5.00. In this case, Peter has a rating of 4.90. This will be based on the feedbacks other users have given Peter.\n" +
                    "Other than these, Peter's hobbies will also be displayed.\n" +
                    "\nYou can then decide if you want to be matched up with Peter after looking through his hobbies and other details such as gender, age and rating by selecting the 'YES' or 'NO' buttons.";
            String text4 = "Wait... What is this 'VIEW FEEDBACKS' button you see here?";
            String text5 = "Well, this allows you to view the feedbacks other users have given Peter, be it good or bad. These are also the feedbacks that form Peter's rating.\n" +
                    "You might want to check it out before selecting the 'YES' or 'NO' buttons to accept or reject the match up.";
            String text6 = "Now, let's move on to checking out the match-ups you have accepted! Go ahead and select the 'CONVERSATIONS' button!";
            String text7 = "This is where you can see a list of the users you have accepted the match ups with. Those are the users whose names are in purple.\n\nWhat about those in red?\n" +
                    "Well... just as you can find other users, other users can find you too! Those users who found you will be denoted in red.";
            String text8 = "You have matched up with Peter just now. Now, talk to him! Just click on his name and you will be brought over to the chats screen where the two of you can send messages to one another!";
            String text9 = "Going back to the previous page, just now all we had done is click on the name. What if we long pressed on it? A menu will appear! You are given the options to view the user's details, or delete them from your list of matched up users!";
            String text10 = "First, let's take a look at user details. You will be brought to a page where you can see the user's name, age, gender, rating and hobbies. There are also two buttons, 'VIEW FEEDBACKS' and 'FEEDBACK'.";
            String text11 = "Clicking on 'VIEW FEEDBACKS' will bring you to a page which has a list of feedbacks the user has received from other users. Here, you can further evaluate the user!";
            String text12 = "Clicking on the 'FEEDBACK' button will bring you to a page where you can enter a feedback for the user.\n\n" +
                    "Here, you can rate the Peter on a scale of 1 to 5 by dragging the small pink circle on the pink line.\n" +
                    "Then, make sure to enter a feedback on Peter be it good or bad. Don't worry, we will keep your identity a secret!\n\n" +
                    "This feedback can also be seen by other users. Your feedback will be greatly appreciated and it will help other users to evaluate the kind of person Peter is!\n\n" +
                    "So, go ahead and enter your feedback on Peter!";
            String text13 = "Regret accepting a match-up? No worries, just long press at the name of the user you want to delete, then select 'Delete', and you will be prompted to confirm the deletion.\nClick on 'CONFIRM' if you want to proceed on with the deletion.\n" +
                    "Otherwise, click on 'CANCEL'.\n\nSince other users are also able to find you, you will also definitely be able to remove these match-ups! Just delete them if you don't want to be matched up with them.";
            String text14 = "Tada! Peter Lim has been removed from your list of matched up users. You will no longer be able to contact him and vice-versa.\n\nWant to add matched up users into your friend list in Patch (to be able to use Appointments, Chats and Friends functions)?\n" +
                    "Head to the 'REQUESTS' help manual then!";

            if (MainActivity.language.equals("Chinese")) {
                text1 = "配对功能可以让您跟其他有相似兴趣/爱好的 Patch 用户相识与沟通。\n想使用配对功能的话，请回到‘主页’，再点击‘配对’。\n\n" +
                        "请注意您配对的用户不会自动被加进您的好友列表里。如果您想成为彼此的好友，就必须得到两方的认可。只有在成为好友之后，才能使用其他的功能，例如预约，有关好友的功能还有跟好友对话。\n\n" +
                        "但是，配对不需要彼此的认可。我们一会儿会解释更多，所以请继续读下去！";
                text2 = "然后， 您会看到两个按钮。想进行配对的话，请点击‘新配对’。";
                text3 = "如您所看到的，我们为您找到了另一位 Patch 用户， Peter Lim。\nPeter Lim 的个人资料例如年龄，性别和评分将会被显示。\n" +
                        "为了保持其他用户的隐私，在配对的时候，我们会保密其他用户的头像。\n一位用户能得到的最高评分是5.00。 Peter 的评分是4.90。这是根据其他用户给 Peter 的反馈。\n" +
                        "除此之外，Peter 的爱好也会被显示。\n" +
                        "\n在观看了 Peter 的个人资料例如年龄，性别和评分之后，您可以决定是否要跟 Peter 配对。如果您想跟他配对，请点击‘接受’。否则，请点击‘拒绝’。";
                text4 = "等一等。。。这个‘查看反馈’的按钮到底是做什么用的呢？";
                text5 = "这个查看反馈的功能可以让您看到 Peter 的好友和配对友给他的反馈，不管是好是坏。这些反馈也会影响到 Peter 的评分。\n" +
                        "您可以先看配对用户所得到的反馈之后再选择‘接受’或‘拒绝’的选项。";
                text6 = "现在，让我们看看您所接受的配对吧！请点击‘对话’！";
                text7 = "在这里，您可以看到配对用户的列表。那些名字是紫色的都是您所找到与接受的配对用户哦！\n\n那。。那些名字是红色的呢？\n" +
                        "既然您能找到其他用户，那其他用户自然地也能找到您！这些用户的名字都是红色的哦！";
                text8 = "刚刚，您与 Peter 配对了。现在，您就可以跟他聊天了！只需点击他的名字，您就会被带到聊天的屏幕。在那里，您和 Peter 可以向对方发送信息哦！";
                text9 = "想想在前一页的时候，我们只点击了配对用户的名字。那如果。。我们长按配对用户的名字呢？那您将会看到一个菜单哦！菜单上有两个选项，一个是看看配对用户的个人资料，另一个是把他从您的配对用户列表中除去!";
                text10 = "首先，让我们看看配对用户的个人资料。您会看见配对用户的名字，年龄，性别，评分，还有兴趣/爱好。除此之外，您还会看到两个按钮，‘查看反馈’和‘反馈’。";
                text11 = "点击‘查看反馈’这个按钮会让您看到这个配对用户所得到的反馈。在这里，您可以进一步地了解与评估此用户。";
                text12 = "而点击‘反馈’这个按钮可以让您输入您对此用户的反馈。\n\n" +
                         "在这里，您可以为 Peter 评分（从1-5）。您只需滑动在粉红线上那个小小，粉红色的圆圈。\n" +
                         "然后，您将可以输入对 Peter 的反馈。不管是好是坏，您尽管写下来，不用担心，您的身份会被保密的！\n\n" +
                         "其他用户可以看到您所为 Peter 提供的反馈。您提供的每一个反馈我们都很欢迎哦！除此之外，您还会帮助其他用户更了解 Peter 的为人！\n\n所以，赶快提供您的反馈吧！";
                text13 = "后悔曾经接受的任何配对吗？不用担心，您只需长按想除去的配对用户的名字，再点击‘删除配对’这个选项，便会有一个屏幕弹出，让您确认此举。\n如果确认想删除配对的话，只需点击‘确认’这个按钮。\n" +
                         "不然，就点击‘取消’这个按钮。\n\n既然其他用户也能和您配对，当然您也能除去这些配对！如果您不想和他们配对的话，那就把他们删了吧。";
                text14 = "看！Peter Lim 已经从您的配对用户列表中除去了。您与他将无法再聊天了哦。\n\n" +
                        "想与配对用户成为好友吗（只有成为好友才能使用预约，好友对话，和好友功能）？\n" +
                        "那就前往‘好友请求’的使用手册吧！";

                imghelpmatchup1.setImageResource(R.drawable.help_matchup_1_ch);
                imghelpmatchup2.setImageResource(R.drawable.help_matchup_2_ch);
                imghelpmatchup3.setImageResource(R.drawable.help_matchup_3_ch);
                imghelpmatchup4.setImageResource(R.drawable.help_matchup_4_ch);
                imghelpmatchup5.setImageResource(R.drawable.help_matchup_5_ch);
                imghelpmatchup6.setImageResource(R.drawable.help_matchup_6_ch);
                imghelpmatchup7.setImageResource(R.drawable.help_matchup_7_ch);
                imghelpmatchup8.setImageResource(R.drawable.help_matchup_8_ch);
                imghelpmatchup9.setImageResource(R.drawable.help_matchup_9_ch);
                imghelpmatchup10.setImageResource(R.drawable.help_matchup_10_ch);
                imghelpmatchup11.setImageResource(R.drawable.help_matchup_11_ch);
                imghelpmatchup12.setImageResource(R.drawable.help_matchup_12_ch);
                imghelpmatchup13.setImageResource(R.drawable.help_matchup_13_ch);
                imghelpmatchup14.setImageResource(R.drawable.help_matchup_14_ch);
            }

            TextView txthelpmatchup1 = (TextView)view.findViewById(R.id.txthelpmatchup1);
            txthelpmatchup1.setText(text1);

            TextView txthelpmatchup2 = (TextView)view.findViewById(R.id.txthelpmatchup2);
            txthelpmatchup2.setText(text2);

            TextView txthelpmatchup3 = (TextView)view.findViewById(R.id.txthelpmatchup3);
            txthelpmatchup3.setText(text3);

            TextView txthelpmatchup4 = (TextView)view.findViewById(R.id.txthelpmatchup4);
            txthelpmatchup4.setText(text4);

            TextView txthelpmatchup5 = (TextView)view.findViewById(R.id.txthelpmatchup5);
            txthelpmatchup5.setText(text5);

            TextView txthelpmatchup6 = (TextView)view.findViewById(R.id.txthelpmatchup6);
            txthelpmatchup6.setText(text6);

            TextView txthelpmatchup7 = (TextView)view.findViewById(R.id.txthelpmatchup7);
            txthelpmatchup7.setText(text7);

            TextView txthelpmatchup8 = (TextView)view.findViewById(R.id.txthelpmatchup8);
            txthelpmatchup8.setText(text8);

            TextView txthelpmatchup9 = (TextView)view.findViewById(R.id.txthelpmatchup9);
            txthelpmatchup9.setText(text9);

            TextView txthelpmatchup10 = (TextView)view.findViewById(R.id.txthelpmatchup10);
            txthelpmatchup10.setText(text10);

            TextView txthelpmatchup11 = (TextView)view.findViewById(R.id.txthelpmatchup11);
            txthelpmatchup11.setText(text11);

            TextView txthelpmatchup12 = (TextView)view.findViewById(R.id.txthelpmatchup12);
            txthelpmatchup12.setText(text12);

            TextView txthelpmatchup13 = (TextView)view.findViewById(R.id.txthelpmatchup13);
            txthelpmatchup13.setText(text13);

            TextView txthelpmatchup14 = (TextView)view.findViewById(R.id.txthelpmatchup14);
            txthelpmatchup14.setText(text14);

            return view;
        }
    }

    static public class ChatsHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_chats_tab, container, false);

            ImageView imghelpchats1, imghelpchats2, imghelpchats3;

            imghelpchats1 = (ImageView)view.findViewById(R.id.imghelpchats1);
            imghelpchats2 = (ImageView)view.findViewById(R.id.imghelpchats2);
            imghelpchats3 = (ImageView)view.findViewById(R.id.imghelpchats3);

            String text1 = "The Chats function allows you to chat with friends that you have made in Patch through the match-up function.\nTo access the chats function, go to 'CHATS' in the Home Page.";
            String text2 = "Then, you will see a list of friends that you have made in Patch. First, let's click on John Lim to chat with him.\n\nSee none? Don't worry, refer to the 'MATCH-UP' manual to learn how to make friends in Patch!";
            String text3 = "Here, you and John Lim can send messages to one another as seen in the photo. Have fun chatting with the friends you have made in Patch!\n\nWith that, you have learnt all about the Chats function!";

            if (MainActivity.language.equals("Chinese")) {
                text1 = "好友对话功能可以让您和从配对功能结识的 Patch 好友聊天/对话。\n想使用这个功能的话，请点击主页的‘好友对话’。";
                text2 = "然后，您将会看到您的好友列表。首先，请点击 John Lim 方可与他对话。\n\n您的好友列表是空的吗？不用担心，请看‘配对’的使用手册来进一步地了解如何在 Patch 交到好友！";
                text3 = "在这里，就跟图中一样，您将可以和 John Lim 互相发送信息。快点在 Patch 认识一些好友，然后跟他们进行聊天吧！\n\n您已学会如何使用好友对话功能了耶！‘好友对话’使用手册就在此结束了！";

                imghelpchats1.setImageResource(R.drawable.help_chats_1_ch);
                imghelpchats2.setImageResource(R.drawable.help_chats_2_ch);
                imghelpchats3.setImageResource(R.drawable.help_chats_3_ch);
            }

            TextView txthelpchats1 = (TextView)view.findViewById(R.id.txthelpchats1);
            txthelpchats1.setText(text1);

            TextView txthelpchats2 = (TextView)view.findViewById(R.id.txthelpchats2);
            txthelpchats2.setText(text2);

            TextView txthelpchats3 = (TextView)view.findViewById(R.id.txthelpchats3);
            txthelpchats3.setText(text3);

            return view;
        }
    }

    static public class AppointmentsHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_appointments_tab, container, false);

            ImageView imghelpappt1,imghelpappt2,imghelpappt3,imghelpappt4,imghelpappt5,
                    imghelpappt6,imghelpappt7,imghelpappt8,imghelpappt9,imghelpappt10;

            imghelpappt1 = (ImageView)view.findViewById(R.id.imghelpappt1);
            imghelpappt2 = (ImageView)view.findViewById(R.id.imghelpappt2);
            imghelpappt3 = (ImageView)view.findViewById(R.id.imghelpappt3);
            imghelpappt4 = (ImageView)view.findViewById(R.id.imghelpappt4);
            imghelpappt5 = (ImageView)view.findViewById(R.id.imghelpappt5);
            imghelpappt6 = (ImageView)view.findViewById(R.id.imghelpappt6);
            imghelpappt7 = (ImageView)view.findViewById(R.id.imghelpappt7);
            imghelpappt8 = (ImageView)view.findViewById(R.id.imghelpappt8);
            imghelpappt9 = (ImageView)view.findViewById(R.id.imghelpappt9);
            imghelpappt10 = (ImageView)view.findViewById(R.id.imghelpappt10);

            String text1 = "The Appointments function allows you to keep track of the appointments you have with friends you made in Patch.\nTo access the appointments function, go to 'APPOINTMENTS' in the Home page.";
            String text2 = "Once you have entered the 'APPOINTMENTS', you will be able to see a list of appointments that you have set. Information like your friend's picture, his/her name, the appointment name, location, date and time will be displayed.";
            String text3 = "Within 2 hours of the time and date set for your appointment, you can get a notification to remind you that you have an appointment coming up.\nThis way, you will never forget about your appointment!";
            String text4 = "What if the appointment was cancelled and you want to delete it? Just long hold on the appointment you want to delete!";
            String text5 = "After the long press, you will receive a prompt to confirm the deletion of the appointment. Press 'CONFIRM' to proceed with the deletion and 'CANCEL' if you do not want to delete the appointment!";
            String text6 = "So far, we have only talked about viewing appointments. Let's talk about making these appointments now! In the bottom right corner, you will see a '+' button. Just click on it and it will bring you to the first step in creating an appointment!";
            String text7 = "In the next page, you will be able to see the list of friends you have made in Patch. Is yours empty? No worries, you just need to make some friends before you can use this function. Proceed on to the 'MATCH-UP' and 'REQUESTS' help manual to learn more about making friends!\n" +
                    "\nWhat? Your list is not empty? Then continue reading on! Simply select the friend you want to make an appointment with and you will be brought to the next page!";
            String text8 = "See this page? Let's continue! First, you have to enter the name of your appointment~";
            String text9 = "How do I set the date for this appointment? Easy! Just click on the 'DATE' button and you will see the following dialog. Simply scroll through to select your date.";
            String text10 = "What about the time? It's the same! Just click on 'TIME' to be able to choose a time!\n\nWith that, you have learnt all about the appointments function!";

            if (MainActivity.language.equals("Chinese")) {
                text1 = "预约功能可以让您记录与 Patch 好友们的预约。\n想使用这个功能的话，首先请按‘预约’（在主页）。";
                text2 = "接下来，您将会看到您所定下的所有预约。您的好友头像，姓名，预约 名称，预约地点，和预约时间也将会被显示。";
                text3 = "在预约的两个钟头内/之前，您将会收到通知，提醒您两个钟头内/之后会有和 Patch 好友的预约。\n这样，您就不会再忘记与 Patch 好友们的预约了！";
                text4 = "那如果您和 Patch 好友的预约被取消了，然后您想把它从列表中除去呢？很简单！您只需要长按想除去的预约哦！";
                text5 = "之后，您将会看到一个屏幕弹出来，让您确定是否删除此预约。点击‘确定’方可继续进行删除，而点击‘取消’可以让您取消此举。";
                text6 = "到目前为止，我们只说了关于查看预约的事情。那现在，让我们说说如何创造这些预约吧！在右下角，您会看到一个‘+’按钮。您只需点击这个按钮，就可以进入到创造预约的第一步哦！";
                text7 = "在接下来的一页，您将可以看到您在 Patch 认识的好友的列表。您的列表是空的吗？不用担心，您只需在 Patch 跟其他用户成为好友就可以使用这个功能了！请看‘配对’与‘好友请求’的使用手册来进一步地了解如何在 Patch 跟其他用户成为好友。\n" +
                        "什么？您的列表不是空的？那就继续读下去吧！您只需点击想预约的好友，之后就会被带到下一页！";
                text8 = "看到这一页吗？那让我们继续吧！首先，您需要输入预约的名称~";
                text9 = "那该如何定下预约的日期呢？非常简单！您只需点击‘日期’就会有屏幕弹出来。接下来，您只需滑动屏幕便可选择您的预约日期。";
                text10 = "那该怎么选择预约的时间呢？一样，您只需点击‘时间’便会有屏幕弹出来，让您选择预约的时间哦！\n\n简单吧！您已学会如何使用预约功能了耶！‘预约’使用手册就在此结束了！";

                imghelpappt1.setImageResource(R.drawable.help_appt_1_ch);
                imghelpappt2.setImageResource(R.drawable.help_appt_2_ch);
                imghelpappt3.setImageResource(R.drawable.help_appt_3_ch);
                imghelpappt4.setImageResource(R.drawable.help_appt_4_ch);
                imghelpappt5.setImageResource(R.drawable.help_appt_5_ch);
                imghelpappt6.setImageResource(R.drawable.help_appt_6_ch);
                imghelpappt7.setImageResource(R.drawable.help_appt_7_ch);
                imghelpappt8.setImageResource(R.drawable.help_appt_8_ch);
                imghelpappt9.setImageResource(R.drawable.help_appt_9_ch);
                imghelpappt10.setImageResource(R.drawable.help_appt_10_ch);
            }

            TextView txthelpappt1 = (TextView)view.findViewById(R.id.txthelpappt1);
            txthelpappt1.setText(text1);

            TextView txthelpappt2 = (TextView)view.findViewById(R.id.txthelpappt2);
            txthelpappt2.setText(text2);

            TextView txthelpappt3 = (TextView)view.findViewById(R.id.txthelpappt3);
            txthelpappt3.setText(text3);

            TextView txthelpappt4 = (TextView)view.findViewById(R.id.txthelpappt4);
            txthelpappt4.setText(text4);

            TextView txthelpappt5 = (TextView)view.findViewById(R.id.txthelpappt5);
            txthelpappt5.setText(text5);

            TextView txthelpappt6 = (TextView)view.findViewById(R.id.txthelpappt6);
            txthelpappt6.setText(text6);

            TextView txthelpappt7 = (TextView)view.findViewById(R.id.txthelpappt7);
            txthelpappt7.setText(text7);

            TextView txthelpappt8 = (TextView)view.findViewById(R.id.txthelpappt8);
            txthelpappt8.setText(text8);

            TextView txthelpappt9 = (TextView)view.findViewById(R.id.txthelpappt9);
            txthelpappt9.setText(text9);

            TextView txthelpappt10 = (TextView)view.findViewById(R.id.txthelpappt10);
            txthelpappt10.setText(text10);

            return view;
        }
    }

    static public class ProfileHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_profile_tab, container, false);

            ImageView imghelpprofile1,imghelpprofile2,imghelpprofile3,imghelpprofile4,imghelpprofile5,imghelpprofile6,imghelpprofile7;

            imghelpprofile1 = (ImageView)view.findViewById(R.id.imghelpprofile1);
            imghelpprofile2 = (ImageView)view.findViewById(R.id.imghelpprofile2);
            imghelpprofile3 = (ImageView)view.findViewById(R.id.imghelpprofile3);
            imghelpprofile4 = (ImageView)view.findViewById(R.id.imghelpprofile4);
            imghelpprofile5 = (ImageView)view.findViewById(R.id.imghelpprofile5);
            imghelpprofile6 = (ImageView)view.findViewById(R.id.imghelpprofile6);
            imghelpprofile7 = (ImageView)view.findViewById(R.id.imghelpprofile7);

            String text1 = "The Profile function allows you to view your own profile and edit it.\nTo access the Profile function, go to 'PROFILE' in the Home page.";
            String text2 = "Upon clicking on the 'PROFILE' button in the Home page, you will be able to see your profile. Information such as your name, age, gender, hobbies and profile picture will be displayed.";
            String text3 = "Want to edit your profile? Go ahead and hit that 'EDIT' button right there!";
            String text4 = "This will bring you to the edit profile page.\nHere, some information on your profile that can be edited would include your name, age, hobbies and profile picture.";
            String text5 = "Want to change your profile picture? No problem, just click on your current profile picture and a dialog will pop up.\nJust press 'Select from gallery' if you want to choose a photo that is in your gallery.\nOtherwise, you can also take a photo from your camera by selecting the 'Capture from camera' button!";
            String text6 = "Finished making your edits? Click on the 'DONE' button to save the changes!";
            String text7 = "And that's it! You can view your newly updated profile!";

            if (MainActivity.language.equals("Chinese")) {
                text1 = "个人资料管理功能让您查看自己的个人资料并进行编辑。\n想使用此功能的话，请点击主页中的‘个人资料’。";
                text2 = "在点击主页上的‘个人资料’按钮后，您就可以看到自己的个人资料，例如您的姓名，年龄，性别，兴趣/爱好和头像。";
                text3 = "想要编辑您的个人资料吗？那就即刻点击那个‘编辑’按钮吧！";
                text4 = "您将会被带到编辑个人资料的页面。\n在这里，您的个人资料中可编辑的一些东西包括：您的姓名，年龄，兴趣/爱好和头像。";
                text5 = "想要更改您的头像吗？没问题，只需点击您目前的头像，便会有屏幕弹出来。\n如果想从照片库选择头像，只需点击‘从照片库选择头像’这个选项即可。\n不然，如果您想从相机拍摄头像，就点击‘从相机捕捉头像’这个选项！";
                text6 = "编辑完毕了吗？那便点击‘完成’来保存您的更改！";
                text7 = "就是这么简单罢了！赶快去查看您更新的个人资料！";

                imghelpprofile1.setImageResource(R.drawable.help_profile_1_ch);
                imghelpprofile2.setImageResource(R.drawable.help_profile_2_ch);
                imghelpprofile3.setImageResource(R.drawable.help_profile_3_ch);
                imghelpprofile4.setImageResource(R.drawable.help_profile_4_ch);
                imghelpprofile5.setImageResource(R.drawable.help_profile_5_ch);
                imghelpprofile6.setImageResource(R.drawable.help_profile_6_ch);
                imghelpprofile7.setImageResource(R.drawable.help_profile_7_ch);
            }

            TextView txthelpprofile1 = (TextView)view.findViewById(R.id.txthelpprofile1);
            txthelpprofile1.setText(text1);

            TextView txthelpprofile2 = (TextView)view.findViewById(R.id.txthelpprofile2);
            txthelpprofile2.setText(text2);

            TextView txthelpprofile3 = (TextView)view.findViewById(R.id.txthelpprofile3);
            txthelpprofile3.setText(text3);

            TextView txthelpprofile4 = (TextView)view.findViewById(R.id.txthelpprofile4);
            txthelpprofile4.setText(text4);

            TextView txthelpprofile5 = (TextView)view.findViewById(R.id.txthelpprofile5);
            txthelpprofile5.setText(text5);

            TextView txthelpprofile6 = (TextView)view.findViewById(R.id.txthelpprofile6);
            txthelpprofile6.setText(text6);

            TextView txthelpprofile7 = (TextView)view.findViewById(R.id.txthelpprofile7);
            txthelpprofile7.setText(text7);

            return view;
        }
    }

    static public class LanguageHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_language_tab, container, false);

            ImageView imghelplanguage1, imghelplanguage2, imghelplanguage3;
            imghelplanguage1 = (ImageView)view.findViewById(R.id.imghelplanguage1);
            imghelplanguage2 = (ImageView)view.findViewById(R.id.imghelplanguage2);
            imghelplanguage3 = (ImageView)view.findViewById(R.id.imghelplanguage3);

            String text1 = "The Language function allows you to select a language of your choice. Based on the language chosen, Patch's language will also change.\n\n" +
                    "There are two ways to do so.\nThe first way is from the page you are shown when you enter the application. At the top left, there is an option for you to change the language.";
            String text2 = "The second way is from the home page which you can see after logging in. To go to the language function, select 'MORE'.";
            String text3 = "Then, select 'LANGUAGE' and you will be brought to the page where you can change the language.";
            String text4 = "There are two language options available, English and Chinese.";
            String text5 = "Selecting English would trigger a popup dialog that prompts you to confirm the selection in English.";
            String text6 = "On the other hand, selecting Chinese (中文) would trigger a popup dialog that prompts you to confirm the selection in Chinese.\n\nWith that, we have completed the guide for the language function!";

            if (MainActivity.language.equals("Chinese")) {
                text1 = "语言功能能让您切换 Patch 的语言。\n\n" +
                        "切换语言的方法有两种。\n第一个方法就是从开启 Patch 的第一页那里。在左上角，会有个按钮让您切换语言。";
                text2 = "第二个方法就是从主页（要登录才能看见）。首先，请点击‘更多’。";
                text3 = "接着，请点击‘语言’。之后，您将会被带到让您切换语言的一页。";
                text4 = "我们提供两种语言，英文与中文。";
                text5 = "如果您选择 English (英文)，就会有一个屏幕弹出来（英文字），让您确认您的选择。";
                text6 = "那如果您选择了中文，就会有一个屏幕弹出来（中文字），让您确认您的选择。\n\n您已经学会如何使用语言功能了，那‘语言’使用手册就在此结束了！";

                imghelplanguage1.setImageResource(R.drawable.help_language_1_ch);
                imghelplanguage2.setImageResource(R.drawable.help_language_2_ch);
                imghelplanguage3.setImageResource(R.drawable.help_language_3_ch);
            }

            TextView txthelplanguage1 = (TextView)view.findViewById(R.id.txthelplanguage1);
            txthelplanguage1.setText(text1);

            TextView txthelplanguage2 = (TextView)view.findViewById(R.id.txthelplanguage2);
            txthelplanguage2.setText(text2);

            TextView txthelplanguage3 = (TextView)view.findViewById(R.id.txthelplanguage3);
            txthelplanguage3.setText(text3);

            TextView txthelplanguage4 = (TextView)view.findViewById(R.id.txthelplanguage4);
            txthelplanguage4.setText(text4);

            TextView txthelplanguage5 = (TextView)view.findViewById(R.id.txthelplanguage5);
            txthelplanguage5.setText(text5);

            TextView txthelplanguage6 = (TextView)view.findViewById(R.id.txthelplanguage6);
            txthelplanguage6.setText(text6);

            return view;
        }
    }

    static public class FriendsHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_friends_tab, container, false);

            ImageView imghelpfriends1,imghelpfriends2,imghelpfriends3,imghelpfriends4,imghelpfriends5,
                    imghelpfriends6,imghelpfriends7,imghelpfriends8,imghelpfriends9;

            imghelpfriends1 = (ImageView)view.findViewById(R.id.imghelpfriends1);
            imghelpfriends2 = (ImageView)view.findViewById(R.id.imghelpfriends2);
            imghelpfriends3 = (ImageView)view.findViewById(R.id.imghelpfriends3);
            imghelpfriends4 = (ImageView)view.findViewById(R.id.imghelpfriends4);
            imghelpfriends5 = (ImageView)view.findViewById(R.id.imghelpfriends5);
            imghelpfriends6 = (ImageView)view.findViewById(R.id.imghelpfriends6);
            imghelpfriends7 = (ImageView)view.findViewById(R.id.imghelpfriends7);
            imghelpfriends8 = (ImageView)view.findViewById(R.id.imghelpfriends8);
            imghelpfriends9 = (ImageView)view.findViewById(R.id.imghelpfriends9);

            String text1 = "The Friends function allows you to view your friends and their information, feedback on them, delete them, block them, and view feedbacks on them.\n" +
                    "To access the Friends function, click on 'MORE' in the Home page.";
            String text2 = "Then, select the 'FRIENDS' button.";
            String text3 = "Here, you can view a list of your friends whose friend requests you have accepted or friends who have accepted your friend request.";
            String text4 = "To view more information of a certain friend, click on their name. For example, Peter Lim.";
            String text5 = "After that, you will be able to see Peter's information such as his name, gender, age, rating, hobbies and profile picture.\n\n" +
                    "Other than that, you will also be able to see 4 buttons at the bottom. We will talk about that in a while.\n\n" +
                    "If you are wondering what is this 'Rating', it is a score that is given to each user by his/her friends or matched up friends. This can be achieved through the 'FEEDBACK' button which we will be talking about in a little while.\n" +
                    "The max rating a user can receive is 5.00. Hence, you can see that Peter has a relatively high rating!";
            String text6 = "Now, let's see what the 'FEEDBACK' button does. Press it and you will be brought to this page.\n\n" +
                    "Here, you can rate the Peter on a scale of 1 to 5 by dragging the small pink circle on the pink line.\n" +
                    "Then, make sure to enter a feedback on Peter be it good or bad.\n\n" +
                    "Your feedback would be highly valued since other users who may potentially be matched up with Peter will know what kind of person Peter is.\n" +
                    "\nOnce you are done, press the 'SUBMIT' button.";
            String text7 = "Then, we will move on to the 'BLOCK' button.\n" +
                    "You will be prompted to confirm to block Peter.\n\n" +
                    "Think carefully before you hit the 'CONFIRM' button because once you do so, Peter will be added to your blacklist, meaning he will be removed from your friend list, and you will never be able to match up with him again!";
            String text8 = "Next, we have the 'DELETE FRIEND' button.\n" +
                    "What this button does is that it removes Peter from your friend list, meaning you will not be able to make appointments with him, or chat, or view him in your friend list anymore.\n\n" +
                    "However, the difference between this and the 'BLOCK' button is that even after you delete Peter from your friend list, you may encounter him in the future again using the match-up function.\n\n" +
                    "Unsure of what the match-up function is? Check out the 'MATCH UP' help manual!";
            String text9 = "Lastly, we have the 'VIEW FEEDBACKS' button. This button allows you to view all the feedbacks Peter has received from other Patch users, be it good or bad.\n\n" +
                    "Of course, any feedbacks you have written through the 'FEEDBACK' button will also be added to this!\n\n" +
                    "With that, we will end the help manual on the Friends function here!";

            if (MainActivity.language.equals("Chinese")) {
                text1 = "好友功能让您查看您的好友列表和他们的个人资料。此外，您还可以给他们您的反馈、将他们从您的好友列表中除去、拉黑他们、以及查看他们所收到的反馈。\n" +
                        "想使用好友功能的话，首先请点击主页中的‘更多’。";
                text2 = "接着，便点击‘好友’。";
                text3 = "在这里，您可以查看您的好友列表（接受您的好友请求的用户或向您发出好友请求并且接受的用户）。";
                text4 = "想查看某位好友的个人资料的话，就请点击他们的名字，例如，Peter Lim。";
                text5 = "接下来，您将会看到 Peter 的个人资料，例如他的姓名，性别，年龄，评分，兴趣/爱好以及头像。\n\n" +
                        "除此之外，您还可以在底部看到四个按钮。我们一会儿便会讨论这些按钮的功能。\n\n" +
                        "如果您想知道这个‘评分’是什么，它是此用户的好友/配对友给他/她的一个评分。这是通过我们将在一会儿讨论的'反馈'功能。\n" +
                        "每个用户所能收到的最高评分是5.00。很明显的，您可以看到 Peter 的评分相当地高！";
                text6 = "现在，让我们看看‘反馈’按钮的作用。 按下它，您将进入此页。\n\n" +
                        "在这里，您可以通过拉动粉红线上的那个小粉色圆圈来对 Peter 进行评级（从1-5）。\n" +
                        "不管您对 Peter 的反馈是好还是坏，都请把它写下来！不用担心，我们会保密您的身份哦！\n\n" +
                        "我们非常欢迎您对其他用户的任何反馈！此外，这还能让与 Peter 配对的其他用户更了解 Peter 的为人。\n" +
                        "一旦完成了您对 Peter 的反馈，请点击‘提交’。";
                text7 = "然后，我们将讨论‘拉黑’这个按钮的作用。\n" +
                        "一旦点击此按钮，就会有屏幕弹出来，让您确认是否拉黑 Peter。\n\n" +
                        "在点击‘确定’之前要仔细考虑，因为一旦您确认了，Peter 将被添加到您的黑名单中。这意味着他将会从您的好友列表中除去，并且您也将永远无法再与他配对了！";
                text8 = "接下来，让我们来说说‘删除好友’按钮的作用。\n" +
                        "这个按钮的作用是将 Peter 从您的好友列表中除去。这意味着您将无法再与他预约，聊天，或者使用与他的好友功能了。\n\n" +
                        "‘删除好友’和‘拉黑’的区别在于，即使您从您的好友列表中删除了 Peter，您也有可能再次使用配对功遇到他。\n\n" +
                        "不确定配对功能是什么吗？那就请查看‘配对’的使用手册吧!\n";
                text9 = "最后，我们还有‘查看反馈’这个按钮。通过此按钮，您可以查看其他 Patch 用户给 Peter 的所有反馈，无论好坏。\n\n" +
                        "当然，您通过‘反馈’按钮提交的任何反馈也在其中！\n\n" +
                        "好友功能的使用手册就到此结束了哦！";

                imghelpfriends1.setImageResource(R.drawable.help_friends_1_ch);
                imghelpfriends2.setImageResource(R.drawable.help_friends_2_ch);
                imghelpfriends3.setImageResource(R.drawable.help_friends_3_ch);
                imghelpfriends4.setImageResource(R.drawable.help_friends_4_ch);
                imghelpfriends5.setImageResource(R.drawable.help_friends_5_ch);
                imghelpfriends6.setImageResource(R.drawable.help_friends_6_ch);
                imghelpfriends7.setImageResource(R.drawable.help_friends_7_ch);
                imghelpfriends8.setImageResource(R.drawable.help_friends_8_ch);
                imghelpfriends9.setImageResource(R.drawable.help_friends_9_ch);
            }

            TextView txthelpfriends1 = (TextView)view.findViewById(R.id.txthelpfriends1);
            txthelpfriends1.setText(text1);

            TextView txthelpfriends2 = (TextView)view.findViewById(R.id.txthelpfriends2);
            txthelpfriends2.setText(text2);

            TextView txthelpfriends3 = (TextView)view.findViewById(R.id.txthelpfriends3);
            txthelpfriends3.setText(text3);

            TextView txthelpfriends4 = (TextView)view.findViewById(R.id.txthelpfriends4);
            txthelpfriends4.setText(text4);

            TextView txthelpfriends5 = (TextView)view.findViewById(R.id.txthelpfriends5);
            txthelpfriends5.setText(text5);

            TextView txthelpfriends6 = (TextView)view.findViewById(R.id.txthelpfriends6);
            txthelpfriends6.setText(text6);

            TextView txthelpfriends7 = (TextView)view.findViewById(R.id.txthelpfriends7);
            txthelpfriends7.setText(text7);

            TextView txthelpfriends8 = (TextView)view.findViewById(R.id.txthelpfriends8);
            txthelpfriends8.setText(text8);

            TextView txthelpfriends9 = (TextView)view.findViewById(R.id.txthelpfriends9);
            txthelpfriends9.setText(text9);

            return view;
        }
    }

    static public class RequestsHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_requests_tab, container, false);

            ImageView imghelprequests1,imghelprequests2,imghelprequests3,imghelprequests4,
                    imghelprequests5,imghelprequests6,imghelprequests7,imghelprequests8,imghelprequests9;

            imghelprequests1 = (ImageView)view.findViewById(R.id.imghelprequests1);
            imghelprequests2 = (ImageView)view.findViewById(R.id.imghelprequests2);
            imghelprequests3 = (ImageView)view.findViewById(R.id.imghelprequests3);
            imghelprequests4 = (ImageView)view.findViewById(R.id.imghelprequests4);
            imghelprequests5 = (ImageView)view.findViewById(R.id.imghelprequests5);
            imghelprequests6 = (ImageView)view.findViewById(R.id.imghelprequests6);
            imghelprequests7 = (ImageView)view.findViewById(R.id.imghelprequests7);
            imghelprequests8 = (ImageView)view.findViewById(R.id.imghelprequests8);
            imghelprequests9 = (ImageView)view.findViewById(R.id.imghelprequests9);

            String text1 = "The Requests function allows you to acccept and decline friend requests from users you have been matched up with.\n" +
                    "It also allows you to send requests to users you have been matched up to.\nAfter interacting with the user in the match up conversations, you can send requests to them when you feel that both of you get along well and want to add one another as friends.\n" +
                    "To learn more about the match-up function, head to the 'MATCH UP' help manual!\n" +
                    "To access the Request function, go to 'MATCH-UP' in the Home page.";
            String text2 = "Then, select the 'CONVERSATIONS' button.";
            String text3 = "Here, you can see a list of users you have been matched up with. We will start with sending a request to a user in this list.";
            String text4 = "Click on the name of the user you want to send a friend request to.";
            String text5 = "This will bring you to your conversation with him/her. Press the 'SEND REQUEST' button at the top and you will be prompted to confirm the sending of friend request.\n\n" +
                    "Select 'CONFIRM' to send the request. If not, press 'CANCEL' to cancel the sending of request.";
            String text6 = "Now, let's move on to viewing friend requests sent to you by other users you have been matched up with.\nGo back to the previous page and hit the 'CHECK REQUEST' button at the top.";
            String text7 = "Oh? Looks like Peter Lim has sent you a request!\n\nNow, would you like to accept the request? Or decline it?";
            String text8 = "Are you ready to become friends with Peter? If you are, hit that 'ACCEPT' button!\n" +
                    "You will be prompted to confirm your choice.\nSelect 'ACCEPT' if you are sure you want to accept the request.\nIf not, select 'CANCEL'.\n\n" +
                    "Once you have accepted the requests, all the previous conversations you had with Peter will be moved to the 'CHATS' function in the Home page. Peter will also be removed from your match-up conversations.\n" +
                    "\nTo learn more about the Chats function, head on to the 'CHATS' help manual!\n\n" +
                    "Now that you have become friends with Peter, the functions 'APPOINTMENTS', 'CHATS', and 'FRIENDS' can be used for Peter!\n" +
                    "To learn more about it, check out the respective help manuals!";
            String text9 = "On the other hand, if you feel that you are not ready to be friends with Peter (you do not know him that well yet or for any other reasons), decline the request by hitting the 'DECLINE' button!\n" +
                    "You will be prompted to confirm your choice.\nSelect 'DECLINE' if you are sure you want to decline the request.\nOtherwise, select 'CANCEL'.\n\n" +
                    "If you decline the request, the request will be removed from the request list.";

            if (MainActivity.language.equals("Chinese")) {
                text1 = "好友请求功能让您接受或拒绝与您配对的用户的好友请求。\n" +
                        "它还可以让您向与您配对的用户发送好友请求。\n" +
                        "当您与您的配对友们在对话中交流后，如果您认为两方相处地融洽并且都想要成为对方的好友时，您可以向他们发送好友请求。\n" +
                        "想要更了解配对功能的话，请前往‘配对’的使用手册！\n" +
                        "那如果想要使用好友请求功能的话，请点击主页中的‘配对’。";
                text2 = "然后，请点击‘对话’。";
                text3 = "在这里，您可以看到与您配对的用户列表。首先，让我们向列表中的一位用户发送好友请求吧！";
                text4 = "接下来，请点击您想要发送好友请求的用户的名字！";
                text5 = "您将会被带到您与他/她的对话。 您只需点击顶部的‘发送好友请求’按钮，便会有屏幕弹出来，让您确定发送好友请求。\n\n" +
                        "如果您确定想要发送好友请求给这位用户，就点击‘确定’。不然，便点击‘取消’来取消发送好友请求。";
                text6 = "现在，让我们查看与您配对的其他用户发送给您的好友请求。\n" +
                        "返回到上一页，并且点击顶部的‘查看好友请求’按钮。";
                text7 = "哦？看来 Peter Lim 向您发送了好友请求耶！\n\n" +
                        "现在，您是否愿意接受 Peter Lim 的好友请求？还是不愿意呢？";
                text8 = "您已经准备好跟 Peter 成为好友了吗？如果是，就点击‘接受’！\n" +
                        "接下来，便会有屏幕弹出来，确认您的选择。\n" +
                        "如果您确定要接受请求，就点击‘确定’。\n" +
                        "要不然，就点击‘取消’。\n\n" +
                        "一旦您接受了 Peter 的好友请求，您与 Peter 之前的所有对话都将会被转移至主页的‘好友对话’。Peter 也将从您的配对列表中除去。\n\n" +
                        "想要进一步地了解好友对话功能的话，就前往‘好友对话’的使用手册！\n\n" +
                        "既然您已经与 Peter 成为了好友，那么您便可以和 Peter 使用‘预约’，‘好友对话’和‘好友’有关的功能！\n" +
                        "想要了解更多的话，请查看相应的使用手册！";
                text9 = "不过，要是您觉得自己还没有准备好与 Peter 成为好友（您还不太了解他或出于任何其他原因）的话，就请点击‘拒绝’按钮方可拒绝 Peter 的好友请求！\n" +
                        "在点击了‘拒绝’按钮后，便会有屏幕弹出来，让您确定您的选择。\n" +
                        "如果您确定要拒绝 Peter 的好友请求的话，就点击‘确定’。\n" +
                        "不然，就点击‘取消’。\n\n" +
                        "如果您拒绝了 Peter 的好友请求，Peter 的好友请求就会从请求列表中除去。";

                imghelprequests1.setImageResource(R.drawable.help_requests_1_ch);
                imghelprequests2.setImageResource(R.drawable.help_requests_2_ch);
                imghelprequests3.setImageResource(R.drawable.help_requests_3_ch);
                imghelprequests4.setImageResource(R.drawable.help_requests_4_ch);
                imghelprequests5.setImageResource(R.drawable.help_requests_5_ch);
                imghelprequests6.setImageResource(R.drawable.help_requests_6_ch);
                imghelprequests7.setImageResource(R.drawable.help_requests_7_ch);
                imghelprequests8.setImageResource(R.drawable.help_requests_8_ch);
                imghelprequests9.setImageResource(R.drawable.help_requests_9_ch);
            }

            TextView txthelprequests1 = (TextView)view.findViewById(R.id.txthelprequests1);
            txthelprequests1.setText(text1);

            TextView txthelprequests2 = (TextView)view.findViewById(R.id.txthelprequests2);
            txthelprequests2.setText(text2);

            TextView txthelprequests3 = (TextView)view.findViewById(R.id.txthelprequests3);
            txthelprequests3.setText(text3);

            TextView txthelprequests4 = (TextView)view.findViewById(R.id.txthelprequests4);
            txthelprequests4.setText(text4);

            TextView txthelprequests5 = (TextView)view.findViewById(R.id.txthelprequests5);
            txthelprequests5.setText(text5);

            TextView txthelprequests6 = (TextView)view.findViewById(R.id.txthelprequests6);
            txthelprequests6.setText(text6);

            TextView txthelprequests7 = (TextView)view.findViewById(R.id.txthelprequests7);
            txthelprequests7.setText(text7);

            TextView txthelprequests8 = (TextView)view.findViewById(R.id.txthelprequests8);
            txthelprequests8.setText(text8);

            TextView txthelprequests9 = (TextView)view.findViewById(R.id.txthelprequests9);
            txthelprequests9.setText(text9);

            return view;
        }
    }
}