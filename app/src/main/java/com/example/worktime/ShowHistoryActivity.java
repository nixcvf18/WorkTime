package com.example.worktime;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import com.example.worktime.adapter.ShowHistoryAdapter;
import com.example.worktime.interacor.ShowHistoryInteractor;
import com.example.worktime.interfaces.ShowHistoryView;
import com.example.worktime.model.TBlogBean;
import com.example.worktime.presenter.ShowHistoryPresenterr;
import com.example.worktime.util.ConstantUtil;
import com.example.worktime.util.DatePickerDialogUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
public class ShowHistoryActivity extends AppCompatActivity implements ShowHistoryView, DatePickerDialog.OnDateSetListener {
    /*控件实例*/
    private EditText editTextHistory;
    private TextView textViewAlarm;
    private Button btnAdd;
    private RecyclerView recyclerView;
    private ShowHistoryAdapter historyAdapter;
    /*通用标题栏控件实例*/
    private TextView textViewTitleBack,textViewTitleContent;
    /*没有数据时 界面需要展示的文字*/
    private  TextView textViewEmptyMessage;

    /*中间变量*/
    private String date, userId;
    /*主持人实例*/
    private ShowHistoryPresenterr showHistoryPresenterr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        initView();
        initListener();
    }
    private void initListener() {
        textViewTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textViewAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogUtil.getDatePickerDialog(ShowHistoryActivity.this, ShowHistoryActivity.this);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowHistoryActivity.this, PrintActivity.class));
            }
        });
    }
    private void initView() {
        editTextHistory = findViewById(R.id.et_history_date);
        textViewAlarm = findViewById(R.id.tv_history_alarm);
        btnAdd = findViewById(R.id.btn_history_add);
        /*标题栏控件*/
        textViewTitleBack = findViewById(R.id.tv_titlebar_back);
        textViewTitleContent = findViewById(R.id.tv_titlebar_content);
        setTitleBar();
        /*没有数据时需要展示的文本*/
        textViewEmptyMessage = findViewById(R.id.tv_history_empty_message);

        historyAdapter = new ShowHistoryAdapter(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*添加分割线*/
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        /*实例化主持人*/
        showHistoryPresenterr = new ShowHistoryPresenterr(this, new ShowHistoryInteractor());
        date = DatePickerDialogUtil.getCurrentDay(ConstantUtil.FourTwoTwoDateFormat);
        editTextHistory.setText(date);
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        userId=preferences.getString("userId","000000");
        //userId = getIntent().getStringExtra("userId");
        showHistoryPresenterr.passDateAndWorkId(date, userId);
    }
    /**
     * @param view       the picker associated with the dialog
     * @param year       the selected year
     * @param month      the selected month (0-11 for compatibility with
     *                   {@linkCalendar#MONTH})
     * @param dayOfMonth th selected day of the month (1-31, depending on
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = DatePickerDialogUtil.getFormattedTheDateFromDatePickerDialog(year, month, dayOfMonth);
        editTextHistory.setText(date);
        showHistoryPresenterr.passDateAndWorkId(date, userId);
    }
    @Override
    public void setRecyclerViewItems(List<TBlogBean> tBlogBeans) {
        historyAdapter.setData(tBlogBeans);
        recyclerView.setAdapter(historyAdapter);

        /*如果当前查询日期 有数据返回 那么就隐藏文本框*/
        if (tBlogBeans.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            textViewEmptyMessage.setVisibility(View.INVISIBLE);
        } else {
            textViewEmptyMessage.setVisibility(View.VISIBLE);

        }


    }
    @Override
    public void setTitleBar() {
        textViewTitleBack.setVisibility(View.VISIBLE);
        textViewTitleContent.setText(getString(R.string.history_title_content));
    }

    @Override
    public void setEmptyHistoryLayout() {
        recyclerView.setVisibility(View.INVISIBLE);
        textViewEmptyMessage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showHistoryPresenterr.passDateAndWorkId(date, userId);
    }
    @Override
    protected void onDestroy() {
        showHistoryPresenterr.onDestroy();
        super.onDestroy();
    }
}