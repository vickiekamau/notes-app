package com.example.administrator.notes.activity.main;

import android.support.annotation.NonNull;

import com.example.administrator.notes.api.ApiClient;
import com.example.administrator.notes.api.ApiInterface;
import com.example.administrator.notes.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

    private MainView mainView;
    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }
    void getData(){
        mainView.showLoading();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Note>> call = apiInterface.getNotes();
        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(@NonNull Call<List<Note>> call, @NonNull Response<List<Note>> response) {
                mainView.hideLoading();
                if(response.isSuccessful() && response.body() != null){
                    mainView.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Note>> call, @NonNull Throwable t) {
                mainView.hideLoading();
                mainView.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }

}
