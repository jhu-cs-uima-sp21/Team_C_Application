package com.example.hopportunities.ui.home;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.hopportunities.CreateAccount;
import com.example.hopportunities.MainActivity;
import com.example.hopportunities.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private FirebaseAuth fAuth;
    public boolean isStudent = false;
    public String firstName;
    public List<String> subjects;
    public HomeViewModel() {
        System.out.println("view model");
        fAuth = FirebaseAuth.getInstance();
        String id = fAuth.getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("students");
        ref.addValueEventListener(new MyValueEventListener(id, true, this));

        if (!isStudent) {
            ref = FirebaseDatabase.getInstance().getReference().child("tutors");
            ref.addValueEventListener(new MyValueEventListener(id, false, this));
        }

        //  TextView hello_actual_name = (TextView) findViewById(R.id.hello_name);
        // hello_actual_name.setText("Hello, ");
        //mText = new MutableLiveData<>();
        //mText.setValue("This is notifications fragment");
    }

    public LiveData<List<Student>> GetStudentsContacted() {
        return new MutableLiveData<List<Student>>();
    }

    public class MyValueEventListener implements ValueEventListener {
        private String id;
        private boolean isStudent;
        private HomeViewModel viewModel;

        public MyValueEventListener(String id, boolean isStudent, HomeViewModel viewModel) {
            this.id = id;
            this.isStudent = isStudent;
            this.viewModel = viewModel;
        }

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot datas: dataSnapshot.getChildren()) {
                if (datas.getKey().equals(id)) {
                    viewModel.isStudent = isStudent;
                    viewModel.firstName = datas.child("firstName").getValue().toString();
                    viewModel.subjects = (List<String>) datas.child("subjects").getValue();
                    break;
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

}