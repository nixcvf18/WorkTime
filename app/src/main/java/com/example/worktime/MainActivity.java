package com.example.worktime;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.worktime.interacor.MainInteractor;
import com.example.worktime.interfaces.MainView;
import com.example.worktime.presenter.MainPresenter;
public class MainActivity extends AppCompatActivity  implements MainView {
    /*声明View中的控件*/
    /*TextView*/
    private TextView tvUserId,tvUserName;
    /*Button*/
    private Button btnBgOk,btnBgNg,
    btnBgUndo,btnScGiveout,btnGsPrint,
    btnClose;
    private MainPresenter mainPresenter;
/*存储工号和姓名  然后用SharedPreferences存储起来*/
    private String userIdStr,userNameStr;
/*sharedPreference*/
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        mainPresenter = new MainPresenter(this, new MainInteractor());
        passUserId();
    }
    private void initListener() {
        btnBgOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "" + getString(R.string.main_tip_message), Toast.LENGTH_SHORT).show();
            }
        });
        btnBgNg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "" + getString(R.string.main_tip_message), Toast.LENGTH_SHORT).show();
            }
        });
        btnBgUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "" + getString(R.string.main_tip_message), Toast.LENGTH_SHORT).show();
            }
        });
        btnScGiveout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "" + getString(R.string.main_tip_message), Toast.LENGTH_SHORT).show();
            }
        });
        btnGsPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToWorkTimePrint();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });
    }
    private void initView() {
        /*TextView*/
        tvUserId = findViewById(R.id.tv_userId);
        tvUserName = findViewById(R.id.tv_userName);
        /*Button*/
       btnBgOk= findViewById(R.id.btn_main_bg_ok);
        btnBgNg=findViewById(R.id.btn_main_bg_ng);
        btnBgUndo=findViewById(R.id.btn_main_bg_undo);
        btnScGiveout=findViewById(R.id.btn_main_sc_giveout);
        btnGsPrint=findViewById(R.id.btn_main_gs_print);
        btnClose=findViewById(R.id.btn_main_close);
    }

    @Override
    public void navigateToWorkTimePrint() {
       // startActivity(new Intent(this, ShowHistoryActivity.class));
        Intent intent=new Intent();
        intent.setClass(this, ShowHistoryActivity.class);
        preferences = getSharedPreferences("main", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("account", userIdStr);
        editor.putString("name", userNameStr);
        editor.commit();
        //intent.putExtra("userId", tvUserId.getText().toString());
        startActivity(intent);
    }
    /*设置从webservice返回来的用户名称*/
    @Override
    public void setUserName(String userName) {
       preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        userIdStr=preferences.getString("userId","000000");
       // userIdStr=getIntent().getExtras().getString("userId");
        userNameStr = userName;
        tvUserId.setText(userIdStr);
        tvUserName.setText(userNameStr);
       /* SharedPreferences.Editor editor =
                getSharedPreferences("workerInfo", Context.MODE_PRIVATE).edit();*/
    /*    SharedPreferences preferences = getSharedPreferences("worker", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("wokerid", userIdStr);
        editor.putString("wokername", userNameStr);
        editor.commit();
*/
    }
    public void passUserId() {
        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        userIdStr=preferences.getString("userId","000000");
        mainPresenter.passUserId(userIdStr);
       // mainPresenter.passUserId(getIntent().getExtras().getString("userId"));
    }
    @Override
    protected void onDestroy() {
        mainPresenter.onDestroy();
        super.onDestroy();
    }
}