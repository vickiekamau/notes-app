package com.example.administrator.notes.activity.editor;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.administrator.notes.api.ApiClient;
import com.example.administrator.notes.api.ApiInterface;
import com.example.administrator.notes.model.Note;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPresenter {
    private EditorView editorView;
    public EditorPresenter(EditorView editorView) {
        this.editorView = editorView;
    }
    void saveNote(final String title,final String note, final int color){
        editorView.showProgress();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Note> call = apiInterface.saveNote(title, note, color);

        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(@NonNull Call<Note> call, @NonNull Response<Note> response) {
                editorView.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if(success){
                        editorView.onAddSuccess(response.body().getMessage());
                       // Toast.makeText(EditorActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                       // finish();//back to main activity
                    } else{
                        editorView.onAddError(response.body().getMessage());
                        //Toast.makeText(EditorActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        // if the error still exists
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Note> call, @NonNull Throwable t) {
                editorView.hideProgress();
                editorView.onAddError(t.getLocalizedMessage());
                //Toast.makeText(EditorActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

    }


}
