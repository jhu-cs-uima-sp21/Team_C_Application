package com.example.hopportunities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SignUpFrag1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SignUp signup = (SignUp) getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_1, container, false);
    }
    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d ("Content Fragment", "onStart");
        // Apply any required UI change now that the Fragment is visible.
    }

}
