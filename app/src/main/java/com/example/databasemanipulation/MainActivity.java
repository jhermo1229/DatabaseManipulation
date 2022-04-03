package com.example.databasemanipulation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
//    private ListView listView;
//    private TextView textView;
//
//    String courses[] = {"PROG 8480", "PROG 8470", "PROG 8460", "PROG 8450"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationDrawer();

//        gradeEntryFragment();
    }

//    private void gradeEntryFragment() {
//        String radioBut ;
//        setContentView(R.layout.grade_entry);
//        listView = (ListView) findViewById(R.id.listView);
//        textView = (TextView) findViewById(R.id.textView);
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, courses);
//        listView.setAdapter(arrayAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this, "Selected: " + listView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void setNavigationDrawer(){
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment frag = null;
                int itemId = item.getItemId();
                if(itemId == R.id.gradeEntry){
                    frag = new GradeEntryFragment();
                }else if(itemId == R.id.viewGrades){
                    frag = new ViewFragment();
                } else if(itemId == R.id.search){
                    frag = new SearchFragment();
                }

                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                if(frag != null) {
                    //create fragment transaction
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    //replace the frame layout with new fragment
                    transaction.replace(R.id.frame, frag);
                    transaction.commit();
                    mDrawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
    }

    private boolean onOptionItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}