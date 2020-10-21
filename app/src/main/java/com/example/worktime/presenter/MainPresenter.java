package com.example.worktime.presenter;
import com.example.worktime.interacor.MainInteractor;
import com.example.worktime.interfaces.MainView;
public class MainPresenter implements MainInteractor.OnMainCreateFinishListener {
    private MainView mainView;
    private MainInteractor mainInteractor;
    public MainPresenter(MainView mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }
    public void onDestroy() {
        mainView = null;
    }
    public void passUserId(String userId) {
        mainInteractor.getName(userId, this);
    }
    @Override
    public void onGetNameSuccess(String userName) {
        mainView.setUserName(userName);
    }
}
