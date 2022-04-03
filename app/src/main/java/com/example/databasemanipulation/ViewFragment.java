package com.example.databasemanipulation;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewFragment#} factory method to
 * create an instance of this fragment.
 */
public class ViewFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private List<Student> mList = new ArrayList<>();
    private StudListAdapter mAdapter;
    DatabaseHelper dbh;

    public ViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view, container, false);
        mRecyclerview = v.findViewById(R.id.recyclerView);
        dbh = new DatabaseHelper(getActivity());
        Cursor cursor = dbh.viewData();

        if(cursor == null){
            Toast.makeText(getContext(), "No Records Found", Toast.LENGTH_SHORT).show();
            return v;
        }else{
            if(cursor.moveToFirst()){
                do{
                    Student stud = new Student();
                    stud.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    System.out.println(">>>>>>>>>>>>>>>"+ stud.getId());
                    stud.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow("first_name")));
                    stud.setLastName(cursor.getString(cursor.getColumnIndexOrThrow("last_name")));
                    stud.setCourse(cursor.getString(cursor.getColumnIndexOrThrow("course")));
                    stud.setCredits(cursor.getString(cursor.getColumnIndexOrThrow("credits")));
                    stud.setMarks(cursor.getString(cursor.getColumnIndexOrThrow("marks")));
                    mList.add(stud);
                }while(cursor.moveToNext());
            }
        }

        cursor.close();
        dbh.close();
        bindAdapter();

        return v;
    }

    private void bindAdapter(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(layoutManager);
        System.out.println("BEFORE ADAPTER CALL");
        mAdapter = new StudListAdapter(mList, getContext());
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}