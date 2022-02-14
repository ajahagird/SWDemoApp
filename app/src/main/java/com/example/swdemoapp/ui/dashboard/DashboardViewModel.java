package com.example.swdemoapp.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("https://lacy-common-owner.glitch.me/index-via-sw-1.html");
    }

    public LiveData<String> getText() {
        return mText;
    }
}