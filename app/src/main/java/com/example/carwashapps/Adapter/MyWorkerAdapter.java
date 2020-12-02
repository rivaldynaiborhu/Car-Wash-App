package com.example.carwashapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashapps.Common.Common;
import com.example.carwashapps.Interface.IRecyclerItemSelectedListener;
import com.example.carwashapps.R;
import com.example.carwashapps.model.Worker;

import java.util.ArrayList;
import java.util.List;

public class MyWorkerAdapter extends RecyclerView.Adapter<MyWorkerAdapter.MyViewHolder> {

    Context context;
    List<Worker> workerList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyWorkerAdapter(Context context, List<Worker> workerList) {
        this.context = context;
        this.workerList = workerList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_worker, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_worker_name.setText(workerList.get(i).getName());
        myViewHolder.ratingBar.setRating((float)workerList.get(i).getRating());
        if (!cardViewList.contains(myViewHolder.card_worker))
            cardViewList.add(myViewHolder.card_worker);

        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                // set background for all item not choice
                for (CardView cardView : cardViewList)
                {
                    cardView.setCardBackgroundColor(context.getResources()
                            .getColor(android.R.color.white));
                }

                //set background for choice
                myViewHolder.card_worker.setCardBackgroundColor(
                        context.getResources()
                .getColor(android.R.color.holo_orange_dark)
                );

                //send local broadcast to enable button next
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_BARBER_SELECTED, workerList.get(pos));
                intent.putExtra(Common.KEY_STEP,2);
                localBroadcastManager.sendBroadcast(intent);
            }


        });
    }

    @Override
    public int getItemCount() {
        return workerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_worker_name;
        RatingBar ratingBar;
        CardView card_worker;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_worker = (CardView) itemView.findViewById(R.id.card_worker);
            txt_worker_name = (TextView) itemView.findViewById(R.id.txt_worker_name);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rtb_worker);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view, getAdapterPosition());
        }
    }
}
