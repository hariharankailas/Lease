package com.example.hariramesh.dynamictest;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hariramesh on 9/23/16.
 */
public class ListAdapter extends ArrayAdapter<String> {
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *
     */


   ArrayList<String> a;
    public ListAdapter(Context context, int resource, ArrayList<String> arr) {
        super(context, resource);
        a = arr;
    }

    @Override
    public int getCount() {
        return a.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.bubblelist,parent,false);


        TextView text = (TextView)inflater.findViewById(R.id.text1);

        text.setText(a.get(position));
        if(position % 2 ==1)
        {
            text.setGravity(Gravity.LEFT);
        }
        else
        {
            text.setGravity(Gravity.RIGHT);
        }

        return inflater;


    }
}
