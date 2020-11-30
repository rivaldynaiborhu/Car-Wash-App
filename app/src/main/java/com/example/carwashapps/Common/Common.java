package com.example.carwashapps.Common;

import com.example.carwashapps.model.Carwash;
import com.google.firebase.firestore.auth.User;

public class Common {
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_CARWASH_STORE = "CARWASH_SAVE";
    public static final String KEY_WORKER_LOAD_DONE ="WORKER_LOAD_DONE";
    public static Carwash currentCarwash;
    public static int step = 0; // Init first step is 0
    public static String city="";
}
