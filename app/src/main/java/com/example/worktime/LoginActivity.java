package com.example.worktime;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.worktime.interacor.LoginInteractor;
import com.example.worktime.interfaces.LoginView;
import com.example.worktime.interfaces.MainView;
import com.example.worktime.presenter.LoginPresenter;
public class LoginActivity extends AppCompatActivity implements LoginView {
    /*声明View中的控件*/
    private ProgressBar progressBar;
    private EditText EditUserId,EditPassWord;
    private Button   BtnLogin;
    /*声明类中的变量*/
    private LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        initView();
        initListener();
        /*实例化类中的变量*/
        presenter=new LoginPresenter(this,new LoginInteractor());




        /*声明一个函数 用来判断sharedpreference中是否存在userid 键值
        *
        * 如果键值存在那么将其取出直接赋值到工号框中
        *
        * */


     SharedPreferences   preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String idValue=preferences.getString("userId", "000000");
        if (idValue.equals("000000")) {


            // userIdStr = preferences.getString("userId", "000000");
        } else {


            EditUserId.setText(idValue);

        }







    }
    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
    private void initListener() {
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateIdAndPassword();
            }
        });
    }
    /*初始化控件*/
    private void initView() {
        EditUserId = findViewById(R.id.et_user_id);
        EditPassWord = findViewById(R.id.et_password);
        BtnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progress_bar);
    }
/*验证id和password是否正确*/
    private void validateIdAndPassword() {
        presenter.validateIdAndPassword(EditUserId.getText().toString(),
                EditPassWord.getText().toString());
    }
    @Override
    public void showLoadingProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideLoadingProgeressDialog() {
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void navigateToMain() {
        Intent intent=new Intent();
        intent.setClass(this, MainActivity.class);
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userId", EditUserId.getText().toString());
        editor.commit();
       // intent.putExtra("userId", EditUserId.getText().toString());
        startActivity(intent);
        finish();
    }
    /*实现View接口中定义的函数*/
    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}