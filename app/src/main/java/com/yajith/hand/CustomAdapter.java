package com.yajith.hand;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAdapter extends ArrayAdapter<String> {
    String[] name;
    int[] value;
    Activity context;
    public CustomAdapter(@NonNull Activity context, String[] name, int[] value) {
        super(context,R.layout.card_view,name);
        this.context=context;
        this.name=name;
        this.value=value;
    }
    private int lastPosition = -1;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View row=layoutInflater.inflate(R.layout.card_view,null,true);
        TextView textView=row.findViewById(R.id.head);
        ProgressBar progressBar=row.findViewById(R.id.stats_progressbar);
        TextView textView1=row.findViewById(R.id.number_of_calories);
        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        row.startAnimation(animation);
        lastPosition = position;
        int fina=100;
        int local=value[position];
        double per=(double)local/(double)fina;
        int totla=(int)(per*100);
        progressBar.setProgress(totla);
        textView.setText(name[position]);
        String val=String.valueOf(totla)+"/100";
        textView1.setText(val);
        return row;
    }
}
