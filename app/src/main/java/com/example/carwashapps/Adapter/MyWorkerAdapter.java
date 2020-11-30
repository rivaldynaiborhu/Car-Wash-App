package com.example.carwashapps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashapps.R;
import com.example.carwashapps.model.Worker;

import java.util.List;

public class MyWorkerAdapter extends RecyclerView.Adapter<MyWorkerAdapter.MyViewHolder> {

    Context context;
    List<Worker> workerList;

    public MyWorkerAdapter(Context context, List<Worker> workerList) {
        this.context = context;
        this.workerList = workerList;
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
    }

    @Override
    public int getItemCount() {
        return workerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_worker_name;
        RatingBar ratingBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_worker_name = (TextView) itemView.findViewById(R.id.txt_worker_name);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rtb_worker);

        }
    }
}
