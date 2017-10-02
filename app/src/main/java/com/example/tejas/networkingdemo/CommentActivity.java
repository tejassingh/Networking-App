package com.example.tejas.networkingdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity implements CommentAsyncTask.CommentDownloadListener {

    int pos;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ListView listView = (ListView)findViewById(R.id.commentlistview);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);



        Intent i = getIntent();
        if (i.hasExtra("Post_id")){
            pos = i.getIntExtra("Post_id",0);
        }


        String url = "https://jsonplaceholder.typicode.com/posts/"+pos+"/comments";
        CommentAsyncTask asyncTask = new CommentAsyncTask(this);
        asyncTask.execute(url);


    }

    @Override
    public void onCommentDownload(ArrayList<Comment> comments) {
        arrayList.clear();
        for(Comment comment:comments){
            arrayList.add(comment.getBody());
        }
        adapter.notifyDataSetChanged();
    }
}
