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
            txthelpappt1.setText("To access the appointments function, go to 'APPOINTMENTS' in the Home page.");

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

            return view;
        }
    }

    static public class RequestsHelpFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.help_requests_tab, container, false);

            return view;
        }
    }
}