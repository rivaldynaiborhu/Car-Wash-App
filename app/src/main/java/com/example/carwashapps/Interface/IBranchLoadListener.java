package com.example.carwashapps.Interface;

import com.example.carwashapps.model.Carwash;

import java.util.List;

public interface IBranchLoadListener {
    void onBranchLoadSuccess (List<Carwash> carwashList);
    void onBranchLoadFailed(String message);

}
