package com.example.carwashapps.Common;

import android.content.Intent;

import com.example.carwashapps.Profile;
import com.example.carwashapps.model.Carwash;
import com.example.carwashapps.model.TimeSlot;
import com.example.carwashapps.model.Worker;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Common {
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_CARWASH_STORE = "CARWASH_SAVE";
    public static final String KEY_WORKER_LOAD_DONE ="WORKER_LOAD_DONE";
    public static final String KEY_DISPLAY_TIME_SLOT ="DISPLAY_TIME_SLOT";
    public static final String KEY_STEP ="STEP" ;
    public static final String KEY_BARBER_SELECTED ="BARBER_SELECTED" ;
    public static final int TIME_SLOT_TOTAL = 20;
    public static final Object DISABLE_TAG ="DISABLE" ;
    public static final String KEY_TIME_SLOT ="TIME_SLOT" ;
    public static final String KEY_CONFIRM_BOOKING ="CONFIRM_BOOKING" ;
    public static final String IS_LOGIN ="IsLogin" ;
    public static final String KEY_USER_INFORMATION = "USER_INFORMATION";
    public static final String KEY_EMAIL_INFORMATION = "EMAIL_INFORMATION";
    public static Carwash currentCarwash;
    public static int step = 0; // Init first step is 0
    public static String city="";
    public static Worker currentWorker;
    public static int currentTimeSlot=-1;
    public static Calendar currentDate = Calendar.getInstance();
    public static Profile currentUser;
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");   //only use when we need format key

    public static String convertTimeSlotToString(int slot) {
        switch (slot)
        {
            case 0 :
                return "09:00-09:30";
            case 1 :
                return "09:30-10.00";
            case 2 :
                return "10:00-10:30";
            case 3 :
                return "10:30-11.00";
            case 4 :
                return "11:00-11:30";
            case 5 :
                return "11:30-12.00";
            case 6 :
                return "12:00-12:30";
            case 7 :
                return "12:30-13.00";
            case 8 :
                return "13:00-13:30";
            case 9 :
                return "13:30-14:00";
            case 10 :
                return "14:00-14:30";
            case 11 :
                return "14:30-15.00";
            case 12 :
                return "15:00-15:30";
            case 13 :
                return "15:30-16.00";
            case 14 :
                return "16:00-16:30";
            case 15 :
                return "16:30-17.00";
            case 16 :
                return "17:00-17:30";
            case 17 :
                return "17:30-18.00";
            case 18 :
                return "18:00-18:30";
            case 19 :
                return "18:30-19.00";
            default:
                return "Closed";
        }
    }
}
