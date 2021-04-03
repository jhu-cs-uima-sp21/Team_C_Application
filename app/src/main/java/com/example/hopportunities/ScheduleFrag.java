package com.example.hopportunities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFrag extends Fragment {
    private CreateAccount createAccount;
    private ArrayList<ArrayList<Boolean>> avail;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_schedule_sign_up, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Content Fragment", "onStart");
        createAccount = (CreateAccount) getActivity();
        avail = new ArrayList<>();
        avail.add(new ArrayList<>()); //one list for each day, each has one bool for morning, evening, night
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());


        Button next = createAccount.findViewById(R.id.schedNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton mMorn = createAccount.findViewById(R.id.mMorn);
                ToggleButton mAfternoon = createAccount.findViewById(R.id.mAfternoon);
                ToggleButton mEvening = createAccount.findViewById(R.id.mEvening);

                ToggleButton tuMorn = createAccount.findViewById(R.id.tuMorn);
                ToggleButton tuAfternoon = createAccount.findViewById(R.id.tuAfternoon);
                ToggleButton tuEvening = createAccount.findViewById(R.id.tuEvening);

                ToggleButton wMorn = createAccount.findViewById(R.id.wMorn);
                ToggleButton wAfternoon = createAccount.findViewById(R.id.wAfternoon);
                ToggleButton wEvening = createAccount.findViewById(R.id.wEvening);

                ToggleButton thMorn = createAccount.findViewById(R.id.thMorn);
                ToggleButton thAfternoon = createAccount.findViewById(R.id.thAfternoon);
                ToggleButton thEvening = createAccount.findViewById(R.id.thEvening);

                ToggleButton fMorn = createAccount.findViewById(R.id.fMorn);
                ToggleButton fAfternoon = createAccount.findViewById(R.id.fAfternoon);
                ToggleButton fEvening = createAccount.findViewById(R.id.fEvening);

                ToggleButton satMorn = createAccount.findViewById(R.id.satMorn);
                ToggleButton satAfternoon = createAccount.findViewById(R.id.satAfternoon);
                ToggleButton satEvening = createAccount.findViewById(R.id.satEvening);

                ToggleButton suMorn = createAccount.findViewById(R.id.suMorn);
                ToggleButton suAfternoon = createAccount.findViewById(R.id.suAfternoon);
                ToggleButton suEvening = createAccount.findViewById(R.id.suEvening);

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
                createAccount.avail = avail;

                FragmentTransaction ft = createAccount.fm.beginTransaction();
                ConfirmFragment confirmFragment = new ConfirmFragment();
                ft.replace(R.id.fragment_container, confirmFragment).addToBackStack(null).commit();
                return;
            }
        });


    }
}

