package com.example.worktime.presenter;
import android.icu.lang.UProperty;
import com.example.worktime.interacor.LoginInteractor;
import com.example.worktime.interfaces.LoginView;
public class LoginPresenter implements LoginInteractor.OnLoginFinishedListener {
    /*声明成员变量*/
    private LoginView loginView;
    private LoginInteractor loginInteractor;
        /*在构造函数中初始化类中的成员变量*/
    public LoginPresenter(LoginView loginView, LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }
    //自定义的函数 用来验证用户名和密码
    public void validateIdAndPassword(String userID, String passWord) {
        if (loginView != null) {
            /*负责View的展示*/
            loginView.showLoadingProgressDialog();
        }
        /*负责处理业务逻辑*/
        loginInteractor.login(userID, passWord, this);
    }
    //自定义的函数 避免内存泄露
    public void onDestroy() {
         loginView = null;
    }
    /*重写耦合子回调接口中的函数  根据webservice返回的结果进行处理*/
    @Override
    public void onFailed(String errorMessage) {
        if (loginView != null) {
            /*负责View的展示*/
            loginView.hideLoadingProgeressDialog();
            /*将参数传给View接口中的showErrorMessage()函数*/
            loginView.showErrorMessage(errorMessage);
        }
    }
    /*重写回调接口中的函数  根据webservice返回的结果进行处理*/
    @Override
    public void onSuccess() {
        if (loginView != null) {
            //负责view的切换 跳转到主界面
            loginView.navigateToMain();
        }
    }
}
