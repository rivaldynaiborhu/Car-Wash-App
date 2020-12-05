package com.example.carwashapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.carwashapps.Common.Common;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Profile extends AppCompatActivity implements View.OnClickListener {


    TextView name, email, id;
    Button signOut;
    ImageView imageView;
    public Button button;
    GoogleSignInClient mGoogleSignInClient;
    public CardView card1, card2, card3, card4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(Profile.this);


        //Cardview Function_______________________________________________________
        card1 =(CardView) findViewById(R.id.card_view_booking);
        card2 =(CardView) findViewById(R.id.card_view_cart);
        card3 =(CardView) findViewById(R.id.card_view_history);
        card4 =(CardView) findViewById(R.id.card_view_notification);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);



        //Banner______________________________________________________________________________
        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://i.ibb.co/jTM6G5w/banner1.png"));
        slideModels.add(new SlideModel("https://i.ibb.co/b7614jp/banner2.png"));
        slideModels.add(new SlideModel("https://i.ibb.co/S6GR2yN/banner3.png"));
        imageSlider.setImageList(slideModels, true);


        //Bottom Navigation__________________________________________________________________________________________________
        // inisialisasi variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        //Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()

        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_shopping:
                        startActivity(new Intent(getApplicationContext(), ShopingActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.action_home:
                        return true;
                }
                return false;
            }
        });


        //Google Authentication___________________________________________________
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        //user profile
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signOut = findViewById(R.id.signOut);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        imageView = findViewById(R.id.imgProfile);
        id = findViewById(R.id.id);


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, MainActivity.class);
                switch (view.getId()) {
                    case R.id.signOut:
                        signOut();
                        break;
                }
            }
        });



         GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            name.setText(personName);
            email.setText(personEmail);
            id.setText(personId);

            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
        }

    }





    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Profile.this, "Signed out Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

//CardView Home Function_______________________________________________________________________________________________________
    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId())
        {
            case R.id.card_view_booking :
            i = new Intent(this, BookingActivity.class);
            startActivity(i);
            break;

        }

    }
}




