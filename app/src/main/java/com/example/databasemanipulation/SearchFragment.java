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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchFragment - this is for searching the data either by ID or program code
 */
public class SearchFragment extends Fragment {

    public static final String RADIOVALUE_ID = "Id";
    public static final String ID = "id";
    public static final String NO_RECORDS_FOUND = "No Records Found";
    public static final String ENTER_A_CORRECT_VALUE = "ENTER A CORRECT VALUE";
    public static final String RADIOVALUE_PROGRAMCODE = "Program Code";
    public static final String PROGRAM_CODE = "programCode";
    public static final String CHOOSE_FROM_THE_LIST = "CHOOSE FROM THE LIST";
    public static final String COL_ID = "id";
    public static final String COL_FIRSTNAME = "first_name";
    public static final String COL_LASTNAME = "last_name";
    public static final String COL_COURSE = "course";
    public static final String COL_CREDITS = "credits";
    public static final String COL_MARKS = "marks";
    private View searchView;
    private DatabaseHelper dbh;
    private String courses[];
    private String listViewValue;
    private List<Student> mList = new ArrayList<>();
    private RecyclerView mRecyclerview;
    private StudListAdapter mAdapter;
    private Button submitBtn;
    private RadioGroup radioGroup;
    private String radioValue;
    private Cursor cursor;
    private TextView headerFirstName;
    private TextView headerLastName;
    private TextView headerCourse;
    private TextView headerMark;
    private TextView headerCredit;
    private TextView headerId;
    private TextView output;
    private EditText txtIdSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchView = inflater.inflate(R.layout.fragment_search, container, false);

        //Initialization of screen parts
        txtIdSearch = searchView.findViewById(R.id.txtIdSearch);
        radioGroup = searchView.findViewById(R.id.searchRadioGroup);
        RadioButton programCodeSearch = searchView.findViewById(R.id.searchProgramCode);
        RadioButton idSearch = searchView.findViewById(R.id.searchId);
        ListView programCodeListView = searchView.findViewById(R.id.searchProgramCodeListV);
        submitBtn = searchView.findViewById(R.id.submitButton);
        headerFirstName = searchView.findViewById(R.id.header_firstName);
        headerLastName = searchView.findViewById(R.id.header_lastName);
        headerCredit = searchView.findViewById(R.id.header_credit);
        headerMark = searchView.findViewById(R.id.header_mark);
        headerCourse = searchView.findViewById(R.id.header_course);
        headerId = searchView.findViewById(R.id.header_id);
        mRecyclerview = searchView.findViewById(R.id.searchProgramCodeRecycler);
        output = searchView.findViewById(R.id.output);
        dbh = new DatabaseHelper(getActivity());

        //Hide objects upon loading
        txtIdSearch.setVisibility(View.INVISIBLE);
        programCodeListView.setVisibility(View.INVISIBLE);
        submitBtn.setVisibility(View.INVISIBLE);
        setInvisibleOnHeader();

        //Getting the string array in String.xml
        courses = getResources().getStringArray(R.array.courseList);

