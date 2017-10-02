package com.example.tejas.networkingdemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by charoo on 02-Oct-17.
 */

public class CustomAdapter extends ArrayAdapter<Post> {

    private ArrayList<Post> items;
    private Context mContext;
    viewHolder viewHolder;




    public CustomAdapter(@NonNull Context context, ArrayList<Post> arrayList) {
        super(context, 0);

        mContext=context;
        items=arrayList;

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.detail_row_layout, null);
            viewHolder= new viewHolder();
            viewHolder.title= (TextView) convertView.findViewById(R.id.title);
            viewHolder.body= (TextView) convertView.findViewById(R.id.body);
            convertView.setTag(viewHolder);
        }

        Post item = items.get(position);
        viewHolder holder = (viewHolder)convertView.getTag();
        holder.title.setText("Post id: "+ position+ " - " + item.getTitle());
        holder.body.setText("Content - " + item.getBody());



        return convertView;
    }

    static class viewHolder{
        TextView title;
        TextView body;
    }
}
