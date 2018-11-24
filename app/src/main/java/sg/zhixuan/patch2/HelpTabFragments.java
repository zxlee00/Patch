package sg.zhixuan.patch2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HelpTabFragments {

    static public class MatchUpHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_matchup_tab, container, false);

            TextView txthelpmatchup1 = (TextView)view.findViewById(R.id.txthelpmatchup1);
            txthelpmatchup1.setText("The Match-Up function allows you to interact with other users in Patch based on your hobbies.\nTo access the match-up function, go to 'MATCH-UP' in the Home page.\n\n" +
                    "Please note that the users you interact with in match-up are not automatically part of your friend list. Both parties have to approve before becoming friends (to be able to use the Appointments, Friends and Chats functions).\n\n" +
                    "On the other hand, match-up does not require approval from both sides. We will talk more about it in this guide so continue reading on!");

            TextView txthelpmatchup2 = (TextView)view.findViewById(R.id.txthelpmatchup2);
            txthelpmatchup2.setText("Then, you will see two buttons. Click on the button 'NEW MATCH UP' to proceed on with the match-up.");

            TextView txthelpmatchup3 = (TextView)view.findViewById(R.id.txthelpmatchup3);
            txthelpmatchup3.setText("As you can see here, we have found you another Patch user, Peter Lim.\nInformation such as the user's age, gender, rating will be displayed.\n" +
                    "To maintain the user's privacy, during the match-up, we will keep the user's picture private.\nThe max rating a user can get is 5.00. In this case, Peter has a rating of 4.90. This will be based on the feedbacks from other users who are friends with Peter.\n" +
                    "Other than these, Peter's hobbies will also be displayed.\n" +
                    "\nYou can then decide if you want to be matched up with Peter after looking through his hobbies and other details such as gender, age and rating by selecting the 'YES' or 'NO' buttons.");

            TextView txthelpmatchup4 = (TextView)view.findViewById(R.id.txthelpmatchup4);
            txthelpmatchup4.setText("Wait... What is this 'VIEW FEEDBACKS' button you see here?");

            TextView txthelpmatchup5 = (TextView)view.findViewById(R.id.txthelpmatchup5);
            txthelpmatchup5.setText("Well, this allows you to view the feedbacks Peter's friends have given him, be it good or bad. These are also the feedbacks that form Peter's rating.\n" +
                    "You might want to check it out before selecting the 'YES' or 'NO' buttons to accept or reject the match up.\n\n" +
                    "To know more about giving feedbacks to other users, you can check out the 'FRIENDS' help manual!");

            TextView txthelpmatchup6 = (TextView)view.findViewById(R.id.txthelpmatchup6);
            txthelpmatchup6.setText("Now, let's move on to checking out the match-ups you have accepted! Go ahead and select the 'CONVERSATIONS' button!");

            TextView txthelpmatchup7 = (TextView)view.findViewById(R.id.txthelpmatchup7);
            txthelpmatchup7.setText("This is where you can see a list of the users you have accepted the match ups with. Those are the users whose names are in purple.\n\nWhat about those in red?\n" +
                    "Well... just as you can find other users, other users can find you too! Those users who found you will be denoted in red.");

            TextView txthelpmatchup8 = (TextView)view.findViewById(R.id.txthelpmatchup8);
            txthelpmatchup8.setText("You have matched up with Peter just now. Now, talk to him! Just click on his name and you will be brought over to the chats screen where the two of you can send messages to one another!");

            TextView txthelpmatchup9 = (TextView)view.findViewById(R.id.txthelpmatchup9);
            txthelpmatchup9.setText("Going back to the previous page, just now all we had done is click on the name. What if we long pressed on it? A menu will appear! You are given the options to view the user's details, or delete them from your list of matched up users!");

            TextView txthelpmatchup10 = (TextView)view.findViewById(R.id.txthelpmatchup10);
            txthelpmatchup10.setText("First, let's take a look at user details. You will be brought to a page where you can see the user's name, age, gender, rating and hobbies. There are also two buttons, 'VIEW FEEDBACKS' and 'FEEDBACK'.");

            TextView txthelpmatchup11 = (TextView)view.findViewById(R.id.txthelpmatchup11);
            txthelpmatchup11.setText("Clicking on 'VIEW FEEDBACKS' will bring you to a page which has a list of feedbacks the user has received from other users. Here, you can further evaluate the user!");

            TextView txthelpmatchup12 = (TextView)view.findViewById(R.id.txthelpmatchup12);
            txthelpmatchup12.setText("Clicking on the 'FEEDBACK' button will bring you to a page where you can enter a feedback for the user.\n\n" +
                    "Here, you can rate the Peter on a scale of 1 to 5 by dragging the small pink circle on the pink line.\n" +
                    "Then, make sure to enter a feedback on Peter be it good or bad.\n\n" +
                    "This feedback can also be seen by other users. Your feedback will be greatly appreciated and it will help other users to evaluate the kind of person Peter is!\n\n" +
                    "So, go ahead and enter your feedback on Peter!");

            TextView txthelpmatchup13 = (TextView)view.findViewById(R.id.txthelpmatchup13);
            txthelpmatchup13.setText("Regret accepting a match-up? No worries, just long press at the name of the user you want to delete and you will be prompted to confirm the deletion.\nClick on 'CONFIRM' if you want to proceed on with the deletion.\n" +
                    "Otherwise, click on 'CANCEL'.\n\nSince other users are also able to find you, you will also definitely be able to remove these match-ups! Just delete them if you don't want to be matched up with them.");

            TextView txthelpmatchup14 = (TextView)view.findViewById(R.id.txthelpmatchup14);
            txthelpmatchup14.setText("Tada! Peter Lim has been removed from your list of matched up users. You will no longer be able to contact him and vice-versa.\n\nWant to add matched up users into your friend list in Patch (to be able to use Appointments, Chats and Friends functions)?\n" +
                    "Head to the 'REQUESTS' help manual then!");

            return view;
        }
    }

    static public class ChatsHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_chats_tab, container, false);

            return view;
        }
    }

    static public class AppointmentsHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_appointments_tab, container, false);

            TextView txthelpappt1 = (TextView)view.findViewById(R.id.txthelpappt1);
            txthelpappt1.setText("The Appointments function allows you to keep track of the appointments you have with friends you made in Patch.\nTo access the appointments function, go to 'APPOINTMENTS' in the Home page.");

            TextView txthelpappt2 = (TextView)view.findViewById(R.id.txthelpappt2);
            txthelpappt2.setText("Once you have entered the 'APPOINTMENTS', you will be able to see a list of appointments that you have set. Information like your friend's picture, his/her name, the appointment name, location, date and time will be displayed.");

            TextView txthelpappt3 = (TextView)view.findViewById(R.id.txthelpappt3);
            txthelpappt3.setText("Within 2 hours of the time and date set for your appointment, you can get a notification to remind you that you have an appointment coming up.\nThis way, you will never forget about your appointment!");

            TextView txthelpappt4 = (TextView)view.findViewById(R.id.txthelpappt4);
            txthelpappt4.setText("What if the appointment was cancelled and you want to delete it? Just long hold on the appointment you want to delete!");

            TextView txthelpappt5 = (TextView)view.findViewById(R.id.txthelpappt5);
            txthelpappt5.setText("After the long press, you will receive a prompt to confirm the deletion of the appointment. Press 'CONFIRM' to proceed with the deletion and 'CANCEL' if you do not want to delete the appointment!");

            TextView txthelpappt6 = (TextView)view.findViewById(R.id.txthelpappt6);
            txthelpappt6.setText("So far, we have only talked about viewing appointments. Let's talk about making these appointments now! In the bottom right corner, you will see a '+' icon. Just click on it and it will bring you to the first step in creating an appointment!");

            TextView txthelpappt7 = (TextView)view.findViewById(R.id.txthelpappt7);
            txthelpappt7.setText("In the next page, you will be able to see the list of friends you have made in Patch. Is yours empty? No worries, you just need to make some friends before you can use this function. Proceed on to the 'MATCH-UP' and 'REQUESTS' help manual to learn more about making friends!\n" +
                    "\nWhat? Your list is not empty? Then continue reading on! Simply select the friend you want to make an appointment with and you will be brought to the next page!");

            TextView txthelpappt8 = (TextView)view.findViewById(R.id.txthelpappt8);
            txthelpappt8.setText("See this page? Let's continue! First, you have to enter the name of your appointment~");

            TextView txthelpappt9 = (TextView)view.findViewById(R.id.txthelpappt9);
            txthelpappt9.setText("How do I set the date for this appointment? Easy! Just click on the 'DATE' button and you will see the following dialog. Simply scroll through to select your date.");

            TextView txthelpappt10 = (TextView)view.findViewById(R.id.txthelpappt10);
            txthelpappt10.setText("What about the time? It's the same! Just click on 'TIME' to be able to choose a time!\n\nWith that, you have learnt all about the appointments function!");

            return view;
        }
    }

    static public class ProfileHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_profile_tab, container, false);

            TextView txthelpprofile1 = (TextView)view.findViewById(R.id.txthelpprofile1);
            txthelpprofile1.setText("The Profile function allows you to view your own profile and edit it.\nTo access the Profile function, go to 'PROFILE' in the Home page.");

            TextView txthelpprofile2 = (TextView)view.findViewById(R.id.txthelpprofile2);
            txthelpprofile2.setText("Upon clicking on the 'PROFILE' button in the Home page, you will be able to see your profile. Information such as your name, age, gender, hobbies and profile picture will be displayed.");

            TextView txthelpprofile3 = (TextView)view.findViewById(R.id.txthelpprofile3);
            txthelpprofile3.setText("Want to edit your profile? Go ahead and hit that 'EDIT' button right there!");

            TextView txthelpprofile4 = (TextView)view.findViewById(R.id.txthelpprofile4);
            txthelpprofile4.setText("This will bring you to the edit profile page.\nHere, some information on your profile that can be edited would include your name, age, hobbies and profile picture.");

            TextView txthelpprofile5 = (TextView)view.findViewById(R.id.txthelpprofile5);
            txthelpprofile5.setText("Want to change your profile picture? No problem, just click on your current profile picture and a dialog will pop up.\nJust press 'Select from gallery' if you want to choose a photo that is in your gallery.\nOtherwise, you can also take a photo from your camera by selecting the 'Capture from camera' button!");

            TextView txthelpprofile6 = (TextView)view.findViewById(R.id.txthelpprofile6);
            txthelpprofile6.setText("Finished making your edits? Click on the 'DONE' button to save the changes!");

            TextView txthelpprofile7 = (TextView)view.findViewById(R.id.txthelpprofile7);
            txthelpprofile7.setText("And that's it! You can view your newly updated profile!");

            return view;
        }
    }

    static public class LanguageHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_language_tab, container, false);

            return view;
        }
    }

    static public class FriendsHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_friends_tab, container, false);

            TextView txthelpfriends1 = (TextView)view.findViewById(R.id.txthelpfriends1);
            txthelpfriends1.setText("The Friends function allows you to view your friends and their information, feedback on them, delete them, block them, and view feedbacks on them.\n" +
                    "To access the Friends function, click on 'MORE' in the Home page.");

            TextView txthelpfriends2 = (TextView)view.findViewById(R.id.txthelpfriends2);
            txthelpfriends2.setText("Then, select the 'FRIENDS' button.");

            TextView txthelpfriends3 = (TextView)view.findViewById(R.id.txthelpfriends3);
            txthelpfriends3.setText("Here, you can view a list of your friends whose friend requests you have accepted or friends who have accepted your friend request.");

            TextView txthelpfriends4 = (TextView)view.findViewById(R.id.txthelpfriends4);
            txthelpfriends4.setText("To view more information of a certain friend, click on their name. For example, Peter Lim.");

            TextView txthelpfriends5 = (TextView)view.findViewById(R.id.txthelpfriends5);
            txthelpfriends5.setText("After that, you will be able to see Peter's information such as his name, gender, age, rating, hobbies and profile picture.\n\n" +
                    "Other than that, you will also be able to see 4 buttons at the bottom. We will talk about that in a while.\n\n" +
                    "If you are wondering what is this 'Rating', it is a score that is given to each user by his/her friends. This can be achieved through the 'FEEDBACK' button which we will be talking about in a little while.\n" +
                    "The max rating a user can receive is 5.00. Hence, you can see that Peter has a relatively high rating!");

            TextView txthelpfriends6 = (TextView)view.findViewById(R.id.txthelpfriends6);
            txthelpfriends6.setText("Now, let's see what the 'FEEDBACK' button does. Press it and you will be brought to this page.\n\n" +
                    "Here, you can rate the Peter on a scale of 1 to 5 by dragging the small pink circle on the pink line.\n" +
                    "Then, make sure to enter a feedback on Peter be it good or bad.\n\n" +
                    "Your feedback would be highly valued since other users who may potentially be matched up with Peter will know what kind of person Peter is.\n" +
                    "\nOnce you are done, press the 'SUBMIT' button.");

            TextView txthelpfriends7 = (TextView)view.findViewById(R.id.txthelpfriends7);
            txthelpfriends7.setText("Then, we will move on to the 'BLOCK' button.\n" +
                    "You will be prompted to confirm to block Peter.\n\n" +
                    "Think carefully before you hit the 'CONFIRM' button because once you do so, Peter will be added to your blacklist, meaning he will be removed from your friend list, and you will never be able to match up with him again!");

            TextView txthelpfriends8 = (TextView)view.findViewById(R.id.txthelpfriends8);
            txthelpfriends8.setText("Next, we have the 'DELETE FRIEND' button.\n" +
                    "What this button does is that it removes Peter from your friend list, meaning you will not be able to make appointments with him, or chat, or view him in your friend list anymore.\n\n" +
                    "However, the difference between this and the 'BLOCK' button is that even after you delete Peter from your friend list, you may encounter him in the future again using the match-up function.\n\n" +
                    "Unsure of what the match-up function is? Check out the 'MATCH UP' help manual!");

            TextView txthelpfriends9 = (TextView)view.findViewById(R.id.txthelpfriends9);
            txthelpfriends9.setText("Lastly, we have the 'VIEW FEEDBACKS' button. This button allows you to view all the feedbacks Peter has received from other Patch users, be it good or bad.\n\n" +
                    "Of course, any feedbacks you have written through the 'FEEDBACK' button will also be added to this!\n\n" +
                    "With that, we will end the help manual on the Friends function here!");

            return view;
        }
    }

    static public class RequestsHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_requests_tab, container, false);

            TextView txthelprequests1 = (TextView)view.findViewById(R.id.txthelprequests1);
            txthelprequests1.setText("The Requests function allows you to acccept and decline friend requests from users you have been matched up with.\n" +
                    "It also allows you to send requests to users you have been matched up to.\nAfter interacting with the user in the match up conversations, you can send requests to them when you feel that both of you get along well and want to add one another as friends.\n" +
                    "To learn more about the match-up function, head to the 'MATCH UP' help manual!\n" +
                    "To access the Request function, go to 'MATCH-UP' in the Home page.");

            TextView txthelprequests2 = (TextView)view.findViewById(R.id.txthelprequests2);
            txthelprequests2.setText("Then, select the 'CONVERSATIONS' button.");

            TextView txthelprequests3 = (TextView)view.findViewById(R.id.txthelprequests3);
            txthelprequests3.setText("Here, you can see a list of users you have been matched up with. We will start with sending a request to a user in this list.");

            TextView txthelprequests4 = (TextView)view.findViewById(R.id.txthelprequests4);
            txthelprequests4.setText("Click on the name of the user you want to send a friend request to.");

            TextView txthelprequests5 = (TextView)view.findViewById(R.id.txthelprequests5);
            txthelprequests5.setText("This will bring you to your conversation with him/her. Press the 'SEND REQUEST' button at the top and you will be prompted to confirm the sending of friend request.\n\n" +
                    "Select 'CONFIRM' to send the request. If not, press 'CANCEL' to cancel the sending of request.");

            TextView txthelprequests6 = (TextView)view.findViewById(R.id.txthelprequests6);
            txthelprequests6.setText("Now, let's move on to viewing friend requests sent to you by other users you have been matched up with.\nGo back to the previous page and hit the 'CHECK REQUEST' button at the top.");

            TextView txthelprequests7 = (TextView)view.findViewById(R.id.txthelprequests7);
            txthelprequests7.setText("Oh? Looks like Peter Lim has sent you a request!\n\nNow, would you like to accept the request? Or decline it?");

            TextView txthelprequests8 = (TextView)view.findViewById(R.id.txthelprequests8);
            txthelprequests8.setText("Are you ready to become friends with Peter? If you are, hit that 'ACCEPT' button!\n" +
                    "You will be prompted to confirm your choice.\nSelect 'ACCEPT' if you are sure you want to accept the request.\nIf not, select 'CANCEL'.\n\n" +
                    "Once you have accepted the requests, all the previous conversations you had with Peter will be moved to the 'CHATS' function in the Home page. Peter will also be removed from your match-up conversations.\n" +
                    "\nTo learn more about the Chats function, head on to the 'CHATS' help manual!\n\n" +
                    "Now that you have become friends with Peter, the functions 'APPOINTMENTS', 'CHATS', and 'FRIENDS' can be used for Peter!\n" +
                    "To learn more about it, check out the respective help manuals!");

            TextView txthelprequests9 = (TextView)view.findViewById(R.id.txthelprequests9);
            txthelprequests9.setText("On the other hand, if you feel that you are not ready to be friends with Peter (you do not know him that well yet or for any other reasons), decline the request by hitting the 'DECLINE' button!\n" +
                    "You will be prompted to confirm your choice.\nSelect 'DECLINE' if you are sure you want to decline the request.\nOtherwise, select 'CANCEL'.\n\n" +
                    "If you decline the request, the request will be removed from the request list.");

            return view;
        }
    }
}