package com.example.databasemanipulation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

/**
 * Grade Entry Fragment - Contains the input of grades for students
 */
public class GradeEntryFragment extends Fragment {

    public static final String RECORD_ADDED_SUCCESSFULLY = "Record Added Successfully";
    public static final String UNSUCCESSFUL_IN_RECORD_ADDING_PLEASE_CHECK_LOGS = "Unsuccessful in record adding. Please check logs";
    public static final String PLEASE_DON_T_LEAVE_ANYTHING_BLANK_OR_PUT_NON_NUMERIC_ON_MARKS = "Please don't leave anything blank or put non-numeric on Marks";
    //Object initialization global
    private View gradeEntryView;
    private ListView listView;
    private EditText firstName;
    private EditText lastName;
    private EditText marks;
    private RadioGroup radioGroup;
    private TextView textView;
    private String radioValue;
    private String listViewValue;
    private DatabaseHelper dbh;
    private Boolean insertStat;
    private Button submitBtn;
    private String courses[];

    public GradeEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        gradeEntryView = (View) inflater.inflate(R.layout.fragment_grade_entry, container, false);

        //getting the values from XML counterpart
        firstName = gradeEntryView.findViewById(R.id.firstName);
        lastName = gradeEntryView.findViewById(R.id.lastName);
        marks = gradeEntryView.findViewById(R.id.marks);
        radioGroup = gradeEntryView.findViewById(R.id.creditRadioGroup);

        //Initializing listview
        listView = gradeEntryView.findViewById(R.id.searchProgramCodeListV);
        //getting the textview being used by listview
        textView = gradeEntryView.findViewById(R.id.listViewTextView);

        //Getting the string array in String.xml
        courses = getResources().getStringArray(R.array.courseList);

        //Setting the adapter needed by listview
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.listview, courses);
        listView.setAdapter(adapter);
        listViewOnClick(adapter);

        //Initializing the active database
        dbh = new DatabaseHelper(getActivity());
        submitBtn = gradeEntryView.findViewById(R.id.submitButton);
        submitBtnOnClick();

        return gradeEntryView;
    }


    //Gets the value of the listview chosen
    private void listViewOnClick(ArrayAdapter<String> adapter) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listViewValue = adapter.getItem(i);
            }
        });
    }


    //Trigger process upon clicking the submit button
    private void submitBtnOnClick() {
        submitBtn.setOnClickListener((gradeEntryView) -> {
            String firstNameValue = firstName.getText().toString();
            String lastNameValue = lastName.getText().toString();
            String marksValue = marks.getText().toString();

            //Check first if values are empty, null, or not numeric for marks
            if (isNumeric(marksValue) && !(firstNameValue.isEmpty() || lastNameValue.isEmpty()
                    || marksValue.isEmpty() || listViewValue == null)) {

                //gets the radiobutton chosen
                getRadioButtonValue();

                //Sets the autoincrement id to 0
                Integer studId = 0;

                //Setting the student object with all the values chosen by user
                Student stud = new Student(studId, firstNameValue, lastNameValue,
                        marksValue, listViewValue, radioValue);

                //passing the object to the database handler
                insertStat = dbh.InsertStudent(stud);

                //if values was added successfully will toast a success message. If not, will toast a unsuccessful message
                if (insertStat) {
                    Toast.makeText(getActivity(), RECORD_ADDED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), UNSUCCESSFUL_IN_RECORD_ADDING_PLEASE_CHECK_LOGS, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), PLEASE_DON_T_LEAVE_ANYTHING_BLANK_OR_PUT_NON_NUMERIC_ON_MARKS, Toast.LENGTH_SHORT).show();
            }
        });

    }


    //Setting the value of the radiobutton to global string
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