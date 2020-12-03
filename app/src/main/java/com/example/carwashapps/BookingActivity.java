package com.example.carwashapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.carwashapps.Adapter.MyViewPagerAdapter;
import com.example.carwashapps.Common.Common;
import com.example.carwashapps.Common.NonSwipeViewPager;
import com.example.carwashapps.Fragments.BookingStep2Fragment;
import com.example.carwashapps.model.Worker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class BookingActivity extends AppCompatActivity {

    LocalBroadcastManager localBroadcastManager;
    AlertDialog dialog;
    CollectionReference workerRef;

    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.view_pager)
    NonSwipeViewPager viewPager;
    @BindView(R.id.btn_previous_step)
    Button btn_previous_step;
    @BindView(R.id.btn_next_step)
    Button btn_next_step;

    //Event
    @OnClick (R.id.btn_previous_step)
    void previousStep(){
        if (Common.step == 3 || Common.step > 0)
        {
            Common.step--;
            viewPager.setCurrentItem(Common.step);
            if (Common.step < 3) // always enable next when  step 3
            {
                btn_next_step.setEnabled(true);
                setColorButton();
            }
        }
    }
    @OnClick (R.id.btn_next_step)
    void nextClick(){
        if (Common.step < 3 || Common.step ==0)
        {
            Common.step++;
            if(Common.step == 1) //After Choose Carwash
            {
                if (Common.currentCarwash != null)
                    loadCarwashByPlace(Common.currentCarwash.getCarwashId());
            }
            else if (Common.step ==2) //pick time slot
            {
                if (Common.currentWorker != null)
                    loadTimeSlotOfWorker (Common.currentWorker.getWorkerId());
            }

            else if (Common.step ==3) //confirm
            {
                if (Common.currentTimeSlot != -1)
                   confirmBooking();
            }
            viewPager.setCurrentItem(Common.step);
        }
    }

    private void confirmBooking() {
        //send broadcast to fragment step 4
        Intent intent = new Intent(Common.KEY_CONFIRM_BOOKING);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void loadTimeSlotOfWorker(String workerId) {
        //send local broadcast to fragment step 3
        Intent intent = new Intent(Common.KEY_DISPLAY_TIME_SLOT);
        localBroadcastManager.sendBroadcast(intent);
    }


    private void loadCarwashByPlace(String carwashId) {
        dialog.show();

        //select all worker in carwash
        //    /AllCarwash/Semarang/Branch/4UT2CGAKBQMQrAEIdmRs/Worker
        if (!TextUtils.isEmpty(Common.city))
        workerRef = FirebaseFirestore.getInstance()
                .collection("AllCarwash")
                .document(Common.city)
                .collection("Branch")
                .document(carwashId)
                .collection("Worker");

        workerRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Worker> workers = new ArrayList<>();
                        for (QueryDocumentSnapshot workerSnapshot:task.getResult())
                        {
                            Worker worker = workerSnapshot.toObject(Worker.class);
                            worker.setPassword(""); //hapus password karna ini app untuk client
                            worker.setWorkerId(workerSnapshot.getId());

                            workers.add(worker);
                        }
                        //send broadcast to BookingStep2Fragment to load Recycler
                        Intent intent = new Intent(Common.KEY_WORKER_LOAD_DONE);
                        intent.putParcelableArrayListExtra(Common.KEY_WORKER_LOAD_DONE,workers);
                        localBroadcastManager.sendBroadcast(intent);
                        dialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                    }
                });

    }

   // Broadcast Receiver
    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int step = intent.getIntExtra(Common.KEY_STEP, 0);
            if (step == 1)
            Common.currentCarwash = intent.getParcelableExtra(Common.KEY_CARWASH_STORE);
            else if (step == 2)
                Common.currentWorker = intent.getParcelableExtra(Common.KEY_BARBER_SELECTED);
            else if (step == 3)
                Common.currentTimeSlot = intent.getIntExtra(Common.KEY_TIME_SLOT,-1);

            btn_next_step.setEnabled(true);
            setColorButton();
        }
    };


    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(BookingActivity.this);

        dialog = new SpotsDialog.Builder().setContext(this).build(); // error

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver,new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));
        setupStepView();
        setColorButton();

        //view
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4); // cause we have 4 fragment
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //show step
                stepView.go(i, true);
            if (i == 0)
                btn_previous_step.setEnabled(false);
            else
                btn_previous_step.setEnabled(true);

            btn_next_step.setEnabled(false);
            setColorButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    private void setColorButton() {
        if (btn_next_step.isEnabled())
        {
            btn_next_step.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btn_next_step.setBackgroundResource(android.R.color.darker_gray);
        }

        if (btn_previous_step.isEnabled())
        {
            btn_previous_step.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btn_previous_step.setBackgroundResource(android.R.color.darker_gray);
        }
    }

    private void setupStepView() {
        List<String> stepList = new ArrayList<>();
        stepList.add("Place");
        stepList.add("Carwash");
        stepList.add("Time");
        stepList.add("Confirm");
        stepView.setSteps(stepList);
    }

    }