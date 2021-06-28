package com.example.eathit.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hiện tại bạn chưa có thông báo nào");
    }

    public LiveData<String> getText() {
        return mText;
    }
}