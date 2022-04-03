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

        //gettinig the values from XML counterpart
        firstName = gradeEntryView.findViewById(R.id.firstName);
        lastName = gradeEntryView.findViewById(R.id.lastName);
        marks = gradeEntryView.findViewById(R.id.marks);
        radioGroup = gradeEntryView.findViewById(R.id.creditRadioGroup);

        //Initializing listview
        listView = gradeEntryView.findViewById(R.id.listView);
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

            //gets the radiobutton chosen
            getRadioButtonValue();

            //Sets the autoincrement id to 0
            Integer studId = 0;

            //Setting the student object with all the values chosen by user
            Student stud = new Student(studId, firstName.getText().toString(), lastName.getText().toString(),
                    marks.getText().toString(), listViewValue, radioValue);

            //passing the object to the database handler
            insertStat = dbh.InsertStudent(stud);

            //if values was added successfully will toast a success message. If not, will toast a unsuccessful message
            if (insertStat) {
                Toast.makeText(getActivity(), "Record Added Successfully", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Unsuccessful in record adding. Please check logs", Toast.LENGTH_SHORT).show();
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
}