package com.example.architecturemvvmexample1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    private EditText etTitle, etDescription;
    private NumberPicker numPickerPriority;
    public static final String EXTRA_TITLE = "com.example.architecturemvvmexample1.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.architecturemvvmexample1.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.architecturemvvmexample1.EXTRA_PRIORITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etTitle = findViewById(R.id.etAddTitle);
        etDescription = findViewById(R.id.etAddDescription);
        numPickerPriority = findViewById(R.id.numPickerPriority);

        numPickerPriority.setMaxValue(10);
        numPickerPriority.setMinValue(1);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.btnSaveNote){
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote(){ //we should create a separate view model class for this activity only for inserting data into db
                             //we should'nt use the view model we created for our MainActivity as it retrieve all data from db, which is not required for this activity
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        Integer priority = numPickerPriority.getValue();
        if(TextUtils.isEmpty(title) && TextUtils.isEmpty(description)){
            Toast.makeText(this, "Please enter title and description", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE,title);
            data.putExtra(EXTRA_DESCRIPTION,description);
            data.putExtra(EXTRA_PRIORITY,priority);
            setResult(RESULT_OK,data);
            finish();
        }
    }
}
