package com.example.hopportunities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class StudentAdapter extends ArrayAdapter<Student> {
    int resource;
    public StudentAdapter(Context ctx, int res, List<Student> Tasks)
    {
        super(ctx, res, Tasks);
        resource = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        Student t = getItem(position);

        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView nameView = (TextView) itemView.findViewById(R.id.name_layout);
        TextView edu = (TextView) itemView.findViewById(R.id.education_layout);
        TextView bio = (TextView) itemView.findViewById(R.id.bio_layout);
        nameView.setText(t.getFirstName() + " " + t.getLastName());
        edu.setText(t.getGrade());
        bio.setText(t.getSubjects().toString());


        return itemView;
    }
}

