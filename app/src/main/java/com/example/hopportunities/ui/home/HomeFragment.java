package com.example.hopportunities.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hopportunities.CreateAccount;
import com.example.hopportunities.MainActivity;
import com.example.hopportunities.R;
import com.example.hopportunities.data.model.Question;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MainActivity mainActivity;
    private CreateAccount createAccount;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        homeViewModel =
                new ViewModelProvider(mainActivity).get(HomeViewModel.class);
        String firstName = homeViewModel.firstName;

        View root;
        System.out.println("isStudent: " + homeViewModel.isStudent);
        if (homeViewModel.isStudent) {
            root = inflater.inflate(R.layout.fragment_student_home, container, false);
            LinearLayout subjectsLayout = (LinearLayout) root.findViewById(R.id.subjects2);
            Toast.makeText(getContext(), "Hello", Toast.LENGTH_LONG).show();
            for (String subject : homeViewModel.subjects) {
                TextView subjTextView = new TextView(mainActivity);
                subjTextView.setText("   " + subject + "   ");
//              android:layout_width="88dp"
//              android:layout_height="43dp"
//              android:layout_marginStart="20dp"
//              android:layout_marginBottom="8dp"

                subjectsLayout.addView(subjTextView);
            }
        } else {
            root = inflater.inflate(R.layout.fragment_tutor_home, container, false);
            TextView hello_name_real = root.findViewById(R.id.hello_name);
            hello_name_real.setText("Hello, " + firstName + "!");
            TextView num_students_contacted = root.findViewById(R.id.num_students_contacted);
//            num_students_contacted.setText(notificationsViewModel.GetStudentsContacted().getValue().size());
            TextView num_questions_answered = root.findViewById(R.id.num_questions_answered);
            LinearLayout subjectsLayout = (LinearLayout) root.findViewById(R.id.subjects);
            if (homeViewModel.subjects == null) {
                System.out.println("Error");
                return null;
            }
            for (String subject : homeViewModel.subjects) {
                TextView subjTextView = new TextView(mainActivity);
                subjTextView.setText("      " + subject + "     ");
//              android:layout_width="88dp"
//              android:layout_height="43dp"
//              android:layout_marginStart="20dp"
//              android:layout_marginBottom="8dp"

                subjectsLayout.addView(subjTextView);
            }
        }
        Button viewMore = root.findViewById(R.id.button_view_more);
//        viewMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        // notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        //   @Override
        //   public void onChanged(@Nullable String s) {
        //      textView.setText(s);
        //  }
        // });
        return root;
    }
}
