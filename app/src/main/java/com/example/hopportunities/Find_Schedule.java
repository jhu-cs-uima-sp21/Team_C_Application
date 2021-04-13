package com.example.hopportunities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Find_Schedule extends AppCompatActivity {
    //private CreateAccount createAccount;
    private ArrayList<ArrayList<Boolean>> avail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule_sign_up);
    }

    /*
    private boolean[] flatten (ArrayList<ArrayList<Boolean>> arrayLists) {
        ArrayList<Boolean> tempBobj = new ArrayList<>();
       for (ArrayList<Boolean> a : arrayLists) {
           tempBobj.addAll(a);
       }
       ArrayList<boolean> temp = new ArrayList<>();
       for (Boolean b: tempBobj) {
           temp.add(b.booleanValue());
       }
        boolean [] bArray = temp.toArray(new boolean[temp.size()]);

       return bArray;
    }
*/
    @Override
    public void onStart() {
        super.onStart();
        Log.d("Content Fragment", "onStart");
        //createAccount = (CreateAccount) getActivity();
        avail = new ArrayList<>();
        avail.add(new ArrayList<>()); //one list for each day, each has one bool for morning, evening, night
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());


        Button next = findViewById(R.id.schedNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton mMorn = findViewById(R.id.mMorn);
                ToggleButton mAfternoon = findViewById(R.id.mAfternoon);
                ToggleButton mEvening = findViewById(R.id.mEvening);

                ToggleButton tuMorn = findViewById(R.id.tuMorn);
                ToggleButton tuAfternoon = findViewById(R.id.tuAfternoon);
                ToggleButton tuEvening = findViewById(R.id.tuEvening);

                ToggleButton wMorn = findViewById(R.id.wMorn);
                ToggleButton wAfternoon = findViewById(R.id.wAfternoon);
                ToggleButton wEvening = findViewById(R.id.wEvening);

                ToggleButton thMorn = findViewById(R.id.thMorn);
                ToggleButton thAfternoon = findViewById(R.id.thAfternoon);
                ToggleButton thEvening = findViewById(R.id.thEvening);

                ToggleButton fMorn = findViewById(R.id.fMorn);
                ToggleButton fAfternoon = findViewById(R.id.fAfternoon);
                ToggleButton fEvening = findViewById(R.id.fEvening);

                ToggleButton satMorn = findViewById(R.id.satMorn);
                ToggleButton satAfternoon = findViewById(R.id.satAfternoon);
                ToggleButton satEvening = findViewById(R.id.satEvening);

                ToggleButton suMorn = findViewById(R.id.suMorn);
                ToggleButton suAfternoon = findViewById(R.id.suAfternoon);
                ToggleButton suEvening = findViewById(R.id.suEvening);

                avail.get(0).add(mMorn.isChecked());
                avail.get(0).add(mAfternoon.isChecked());
                avail.get(0).add(mEvening.isChecked());

                avail.get(1).add(tuMorn.isChecked());
                avail.get(1).add(tuAfternoon.isChecked());
                avail.get(1).add(tuEvening.isChecked());

                avail.get(2).add(wMorn.isChecked());
                avail.get(2).add(wAfternoon.isChecked());
                avail.get(2).add(wEvening.isChecked());

                avail.get(3).add(thMorn.isChecked());
                avail.get(3).add(thAfternoon.isChecked());
                avail.get(3).add(thEvening.isChecked());

                avail.get(4).add(fMorn.isChecked());
                avail.get(4).add(fAfternoon.isChecked());
                avail.get(4).add(fEvening.isChecked());

                avail.get(5).add(satMorn.isChecked());
                avail.get(5).add(satAfternoon.isChecked());
                avail.get(5).add(satEvening.isChecked());

                avail.get(6).add(suMorn.isChecked());
                avail.get(6).add(suAfternoon.isChecked());
                avail.get(6).add(suEvening.isChecked());

                ArrayList<String> subs = getIntent().getStringArrayListExtra("subs");
                Intent intent = new Intent(getBaseContext(), FindTutorsActivity.class);

                intent.putExtra("avail", avail);
                intent.putExtra("subs", subs);
                startActivity(intent);



            }
        });


    }
}