package com.example.swdemoapp.ui.error;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ErrorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ErrorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Error occurred while loading the page.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}