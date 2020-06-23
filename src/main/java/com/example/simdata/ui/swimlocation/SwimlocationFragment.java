package com.example.simdata.ui.swimlocation;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simdata.Download;
import com.example.simdata.SwimmingPoolDataRepository;
import com.example.simdata.MainMenuActivity;
import com.example.simdata.R;
import com.example.simdata.SwimpoolReports;
import com.example.simdata.room.database.db.SwimpoolDatabase;
import com.example.simdata.room.database.db.entity.Location;
import com.example.simdata.room.database.db.entity.Swimpool;

import java.util.List;

public class SwimlocationFragment extends Fragment {

    private TextView locationText;
    private GradientDrawable drawable;
    private Context context;
    private SwimlocationViewModel swimlocationViewModel;
    SwimpoolDatabase swimpoolDatabase;

    private SwimlocationViewModel mViewModel;

    public static SwimlocationFragment newInstance() {
        return new SwimlocationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {




        final View view = inflater.inflate(R.layout.swimlocation_fragment, container, false);

        locationText = view.findViewById(R.id.swimmingPoolName);

            swimlocationViewModel = ViewModelProviders.of(this).get(SwimlocationViewModel.class);
            swimlocationViewModel.getLocation().observe(this, new Observer<List<Location>>() {

                @Override
                public void onChanged(@Nullable List<Location> locations) {

                    locationText.setTextSize(30);
                    if(locations.isEmpty()){
                        locationText.setText(" ");
                    }else{
                        locationText.setText(locations.get(0).getName());

                    }




                }
            });


           swimlocationViewModel.getSwimpoolLiveData().observe(this, new Observer<List<Swimpool>>() {
               @Override
               public void onChanged(@Nullable List<Swimpool> swimpools) {

                   int scale = 0;
                   for(int i = 0; i < swimpools.size(); i++) {

                       scale = scale + 1;
                       int id = swimpools.get(i).getId();
                       String name = swimpools.get(i).getName();

                       double left = roundDownOne(swimpools.get(i).getRect_left());
                       double top = roundDownOne(swimpools.get(i).getRect_top());
                       double right = roundDownOne(swimpools.get(i).getRect_right());
                       double bottom = roundDownOne(swimpools.get(i).getRect_bottom());

                        createSwimmingPool(name,left, top, right, bottom, scale,id,view);
                   }
               }
           });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SwimlocationViewModel.class);
        // TODO: Use the ViewModel
    }

    public void createSwimmingPool(final String name,double left, double top, double right, double bottom, int scale, final int id,View view){


        int size = 850;
        drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(Color.CYAN);
        drawable.setStroke(3, Color.BLACK);

        FrameLayout fl = (FrameLayout) view.findViewById(R.id.FrameLay);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        int width = (int) (size*(right - left));
        int height = (int) (size*(bottom - top));
        int mStart = (int) (size*left);
        int mTop = (int) (size*top);


        Button btn = new Button(getContext());
        btn.setText(name);
        btn.setId(id);
        btn.setWidth(width);
        btn.setHeight(height);
        btn.setBackground(drawable);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Log.i("TAG", "The index is" + index);
                Toast.makeText(v.getContext(), "Swimming pool:" + id, Toast.LENGTH_SHORT).show();
                sendPickedTemplate(id,name);

            }
        });

        params.setMarginStart(mStart);
        params.setMargins(0,mTop+scale,0,0);
        btn.setLayoutParams(params);
        fl.addView(btn);

    }
    public void sendPickedTemplate(int swimmingPoolId,String swimmingPoolname) {
        Intent intent = new Intent(getContext(), SwimpoolReports.class);
        intent.putExtra("swimmingPoolId",swimmingPoolId);
        intent.putExtra("swimmingPoolName",swimmingPoolname);
       // Intent intent1 = new Intent(getContext(),SwimpoolReports.class);
        startActivity(intent);
    }
    public static double roundDownOne(double d) {
        return ((long)(d * 1e2)) / 1e2;
        //Long typecast will remove the decimals
    }



}
