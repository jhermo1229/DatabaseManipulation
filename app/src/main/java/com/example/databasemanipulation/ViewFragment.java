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
 * ViewFragment - this shows all the available record in the database
 */
public class ViewFragment extends Fragment {

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String COURSE = "course";
    public static final String CREDITS = "credits";
    public static final String MARKS = "marks";
    //This fragment uses a recycler view for scrolling
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
        View viewFragment = inflater.inflate(R.layout.fragment_view, container, false);

        //getting the recycler view
        mRecyclerview = viewFragment.findViewById(R.id.recyclerView);

        //initializing database
        dbh = new DatabaseHelper(getActivity());

        //Getting all the available data using getAll parameter
        Cursor cursor = dbh.viewData("getAll", "");
    System.out.println("CURSOR: " + cursor);
        //if no record was found, will toast a no records message.
        //Otherwise will set the values to object
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No Records Found", Toast.LENGTH_SHORT).show();
            return viewFragment;
        } else {
            //Setting the values in object then added to list
            if (cursor.moveToFirst()) {
                do {
                    Student stud = new Student();
                    stud.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                    stud.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(FIRST_NAME)));
                    stud.setLastName(cursor.getString(cursor.getColumnIndexOrThrow(LAST_NAME)));
                    stud.setCourse(cursor.getString(cursor.getColumnIndexOrThrow(COURSE)));
                    stud.setCredits(cursor.getString(cursor.getColumnIndexOrThrow(CREDITS)));
                    stud.setMarks(cursor.getString(cursor.getColumnIndexOrThrow(MARKS)));
                    mList.add(stud);
                } while (cursor.moveToNext());
            }
        }

        //Closing the cursor and database then binding to the adapter needed
        cursor.close();
        dbh.close();
        bindAdapter();

        return viewFragment;
    }


    //Binding the list to recyclerview by using the object adapter
    private void bindAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(layoutManager);
        mAdapter = new StudListAdapter(mList, getContext());
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}