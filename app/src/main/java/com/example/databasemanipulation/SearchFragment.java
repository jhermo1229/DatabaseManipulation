package com.example.databasemanipulation;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private View searchView;
    DatabaseHelper dbh;
    String courses[] = {"PROG 8480", "PROG 8470", "PROG 8460", "PROG 8450"};
    String value;
    private List<Student> mList = new ArrayList<>();
    private RecyclerView mRecyclerview;
    private StudListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchView = inflater.inflate(R.layout.fragment_search, container, false);


//        RadioGroup groupRadio=(RadioGroup)searchView.findViewById(R.id.searchRadioGroup);
        EditText txtIdSearch=(EditText)searchView.findViewById(R.id.txtIdSearch);
        RadioButton programCodeSearch=(RadioButton)searchView.findViewById(R.id.searchProgramCode);
        RadioButton idSearch=(RadioButton)searchView.findViewById(R.id.searchId);
//        LinearLayout l = searchView.findViewById(R.id.searchIdGroup);
        ListView lv = searchView.findViewById(R.id.listView2);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.listview, courses);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                value = adapter.getItem(i);
            }
        });

        View.OnClickListener searchRadioListener = new View.OnClickListener(){
            public void onClick(View v){
                txtIdSearch.setVisibility(View.VISIBLE);
                lv.setVisibility(View.INVISIBLE);
            }
        };

        View.OnClickListener programCodeRadioListener = new View.OnClickListener(){
            public void onClick(View v){
                txtIdSearch.setVisibility(View.INVISIBLE);
                lv.setVisibility(View.VISIBLE);
            }
        };

        idSearch.setOnClickListener(searchRadioListener);
        programCodeSearch.setOnClickListener(programCodeRadioListener);

    TextView output = searchView.findViewById(R.id.output);
        Button submitBtn = searchView.findViewById(R.id.submitButton);
        dbh = new DatabaseHelper(getActivity());
        submitBtn.setOnClickListener((gradeEntryView) -> {
//           Cursor cursor = dbh.viewDataId(txtIdSearch.getText().toString());
//            if(cursor == null){
//                Toast.makeText(getContext(), "No Records Found", Toast.LENGTH_SHORT).show();
//            }else{
//                cursor.moveToFirst();
//                Student stud = new Student();
//                stud.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
//                stud.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow("first_name")));
//                stud.setLastName(cursor.getString(cursor.getColumnIndexOrThrow("last_name")));
//                stud.setCourse(cursor.getString(cursor.getColumnIndexOrThrow("course")));
//                stud.setCredits(cursor.getString(cursor.getColumnIndexOrThrow("credits")));
//                stud.setMarks(cursor.getString(cursor.getColumnIndexOrThrow("marks")));
//                output.setText("Student ID: "+stud.getId().toString() + ", "
//                        + "First Name: " + stud.getFirstName());
//            }

            // Inflate the layout for this fragment
//            View v = inflater.inflate(R.layout.fragment_view, container, false);
            mRecyclerview = searchView.findViewById(R.id.recyclerView2);
            dbh = new DatabaseHelper(getActivity());
            Cursor cursor = dbh.viewData();

            if(cursor == null){
                Toast.makeText(getContext(), "No Records Found", Toast.LENGTH_SHORT).show();

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
        });

        return searchView;
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