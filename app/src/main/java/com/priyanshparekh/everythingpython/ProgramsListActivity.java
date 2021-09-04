package com.priyanshparekh.everythingpython;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.priyanshparekh.everythingpython.databinding.ActivityProgramsListBinding;

import java.util.ArrayList;
import java.util.List;

public class ProgramsListActivity extends AppCompatActivity {

    private ActivityProgramsListBinding programsListBinding;

    public static RecyclerViewAdapter adapter;
    public static List<Program> data;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        programsListBinding = ActivityProgramsListBinding.inflate(getLayoutInflater());
        setContentView(programsListBinding.getRoot());

        db = new DBHelper(this);
        programsListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        data = new ArrayList<>();
        fetchData();

    }

    private void fetchData() {
        db = new DBHelper(this);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Program program = new Program();

//        data = db.getProgramList(this);
//        String programQues = program.getProgramQuestion();
//        int id = program.getId();
//        data.add(programQues);
//        data.add(id);

        List<Program> programList = db.getProgramList();
        for (Program program1 : programList) {
            data.add(program1);
        }
        adapter = new RecyclerViewAdapter(this, data);
        programsListBinding.recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}