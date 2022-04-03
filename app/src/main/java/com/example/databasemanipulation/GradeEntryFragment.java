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

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GradeEntryFragment#} factory method to
 * create an instance of this fragment.
 */
public class GradeEntryFragment extends Fragment {

    private View gradeEntryView;
    private ListView listView;
    private EditText firstName;
    private EditText lastName;
    private EditText marks;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView textView;
    private String radioValue;

    private String value;
    DatabaseHelper dbh;
    Boolean insertStat;

    private Button submitBtn;

    String courses[] = {"PROG 8480", "PROG 8470", "PROG 8460", "PROG 8450"};

    public GradeEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        gradeEntryView = (View) inflater.inflate(R.layout.grade_entry, container, false);
        firstName = gradeEntryView.findViewById(R.id.firstName);
        lastName = gradeEntryView.findViewById(R.id.lastName);
        marks = gradeEntryView.findViewById(R.id.marks);
        radioGroup = gradeEntryView.findViewById(R.id.radioGroup2);


//       radioButton.setOnClickListener((View.OnClickListener) gradeEntryView);
        listView = gradeEntryView.findViewById(R.id.listView);
        textView = gradeEntryView.findViewById(R.id.textView2);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
               R.layout.listview, courses);
        listView.setAdapter(adapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               value = adapter.getItem(i);
           }
       });

        submitBtn = gradeEntryView.findViewById(R.id.submitButton);



        dbh = new DatabaseHelper(getActivity());
        submitBtn.setOnClickListener((gradeEntryView) -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            System.out.println("MOJA " + selectedId);
            if(selectedId !=-1){
                //get radio button view
                View radioButton = radioGroup.findViewById(selectedId);
                // return index of selected radiobutton
                int radioId = radioGroup.indexOfChild(radioButton);
                // based on index getObject of radioButton
                RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                //After getting radiobutton you can now use all its methods
                radioValue = (String) btn.getText();
            }
            Integer studId = 0;
            Student stud = new Student(studId, firstName.getText().toString(), lastName.getText().toString(),
                    marks.getText().toString(), value, radioValue );

            insertStat = dbh.InsertStudent(stud);
            if(insertStat){
                Toast.makeText(getActivity(), "Record Added Successfully" , Toast.LENGTH_SHORT).show();
            }
        });

        return gradeEntryView;
    }

    public void onRadioButtonClicked(View view) {


                radioValue = ((RadioButton) view).getText().toString();

        }

}