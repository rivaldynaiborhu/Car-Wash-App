package com.example.carwashapps.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashapps.Adapter.MyCarWashAdapter;
import com.example.carwashapps.Common.Common;
import com.example.carwashapps.Interface.IAllCarwashLoadListener;
import com.example.carwashapps.Interface.IBranchLoadListener;
import com.example.carwashapps.R;
import com.example.carwashapps.model.Carwash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import com.example.carwashapps.Common.SpacesItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class BookingStep1Fragment extends Fragment implements IAllCarwashLoadListener, IBranchLoadListener {

    //Variable
    CollectionReference allCarwashRef;
    CollectionReference branchRef;

    IAllCarwashLoadListener  iAllCarwashLoadListener;
    IBranchLoadListener iBranchLoadListener;

    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.recycler_carwash)
    RecyclerView recycler_carwash;

    Unbinder unbinder;

    AlertDialog dialog;


    static BookingStep1Fragment instance;

    public static BookingStep1Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep1Fragment();

        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allCarwashRef = FirebaseFirestore.getInstance().collection("AllCarwash");
        iAllCarwashLoadListener = this;
        iBranchLoadListener = this;

        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_one,container, false);
        unbinder = ButterKnife.bind(this, itemView);

        initView();
        loadAllCarwash();
        return itemView;
    }

    private void initView()
    {
        recycler_carwash.setHasFixedSize(true);
        recycler_carwash.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recycler_carwash.addItemDecoration(new SpacesItemDecoration(4));
    }

    private void loadAllCarwash() {
        allCarwashRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            List<String> list = new ArrayList<>();
                            list.add("Silahkan Pilih Kota");
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult())
                                list.add(documentSnapshot.getId());
                            iAllCarwashLoadListener.onAllCarwashLoadSuccess(list);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAllCarwashLoadListener.onAllCarwashLoadFailed(e.getMessage());
            }
        });

    }

    @Override
    public void onAllCarwashLoadSuccess(List<String> areaNameList) {
        spinner.setItems(areaNameList);
        spinner.setOnItemSelectedListener(  new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position > 0)
                {
                    loadBranchOfCity(item.toString());
                }
                else
                    recycler_carwash.setVisibility(View.GONE);
            }
        });
    }

    private void loadBranchOfCity(String cityName) {
        dialog.show();

        Common.city = cityName;

        branchRef = FirebaseFirestore.getInstance()
                .collection("AllCarwash")
                .document(cityName)
                .collection("Branch");

        branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Carwash> list = new ArrayList<>();
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult())
                    {
                        Carwash carwash = documentSnapshot.toObject(Carwash.class);
                        carwash.setCarwashId(documentSnapshot.getId());
                        list.add(carwash);
                    }
                    iBranchLoadListener.onBranchLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBranchLoadListener.onBranchLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllCarwashLoadFailed(String message) {
           Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBranchLoadSuccess(List<Carwash> carwashList)  {
        MyCarWashAdapter adapter = new MyCarWashAdapter(getActivity(), carwashList);
        recycler_carwash.setAdapter(adapter);
        recycler_carwash.setVisibility(View.VISIBLE);

        dialog.dismiss();
    }

    @Override
    public void onBranchLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show( );
        dialog.dismiss();
    }
}
