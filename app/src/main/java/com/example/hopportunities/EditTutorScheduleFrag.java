package com.example.hopportunities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import java.util.ArrayList;


public class EditTutorScheduleFrag extends Fragment {

    private EditAccount editAccount;
    private ArrayList<ArrayList<Boolean>> avail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_tutor_schedule, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("Content Fragment", "Edit Tutor Schedule onStart");
        editAccount = (EditAccount) getActivity();
        avail = new ArrayList<>();
        avail.add(new ArrayList<>()); //one list for each day, each has one bool for morning, evening, night
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());
        avail.add(new ArrayList<>());

        ToggleButton mMorn = editAccount.findViewById(R.id.mMorn);
        ToggleButton mAfternoon = editAccount.findViewById(R.id.mAfternoon);
        ToggleButton mEvening = editAccount.findViewById(R.id.mEvening);

        ToggleButton tuMorn = editAccount.findViewById(R.id.tuMorn);
        ToggleButton tuAfternoon = editAccount.findViewById(R.id.tuAfternoon);
        ToggleButton tuEvening = editAccount.findViewById(R.id.tuEvening);

        ToggleButton wMorn = editAccount.findViewById(R.id.wMorn);
        ToggleButton wAfternoon = editAccount.findViewById(R.id.wAfternoon);
        ToggleButton wEvening = editAccount.findViewById(R.id.wEvening);

        ToggleButton thMorn = editAccount.findViewById(R.id.thMorn);
        ToggleButton thAfternoon = editAccount.findViewById(R.id.thAfternoon);
        ToggleButton thEvening = editAccount.findViewById(R.id.thEvening);

        ToggleButton fMorn = editAccount.findViewById(R.id.fMorn);
        ToggleButton fAfternoon = editAccount.findViewById(R.id.fAfternoon);
        ToggleButton fEvening = editAccount.findViewById(R.id.fEvening);

        ToggleButton satMorn = editAccount.findViewById(R.id.satMorn);
        ToggleButton satAfternoon = editAccount.findViewById(R.id.satAfternoon);
        ToggleButton satEvening = editAccount.findViewById(R.id.satEvening);

        ToggleButton suMorn = editAccount.findViewById(R.id.suMorn);
        ToggleButton suAfternoon = editAccount.findViewById(R.id.suAfternoon);
        ToggleButton suEvening = editAccount.findViewById(R.id.suEvening);

        ArrayList<ArrayList<Boolean>> oldAvail = editAccount.tutorAcc.getAvail();

        if (oldAvail.get(0).get(0)) {
            mMorn.setChecked(true);
        }
        if (oldAvail.get(0).get(1)) {
            mAfternoon.setChecked(true);
        }
        if (oldAvail.get(0).get(2)) {
            mEvening.setChecked(true);
        }

        if (oldAvail.get(1).get(0)) {
            tuMorn.setChecked(true);
        }
        if (oldAvail.get(1).get(1)) {
            tuAfternoon.setChecked(true);
        }
        if (oldAvail.get(1).get(2)) {
            tuEvening.setChecked(true);
        }

        if (oldAvail.get(2).get(0)) {
            wMorn.setChecked(true);
        }
        if (oldAvail.get(2).get(1)) {
            wAfternoon.setChecked(true);
        }
        if (oldAvail.get(2).get(2)) {
            wEvening.setChecked(true);
        }

        if (oldAvail.get(3).get(0)) {
            thMorn.setChecked(true);
        }
        if (oldAvail.get(3).get(1)) {
            thAfternoon.setChecked(true);
        }
        if (oldAvail.get(3).get(2)) {
            thEvening.setChecked(true);
        }

        if (oldAvail.get(4).get(0)) {
            fMorn.setChecked(true);
        }
        if (oldAvail.get(4).get(1)) {
            fAfternoon.setChecked(true);
        }
        if (oldAvail.get(4).get(2)) {
            fEvening.setChecked(true);
        }

        if (oldAvail.get(5).get(0)) {
            satMorn.setChecked(true);
        }
        if (oldAvail.get(5).get(1)) {
            satAfternoon.setChecked(true);
        }
        if (oldAvail.get(5).get(2)) {
            satEvening.setChecked(true);
        }

        if (oldAvail.get(6).get(0)) {
            suMorn.setChecked(true);
        }
        if (oldAvail.get(6).get(1)) {
            suAfternoon.setChecked(true);
        }
        if (oldAvail.get(6).get(2)) {
            suEvening.setChecked(true);
        }

        Button next = editAccount.findViewById(R.id.schedNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                editAccount.avail = avail;
                FragmentTransaction ft = editAccount.fm.beginTransaction();
                EditConfirmFrag conf = new EditConfirmFrag();
                ft.replace(R.id.fragment_container, conf).addToBackStack(null).commit();
                return;
            }
        });
    }
}