        //Initialize adapter for list view
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.listview, courses);
        programCodeListView.setAdapter(adapter);

        //On click on the listview, it will get the value and pass on the string
        programCodeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listViewValue = adapter.getItem(i);
            }
        });

        //Radiobutton onClick for id search.
        //It will show and hide objects
        View.OnClickListener searchRadioListener = new View.OnClickListener() {
            public void onClick(View v) {
                txtIdSearch.setVisibility(View.VISIBLE);
                programCodeListView.setVisibility(View.INVISIBLE);
                submitBtn.setVisibility(View.VISIBLE);
                setInvisibleOnHeader();

                //Set the recyclerview to clear when clicking radiobutton
                mList = new ArrayList<>();
                bindAdapter();
            }
        };

        //Radiobutton onClick for program code search.
        //It will show and hide objects
        View.OnClickListener programCodeRadioListener = new View.OnClickListener() {
            public void onClick(View v) {
                txtIdSearch.setVisibility(View.INVISIBLE);
                programCodeListView.setVisibility(View.VISIBLE);
                submitBtn.setVisibility(View.VISIBLE);
                setInvisibleOnHeader();

                //Set the recyclerview to clear when clicking radiobutton
                mList = new ArrayList<>();
                bindAdapter();
            }
        };

        //Setting the listener for each radio button
        idSearch.setOnClickListener(searchRadioListener);
        programCodeSearch.setOnClickListener(programCodeRadioListener);

        //On click of submit button process
        submitButtonOnClick();

        return searchView;
    }

    //Logic for clicking the submit button
    private void submitButtonOnClick() {
        submitBtn.setOnClickListener((gradeEntryView) -> {

            //Upon click will get the value of radio button chosen
            getRadioButtonValue();

            //Making sure the list is empty on every search so that data will not just add up
            if (!mList.isEmpty()) {
                mList = new ArrayList<>();
            }

            //Radiobutton clicked is ID
            if (radioValue.equalsIgnoreCase(RADIOVALUE_ID)) {

                //Get value inputted in the edit text
                String inputId = txtIdSearch.getText().toString();

                //Check first if value is empty, null, or not numeric. If not toast an error message
                if (!(inputId.isEmpty() || inputId == null)
                        && isNumeric(inputId)) {

                    //Get value based on input
                    cursor = dbh.viewData(ID, txtIdSearch.getText().toString());

                    //if no records found, toast a no record found message
                    if (cursor == null) {
                        Toast.makeText(getContext(), NO_RECORDS_FOUND, Toast.LENGTH_SHORT).show();
                    } else {
                        //main logic for getting, assigning, outputting data
                        assignDataAndAddToList();
                    }
                } else {
                    Toast.makeText(getContext(), ENTER_A_CORRECT_VALUE, Toast.LENGTH_SHORT).show();
                }

                //Radiobutton clicked is PROGRAM CODE
            } else if (radioValue.equalsIgnoreCase(RADIOVALUE_PROGRAMCODE)) {

                //Check first if any of the item in the listview is chosen. If not will toast an error message
                if (!(listViewValue.isEmpty() || listViewValue == null)) {
                    //getting the data depending on the chosen program code
                    cursor = dbh.viewData(PROGRAM_CODE, listViewValue);

                    if (cursor == null) {
                        Toast.makeText(getContext(), NO_RECORDS_FOUND, Toast.LENGTH_SHORT).show();

                    } else {
                        //main logic for getting, assigning, outputting data
                        assignDataAndAddToList();
                    }

                } else {
                    Toast.makeText(getContext(), CHOOSE_FROM_THE_LIST, Toast.LENGTH_SHORT).show();
                }
            }
            //Close the database after use
            dbh.close();
        });
    }

    //This method assign the data from cursor to the student object then added on the list.
    //This will be outputted in the recycler view
    private void assignDataAndAddToList() {

        //Always move to first the cursor
        if (cursor.moveToFirst()) {
            do {

                //Assigning the value from cursor to the student object
                Student stud = new Student();
                stud.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                stud.setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRSTNAME)));
                stud.setLastName(cursor.getString(cursor.getColumnIndexOrThrow(COL_LASTNAME)));
                stud.setCourse(cursor.getString(cursor.getColumnIndexOrThrow(COL_COURSE)));
                stud.setCredits(cursor.getString(cursor.getColumnIndexOrThrow(COL_CREDITS)));
                stud.setMarks(cursor.getString(cursor.getColumnIndexOrThrow(COL_MARKS)));

                //Adding to list for the recycler view
                mList.add(stud);
            } while (cursor.moveToNext());
        }
        //Header for the resulting data will be enabled
        setVisibleOnHeader();

        //Closing the cursor used
        cursor.close();

        //Calling the adapter for the recyclerview to bind the data in the object
        bindAdapter();
    }


    //Method for making the header of the output invisible
    private void setInvisibleOnHeader() {
        headerFirstName.setVisibility(View.INVISIBLE);
        headerLastName.setVisibility(View.INVISIBLE);
        headerCourse.setVisibility(View.INVISIBLE);
        headerMark.setVisibility(View.INVISIBLE);
        headerCredit.setVisibility(View.INVISIBLE);
        headerId.setVisibility(View.INVISIBLE);
    }

    //Method for making the header of the output visible
    private void setVisibleOnHeader() {
        headerFirstName.setVisibility(View.VISIBLE);
        headerLastName.setVisibility(View.VISIBLE);
        headerCourse.setVisibility(View.VISIBLE);
        headerMark.setVisibility(View.VISIBLE);
        headerCredit.setVisibility(View.VISIBLE);
        headerId.setVisibility(View.VISIBLE);
    }

    //Binding the data in the list to the recyclerview by using adapter
    private void bindAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(layoutManager);
        mAdapter = new StudListAdapter(mList, getContext());
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    //Method for getting the value of the radiobutton chosen
    private void getRadioButtonValue() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            //get radio button view
            View radioButton = radioGroup.findViewById(selectedId);
            // return index of selected radiobutton
            int radioId = radioGroup.indexOfChild(radioButton);
            // based on index getObject of radioButton
            RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
            //After getting radiobutton you can now use all its methods
            radioValue = (String) btn.getText();
        }
    }


    //Checking if input of user is numeric
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}