package com.example.carwashapps.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.WorkSource;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.carwashapps.Adapter.MyCarWashAdapter;
import com.example.carwashapps.Adapter.MyWorkerAdapter;
import com.example.carwashapps.Common.Common;
import com.example.carwashapps.Common.SpacesItemDecoration;
import com.example.carwashapps.R;
import com.example.carwashapps.model.Worker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingStep2Fragment extends Fragment {

    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;

    @BindView(R.id.recycler_worker)
    RecyclerView recycler_worker;

    private BroadcastReceiver workerDoneReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Worker>  workerArrayList = intent.getParcelableArrayListExtra(Common.KEY_WORKER_LOAD_DONE);
            // Create adapter late
            MyWorkerAdapter adapter = new MyWorkerAdapter(getContext(),workerArrayList);
            recycler_worker.setAdapter(adapter);
        }
    };

    static BookingStep2Fragment instance;
    public static BookingStep2Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep2Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(workerDoneReceiver, new IntentFilter(Common.KEY_WORKER_LOAD_DONE));
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(workerDoneReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_two, container, false);

        unbinder = ButterKnife.bind(this, itemView);

        initView();
        return itemView;
    }

    private void initView() {
        recycler_worker.setHasFixedSize(true);
        recycler_worker.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recycler_worker.addItemDecoration(new SpacesItemDecoration(4));
    }
}
