package com.example.administrator.notes.activity.main;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import com.example.administrator.notes.R;
import com.example.administrator.notes.activity.editor.EditorActivity;
import com.example.administrator.notes.model.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
   private FloatingActionButton fab;
   RecyclerView recyclerView;
   SwipeRefreshLayout swipeRefreshLayout;

   MainPresenter presenter;
   MainAdapter adapter;
   MainAdapter.ItemClickListener itemClickListener;

   List<Note> note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EditorActivity.class));
            }
        });
        presenter = new MainPresenter(this);
        presenter.getData();

        swipeRefreshLayout.setOnRefreshListener(
                () -> presenter.getData()
        );
        itemClickListener = ((view,position) ->{
            String title = note.get(position).getTitle();
            Toast.makeText(this,title,Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
         swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Note> notes) {
         adapter = new MainAdapter(this,notes,itemClickListener);
         adapter.notifyDataSetChanged();
         recyclerView.setAdapter(adapter);

         note = notes;


    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
