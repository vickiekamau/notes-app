package com.example.administrator.notes.activity.editor;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**import com.haerulmuttaqin.mynotesapp.R;
 import com.haerulmuttaqin.mynotesapp.api.ApiClient;
 import com.haerulmuttaqin.mynotesapp.api.ApiInterface;
 import com.haerulmuttaqin.mynotesapp.model.Note;
 import com.thebluealliance.spectrum.SpectrumPalette;*/


import com.example.administrator.notes.R;
import com.example.administrator.notes.api.ApiClient;
import com.example.administrator.notes.api.ApiInterface;
import com.example.administrator.notes.model.Note;
import com.thebluealliance.spectrum.SpectrumPalette;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity implements EditorView{

    EditText et_title, et_note;
    ProgressDialog progressDialog;
    SpectrumPalette palette;

    EditorPresenter presenter;
    ApiInterface apiinterface;

    int color, id;
    //String title, note;

    //Menu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        et_title = findViewById(R.id.title);
        et_note = findViewById(R.id.note);
        palette = findViewById(R.id.palette);

        palette.setOnColorSelectedListener(clr -> color = clr);
        palette.setSelectedColor(getResources().getColor(R.color.white));
        color = getResources().getColor(R.color.white);

//      Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        presenter = new EditorPresenter(this);


        /** Intent intent = getIntent();
         id = intent.getIntExtra("id", 0);
         title = intent.getStringExtra("title");
         note = intent.getStringExtra("note");
         color = intent.getIntExtra("color", 0);*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);



        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                //Save
                String title = et_title.getText().toString().trim();
                String note = et_note.getText().toString().trim();
                int color = this.color;

                if(title.isEmpty()){
                    et_title.setError("Please Enter a Title");
                }else
                if(note.isEmpty()){
                    et_note.setError("Please Enter a Note");
                }else {
                    presenter.saveNote(title,note,color);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
     progressDialog.hide();
    }

    @Override
    public void onAddSuccess(String message) {
     Toast.makeText(EditorActivity.this,
             message,
             Toast.LENGTH_SHORT).show();
              finish();
    }

    @Override
    public void onAddError(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        finish();
    }
}