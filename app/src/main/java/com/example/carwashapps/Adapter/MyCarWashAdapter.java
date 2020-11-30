package com.example.carwashapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.nfc.cardemulation.CardEmulation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashapps.Common.Common;
import com.example.carwashapps.Interface.IRecyclerItemSelectedListener;
import com.example.carwashapps.R;
import com.example.carwashapps.model.Carwash;

import java.util.ArrayList;
import java.util.List;

public class MyCarWashAdapter extends RecyclerView.Adapter<MyCarWashAdapter.MyViewHolder> {

    Context context;
    List<Carwash> carwashList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;


    public MyCarWashAdapter(Context context, List<Carwash> carwashList) {
        this.context = context;
        this.carwashList = carwashList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_carwash, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_carwash_name.setText(carwashList.get(i).getName());
        myViewHolder.txt_carwash_address.setText(carwashList.get(i).getAddress());

        if (!cardViewList.contains(myViewHolder.card_carwash))
            cardViewList.add(myViewHolder.card_carwash);

        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {

               // Set white background for all card not be selected
                for(CardView cardView:cardViewList)
                    cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                //Set selected BG for only selected item
                myViewHolder.card_carwash.setCardBackgroundColor(context.getResources()
                .getColor(android.R.color.holo_orange_dark));

               // send broadcast to tell Booking Activity enable the button
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_CARWASH_STORE, carwashList.get(pos));
                localBroadcastManager.sendBroadcast(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return carwashList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_carwash_name, txt_carwash_address;
        CardView card_carwash;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_carwash = (CardView)itemView.findViewById(R.id.card_carwash);
            txt_carwash_address = (TextView) itemView.findViewById(R.id.txt_carwash_address);
            txt_carwash_name = (TextView) itemView.findViewById(R.id.txt_carwash_name);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view, getAdapterPosition());
        }
    }
}
