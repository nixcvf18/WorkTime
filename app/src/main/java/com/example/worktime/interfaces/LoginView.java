package com.example.worktime.interfaces;
public interface LoginView {
    void showLoadingProgressDialog();
    void hideLoadingProgeressDialog();
    void navigateToMain();
    void showErrorMessage(String errorMessage);
}
