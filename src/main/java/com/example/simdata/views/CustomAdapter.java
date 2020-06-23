package com.example.simdata.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.simdata.R;

public class CustomAdapter extends BaseAdapter {
Context context;
LayoutInflater inf;

public CustomAdapter (Context applicationContext) {
    this.context = applicationContext;
    inf = (LayoutInflater.from(applicationContext));
}

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       convertView = inf.inflate(R.layout.customlayout,null);

        Toast.makeText(this.context, "Clicked on="+position, Toast.LENGTH_LONG).show();

    //   CustomView rl = (CustomView) convertView.findViewById(R.id.ListView_temp);

        LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.List_cust);

        Button btn = new Button(this.context);
        btn.setText("TEST");
        btn.setWidth(250);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btn.setHeight(125);
        ll.addView(btn);

/*
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMarginStart(0);
        params.setMargins(0,0,0,0);
        Button btn = new Button(context);
        btn.setText("TEST");
        btn.setWidth(250);
        btn.setHeight(125);
        fl.addView(btn,params);

        params.setMarginStart(250);
        params.setMargins(0,0,0,0);
        Button btn2 = new Button(context);
        btn2.setText("TEST2");
        btn2.setWidth(250);
        btn2.setHeight(125);
        fl.addView(btn2,params);
            */
        return convertView;
    }
}
