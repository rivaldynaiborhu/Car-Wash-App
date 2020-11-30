package com.example.carwashapps.Interface;

import java.util.List;

public interface IAllCarwashLoadListener {

    void onAllCarwashLoadSuccess (List<String> areaNameList);
    void onAllCarwashLoadFailed(String message);
}
