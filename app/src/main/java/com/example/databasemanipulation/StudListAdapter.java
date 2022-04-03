package com.example.databasemanipulation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Student> mList;
    public StudListAdapter(List<Student> list, Context context) {
        super();
        System.out.println("<><><><>" + list.get(0).getId());
        mList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextId;
        public TextView mTextFirstName;
        public TextView mTextLastName;
        public TextView mTextCourse;
        public TextView mTextCredits;
        public TextView mTextMarks;

        public ViewHolder(View v){
            super(v);
            mTextId = v.findViewById(R.id.txtId);
            mTextFirstName=v.findViewById(R.id.firstName);
            mTextLastName = v.findViewById(R.id.lastName);
            mTextCourse = v.findViewById(R.id.course);
            mTextCredits = v.findViewById(R.id.credits);
            mTextMarks = v.findViewById(R.id.marks);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Student studAdapter = mList.get(position);
        System.out.println("<<<<<<<<<<<<<<<<" + studAdapter.getId());
        ((ViewHolder) holder).mTextId.setText(Integer.toString(studAdapter.getId()));
        ((ViewHolder) holder).mTextFirstName.setText(studAdapter.getFirstName());
        ((ViewHolder) holder).mTextLastName.setText(studAdapter.getLastName());
        ((ViewHolder) holder).mTextCourse.setText(studAdapter.getCourse());
        ((ViewHolder) holder).mTextCredits.setText(studAdapter.getCredits());
        ((ViewHolder) holder).mTextMarks.setText(studAdapter.getMarks());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
