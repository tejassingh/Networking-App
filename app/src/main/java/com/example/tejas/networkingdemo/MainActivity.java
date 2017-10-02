package com.example.tejas.networkingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostAsyncTask.PostDownloadListener {


    ListView listView;
//    ArrayAdapter arrayAdapter;
//    ArrayList<String> titles=new ArrayList<>();

    ArrayList<Post> titles = new ArrayList<>();
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchPost();
            }
        });


        listView= (ListView)findViewById(R.id.listview);
//        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles);
        customAdapter = new CustomAdapter(this,titles);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(MainActivity.this,CommentActivity.class);
                intent.putExtra("Post_id",i);
                startActivity(intent);
            }
        });

    }

    private void fetchPost() {
        String urlString ="https://jsonplaceholder.typicode.com/posts";
        PostAsyncTask asyncTask = new PostAsyncTask(this);
        asyncTask.execute(urlString);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDownload(ArrayList<Post> posts) {
        titles.clear();
        for(Post post:posts){

            titles.add(post);
        }

        customAdapter.notifyDataSetChanged();
    }
}
