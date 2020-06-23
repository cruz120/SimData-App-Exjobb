package com.example.simdata.ui.swimpoolpartition;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simdata.R;

public class SwimpoolPartitionFragment extends Fragment {

    private SwimpoolPartitionViewModel mViewModel;

    public static SwimpoolPartitionFragment newInstance() {
        return new SwimpoolPartitionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.swimpool_partition_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SwimpoolPartitionViewModel.class);
        // TODO: Use the ViewModel
    }

}
