package com.example.carwashapps.Fragments;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.carwashapps.BookingActivity;
import com.example.carwashapps.Common.Common;
import com.example.carwashapps.MainActivity;
import com.example.carwashapps.Profile;
import com.example.carwashapps.R;
import com.example.carwashapps.model.BookingInformation;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.microedition.khronos.egl.EGLDisplay;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BookingStep4Fragment extends Fragment {


    SimpleDateFormat simpleDateFormat;
    LocalBroadcastManager localBroadcastManager;
    Unbinder unbinder;

    private Button btn_confirm;
    public String personName;

    GoogleSignInClient mGoogleSignInClient;

    @BindView(R.id.txt_booking_worker_text)
    TextView txt_booking_worker_text;
    @BindView(R.id.txt_booking_time_text)
    TextView txt_booking_time_text;
    @BindView(R.id.txt_carwash_address)
    TextView txt_carwash_address;
    @BindView(R.id.txt_carwash_name)
    TextView txt_carwash_name;
    @BindView(R.id.txt_carwash_open_hours)
    TextView txt_carwash_open_hours;
    @BindView(R.id.txt_carwash_phone)
    TextView txt_carwash_phone;
    @BindView(R.id.txt_carwash_website)
    TextView txt_carwash_website;

    @OnClick(R.id.btn_confirm)



    void confirmBooking(){

        //Create Booking Information
        BookingInformation bookingInformation = new BookingInformation();
        bookingInformation.setWorkerId(Common.currentWorker.getWorkerId());
        bookingInformation.setWorkerName(Common.currentWorker.getName());
        bookingInformation.setCustomerName(personName);
//        bookingInformation.setCustomerPhone(catatan);
        bookingInformation.setCarwashId(Common.currentCarwash.getCarwashId());
        bookingInformation.getCarwashAddress(Common.currentCarwash.getAddress());
        bookingInformation.setCarwashName(Common.currentCarwash.getName());
        bookingInformation.setTime(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                .append(" at ")
                .append(simpleDateFormat.format(Common.currentDate.getTime())).toString());
        bookingInformation.setSlot(Long.valueOf(Common.currentTimeSlot));

        //Submit to worker document ini kode yang bener
        DocumentReference bookingDate = FirebaseFirestore.getInstance()
                .collection("allcarwash")
                .document(Common.city)
                .collection("Branch")
                .document(Common.currentCarwash.getCarwashId())
                .collection("Worker")
                .document(Common.currentWorker.getWorkerId())
                .collection(Common.simpleDateFormat.format(Common.currentDate.getTime()))
                .document(String.valueOf(Common.currentTimeSlot));


        //Write Data
        bookingDate.set(bookingInformation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        resetStaticData();
                        getActivity().finish(); // close activity
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


//        //Ini kode percobaan
//        DocumentReference bookingdateuser = FirebaseFirestore.getInstance()
//             .collection("AllUser")
                //              .document(Common.city)
                //               .collection(Common.simpleDateFormat.format(Common.currentDate.getTime()))
 //               .document(String.valueOf(Common.currentTimeSlot));


        //ALL-USER COLLECTION WRITE DATABASE
//        bookingdateuser.set(bookingInformation)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        resetStaticData();
//                        getActivity().finish(); // close activity
//                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });




    }

    private void resetStaticData() {
        Common.step = 0;
        Common.currentTimeSlot = -1;
        Common.currentCarwash = null;
        Common.currentWorker = null;
        Common.currentDate.add(Calendar.DATE,0);        //Current date added
    }


    BroadcastReceiver confirmBookingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setData();
        }
    };

    private void setData() {
        txt_booking_worker_text.setText(Common.currentWorker.getName());
        txt_booking_time_text.setText(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
        .append(" at ")
        .append(simpleDateFormat.format(Common.currentDate.getTime())));

        txt_carwash_address.setText(Common.currentCarwash.getAddress());
        txt_carwash_website.setText(Common.currentCarwash.getWebsite());
        txt_carwash_name.setText(Common.currentCarwash.getName());
        txt_carwash_open_hours.setText(Common.currentCarwash.getOpenHours());


    }


    static BookingStep4Fragment instance;
    public static BookingStep4Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep4Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Apply format for date display on Confirm
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(confirmBookingReceiver, new IntentFilter(Common.KEY_CONFIRM_BOOKING));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            personName = acct.getDisplayName();
            Log.d("personName", personName);

        }


    }



    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(confirmBookingReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_four,container, false);
        unbinder = ButterKnife.bind(this, itemView);

        return itemView;



    }


}
