package com.example.tejas.networkingdemo;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by charoo on 01-Oct-17.
 */

public class PostAsyncTask extends AsyncTask<String,Void,ArrayList<Post>> {

    public PostDownloadListener postDownloadListener;

    public PostAsyncTask(PostDownloadListener listener) {
        postDownloadListener = listener;
    }


    @Override
    protected ArrayList<Post> doInBackground(String... strings) {
        String urlString = strings[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            Log.i("Post Response:","Connection Started");
            urlConnection.connect();
            Log.i("Post Response:","Connection Complete");
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String response = "";
            while (scanner.hasNext()){
                response += scanner.next();
            }
            Log.i("Post Response:",response);
            ArrayList<Post> posts = parsePost(response);
            return  posts;



        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Post> parsePost(String response) throws JSONException {
        ArrayList<Post> posts = null;
        JSONArray postArray = new JSONArray(response);

        if(postArray!=null){
            posts = new ArrayList<>();
            for(int i = 0;i<postArray.length();i++){
                JSONObject postObject = postArray.getJSONObject(i);
                int id = postObject.getInt("id");
                String title = postObject.getString("title");
                String body = postObject.getString("body");
//                String name = courseObject.getString("name");
//                String overview = courseObject.getString("overview");
                Post post = new Post(id,title,body);
                posts.add(post);
            }
        }


        return posts;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Post> posts) {

        Log.i("Posts Array Size",posts.size() +"");
        postDownloadListener.onDownload(posts);
    }


    public static interface PostDownloadListener{
        void onDownload(ArrayList<Post> posts);
    }


}
