package com.priyanshparekh.everythingpython;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CompilerActivity extends AppCompatActivity {

    EditText codeArea;
    FloatingActionButton runBtn;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compiler);

        codeArea = findViewById(R.id.code_area);
        runBtn = findViewById(R.id.run_btn);
        output = findViewById(R.id.output_tv);

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        // now on clicking fab it will extract code form edit text and send it to python script
        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creating python instance
                Python py = Python.getInstance();

                // creating python object
                PyObject pyObject = py.getModule("myScript");

                // now call the function in python file... // pass data here
                PyObject pyObj = pyObject.callAttr("main", codeArea.getText().toString());

                // now set returned text to textview
                output.setText(pyObj.toString());
            }
        });

        Intent intent = getIntent();
        String program = intent.getStringExtra("PROGRAM");
        try {
            codeArea.setText(Html.fromHtml(program, Html.FROM_HTML_MODE_LEGACY));
        }
        catch (Exception e) {
            codeArea.setText(null);
        }
    }
}