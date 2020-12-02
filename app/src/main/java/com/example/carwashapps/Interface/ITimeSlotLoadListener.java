package com.example.carwashapps.Interface;

import com.example.carwashapps.model.TimeSlot;

import java.util.List;
import java.util.Timer;

public interface ITimeSlotLoadListener {
    void onTimeSlotLoadSuccess(List<TimeSlot> timeSlotList);
    void onTimeSLotLoadFailed (String message);
    void onTimeSlotLoadEmpty();

}
