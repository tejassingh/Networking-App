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
 * Created by charoo on 02-Oct-17.
 */

class CommentAsyncTask extends AsyncTask<String,Void,ArrayList<Comment>>{

    public CommentDownloadListener commentDownloadListener;

    public CommentAsyncTask(CommentDownloadListener listener) {
        commentDownloadListener = listener;
    }


    @Override
    protected ArrayList<Comment> doInBackground(String... strings) {

        String urlString = strings[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            Log.i("Post Response:","Comment Connection Started");
            urlConnection.connect();
            Log.i("Post Response:","Comment Connection Complete");
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String response = "";
            while (scanner.hasNext()){
                response += scanner.next();
            }

            ArrayList<Comment> comments = parseComment(response);
            return comments;


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


    private ArrayList<Comment> parseComment(String response) throws JSONException {
        ArrayList<Comment> comments = null;
        JSONArray commentArray = new JSONArray(response);

        if(commentArray!=null){
            comments = new ArrayList<>();
            for(int i = 0;i<commentArray.length();i++){
                JSONObject commentObject = commentArray.getJSONObject(i);

                String name = commentObject.getString("name");
                String body = commentObject.getString("body");
//                String name = courseObject.getString("name");
//                String overview = courseObject.getString("overview");
                Comment comment = new Comment(name,body);
                comments.add(comment);
            }
        }


        return comments;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Comment> comments) {
        commentDownloadListener.onCommentDownload(comments);
    }

    static interface CommentDownloadListener{

        void onCommentDownload(ArrayList<Comment> comments);

    }
}
