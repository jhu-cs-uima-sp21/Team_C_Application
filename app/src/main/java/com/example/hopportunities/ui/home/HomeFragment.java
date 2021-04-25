package com.example.hopportunities.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.hopportunities.Student;
import com.example.hopportunities.StudentAdapter;
import com.example.hopportunities.Tutor;
import com.example.hopportunities.TutorAdapter;
import com.example.hopportunities.ViewTutor;
import com.example.hopportunities.data.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MainActivity mainActivity;
    private CreateAccount createAccount;
    private ListView lv;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    protected ArrayAdapter aa;
    private static final String TAG = "dbref: ";
    private View root;
    private ArrayList users;
    private int numContacts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        homeViewModel =
                new ViewModelProvider(mainActivity).get(HomeViewModel.class);
        String firstName = homeViewModel.firstName;

        //View root;
        System.out.println("isStudent: " + homeViewModel.isStudent);
        users = new ArrayList();
        numContacts = 0;
        if (homeViewModel.isStudent) {

            root = inflater.inflate(R.layout.fragment_student_home, container, false);
            LinearLayout subjectsLayout = (LinearLayout) root.findViewById(R.id.subjects2);
            lv = root.findViewById(R.id.tutors);
            aa = new TutorAdapter(mainActivity,R.layout.tutor_layout,users);
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

            aa = new StudentAdapter(mainActivity,R.layout.tutor_layout,users);
            TextView hello_name_real = root.findViewById(R.id.hello_name);
            hello_name_real.setText("Hello, " + firstName + "!");
            TextView num_students_contacted = root.findViewById(R.id.num_students_contacted);
//            num_students_contacted.setText(notificationsViewModel.GetStudentsContacted().getValue().size());
            TextView num_questions_answered = root.findViewById(R.id.num_questions_answered);
            lv = root.findViewById(R.id.students);
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

        //Button viewMore = root.findViewById(R.id.button_view_more);

        mdbase = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/");
        dbref = mdbase.getReference();

        lv.setAdapter(aa);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                // need to recreate the mItems list somehow
                // another way is to use a FirebaseRecyclerView - see Sample Database code

                users.clear();
                Iterable<DataSnapshot> clients = snapshot.child("contacts").getChildren();
                for (DataSnapshot pair : clients) {
                    String pairString = pair.getValue(String.class);
                    String[] s = pairString.split("-");
                    if (homeViewModel.isStudent && homeViewModel.getID().equals(s[0])) {
                        DataSnapshot ds = snapshot.child("tutors").child(s[1]);
                        System.out.println( "Student contacted this tutor: " + s[1] + ds.child("firstName").getValue().toString() );
                        Tutor tutor = new Tutor(ds.child("firstName").getValue().toString(), ds.child("lastName").getValue().toString(), ds.child("email").getValue().toString(),  (ArrayList<String>) ds.child("subjects").getValue(), (ArrayList<ArrayList<Boolean>>) ds.child("avail").getValue() , (String) ds.child("bio").getValue().toString(), (String) ds.child("id").getValue().toString(), ds.child("grade").getValue().toString());

                        users.add(tutor);
                    } else if (!homeViewModel.isStudent && homeViewModel.getID().equals(s[1])) {
                        DataSnapshot ds = snapshot.child("students").child(s[0]);
                        if (ds.child("firstName").getValue() != null) {
                            System.out.println(s[0]);
                            System.out.println(ds.child("firstName").getValue().toString());
                            Student student = new Student(ds.child("firstName").getValue().toString(), ds.child("lastName").getValue().toString(), ds.child("email").getValue().toString(), ds.child("grade").getValue().toString(), (ArrayList<String>) ds.child("subjects").getValue(), (String) ds.child("id").getValue().toString());
                            users.add(student);



                            numContacts++;
                        }
                    }


                }

                aa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        if (homeViewModel.isStudent) {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Tutor t = (Tutor) users.get(position);



                    AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                    builder.setTitle("Contact");
                    builder.setMessage("Email this tutor to set up meeting?");

                    // add the buttons


                    builder.setPositiveButton("View Profile", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(mainActivity, ViewTutor.class);
                            intent.putExtra("id", t.getId());
                            startActivity(intent);

                        }
                    } );

                    builder.setNegativeButton("Cancel", null);

                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }
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


    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of HomeFragment");
        super.onResume();
        System.out.println("is Student?" + homeViewModel.isStudent);
        if (!homeViewModel.isStudent) {
            aa.notifyDataSetChanged();
            TextView answered = root.findViewById(R.id.num_questions_answered);
            TextView contact = root.findViewById(R.id.num_students_contacted);

            System.out.println("So print here?" + users.isEmpty());
            DatabaseReference dbref2  = FirebaseDatabase.getInstance("https://hopportunities-bb518-default-rtdb.firebaseio.com/").getReference();
            dbref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("In data change?" + users.size());
                        contact.setText( String.valueOf(aa.getCount()));
                        int ans;
                        if (dataSnapshot.child("numAns").child(homeViewModel.getID()).getValue() == null) {
                            ans = 0;
                        } else {
                            ans = parseInt(dataSnapshot.child("numAns").child(homeViewModel.getID()).getValue().toString());
                        }
                        answered.setText(String.valueOf(ans));


                }
                @Override public void onCancelled(DatabaseError error) { }
            });

        }


    }


}
