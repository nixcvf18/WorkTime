package com.example.worktime;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.worktime.interacor.PrintInteractor;
import com.example.worktime.interfaces.PrintView;
import com.example.worktime.model.MaterialBean;
import com.example.worktime.model.NewMaterialBean;
import com.example.worktime.model.TaskBean;
import com.example.worktime.presenter.PrintPresenter;
import com.example.worktime.util.ConstantUtil;
import com.example.worktime.util.DatePickerDialogUtil;

import java.util.List;

public class PrintActivity extends AppCompatActivity implements PrintView, DatePickerDialog.OnDateSetListener {
    private TextView textViewTitleBack, textViewTitleContent, textViewDate, textViewMaterialDsc;
    private EditText editTextWorkerID, editTextDate, editTextOrderNumber;
    private TextView textViewWorkerName;
    private TextView textViewPrintTaskDescription;
    private Spinner spinnerTaskCode, spinnerHour, spinnerMinute;
    private Button btnSaveToSQLServer;
    PrintPresenter presenter;


    int lastTaskCodePosition = 0;
    String dateStr;
    /*将要保存进数据表中的数据默认值*/
    String defaultDateValue = "",
            defaultWorkerIdValue = "",
            defaultWorkerNameValue = "",
            defaultTaskCodeValue = "",
            defaultOrderNumberValue = "",
            defaultMaterialNumberValue = "",
            defaultMaterialDscValue = "",
            defaultOpnoValue = "10",
            defaultOkQtyValue = "0",
            defaultNgQtyValue = "0",
            defaultWorkHourValue = "0",
            defaultWorkMinValue = "0",
            defaultRemark = "";

    /*自定义的函数 用来清空保存后 控件上的值 便于下一次输入*/
    public void clearWidgetValues() {
        editTextOrderNumber.setText("");
        textViewMaterialDsc.setText("");
        spinnerTaskCode.setSelection(lastTaskCodePosition, true);
        spinnerHour.setSelection(0, true);
        spinnerMinute.setSelection(0, true);
    }

    /*自定义的函数*/
    public void passTheValuesToSqlServer() {


/*        Log.e("defaultDateValue", defaultDateValue);
        Log.e("defaultWorkerIdValue", defaultWorkerIdValue);
        Log.e("defaultWorkerNameValue", defaultWorkerNameValue);
        Log.e("defaultTaskCodeValue", defaultTaskCodeValue);
        Log.e("defaultOrderNumberValue", defaultOrderNumberValue);
        Log.e("defaultMaterialNumberVa", defaultMaterialNumberValue);
        Log.e("defaultMaterialDscValue", defaultMaterialDscValue);
        Log.e("defaultOpnoValue", defaultOpnoValue);
        Log.e("defaultOkQtyValue",  defaultOkQtyValue);
        Log.e("defaultNgQtyValue",  defaultNgQtyValue);
        Log.e("defaultWorkHourValue", defaultWorkHourValue);
        Log.e("defaultWorkMinValue",  defaultWorkMinValue);
        Log.e("defaultRemark",  defaultRemark);*/
        presenter.passTheValueToInteractor(
                defaultDateValue,
                defaultWorkerIdValue,
                defaultWorkerNameValue,
                defaultTaskCodeValue,
                defaultOrderNumberValue,
                defaultMaterialNumberValue,
                defaultMaterialDscValue,
                defaultOpnoValue,
                defaultOkQtyValue,
                defaultNgQtyValue,
                defaultWorkHourValue,
                defaultWorkMinValue,
                defaultRemark
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        initView();
        setTitleBar();
        initListener();
        presenter = new PrintPresenter(this, new PrintInteractor());
    }

    private void initListener() {
        textViewTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*日期文本*/
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogUtil.getDatePickerDialog(PrintActivity.this, PrintActivity.this);
            }
        });
        /*订单号编辑框*/
        editTextOrderNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 7) {
                    String finalOrderNumber = editTextOrderNumber.getText().toString();
                    /*给默认订单号 赋值*/
                    defaultOrderNumberValue = finalOrderNumber;
                    editTextOrderNumber.clearFocus();
                    //Toast.makeText(PrintActivity.this, finalOrderNumber, Toast.LENGTH_SHORT).show();
                    /*将订单号传入 presenter中的函数*/
                    presenter.passOrderNumberToInteractor(finalOrderNumber);
                }
            }
        });
        /*任务码下列框*/
        spinnerTaskCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = ((Spinner) parent);
                String taskCodeValue = (String) spinner.getItemAtPosition(position);
                lastTaskCodePosition = position;
                // String taskCodeValue = getResources().getStringArray(R.array.tasklist)[position];
                //给默认的任务代码赋值
                defaultTaskCodeValue = taskCodeValue;
                // Toast.makeText(PrintActivity.this, ""+taskCodeValue, Toast.LENGTH_SHORT).show();
                /*将任务码 传入 presenter中的函数*/
                presenter.passTaskCodeToInteractor(taskCodeValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /*小时下列框*/
        spinnerHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = ((Spinner) parent);
                String hourValue = ((String) spinner.getItemAtPosition(position));
                //String hourValue = getResources().getStringArray(R.array.hourlist)[position];
                defaultWorkHourValue = hourValue;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /*分钟下拉框*/
        spinnerMinute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = ((Spinner) parent);
                String minuteValue = ((String) spinner.getItemAtPosition(position));
                // String minuteValue = getResources().getStringArray(R.array.minutelist)[position];
                defaultWorkMinValue = minuteValue;


                Log.e("defaultDateValue", defaultDateValue);
                Log.e("defaultWorkerIdValue", defaultWorkerIdValue);
                Log.e("defaultWorkerNameValue", defaultWorkerNameValue);
                Log.e("defaultTaskCodeValue", defaultTaskCodeValue);
                Log.e("defaultOrderNumberValue", defaultOrderNumberValue);
                Log.e("defaultMaterialNumberVa", defaultMaterialNumberValue);
                Log.e("defaultMaterialDscValue", defaultMaterialDscValue);
                Log.e("defaultOpnoValue", defaultOpnoValue);
                Log.e("defaultOkQtyValue", defaultOkQtyValue);
                Log.e("defaultNgQtyValue", defaultNgQtyValue);
                Log.e("defaultWorkHourValue", defaultWorkHourValue);
                Log.e("defaultWorkMinValue", defaultWorkMinValue);
                Log.e("defaultRemark", defaultRemark);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnSaveToSQLServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isHourAndMinuteAllZero(defaultWorkHourValue, defaultWorkMinValue)) {


                    Toast.makeText(PrintActivity.this, "小时和分钟不能同时为0", Toast.LENGTH_SHORT).show();

                } else {
                    passTheValuesToSqlServer();
                    clearWidgetValues();


                }


                /*
                 */
                /*将录入的数据 保存到SqlServer的表中*//*
                Map<String, String> map = new HashMap<>();
                map.put("Pdate", defaultDateValue);
                map.put("Workid", defaultWorkerIdValue);
                map.put("Workname", defaultWorkerNameValue);
                map.put("TaskCode", defaultTaskCodeValue);
                map.put("OrderNo", defaultOrderNumberValue);
                map.put("Item", defaultMaterialNumberValue);
                map.put("Opno", defaultOpnoValue);
                map.put("OKQty", defaultOkQtyValue);
                map.put("NgQty", defaultNgQtyValue);
                map.put("WorkHour", defaultWorkHourValue);
                map.put("WorkMin", defaultWorkMinValue);
                map.put("Remark", defaultRemark);
*/
            }
        });
    }

    private void initView() {
        /*标题栏*/
        textViewTitleBack = findViewById(R.id.tv_titlebar_back);
        textViewTitleContent = findViewById(R.id.tv_titlebar_content);
        /*工号和姓名*/
        editTextWorkerID = findViewById(R.id.et_print_workId);
        textViewWorkerName = findViewById(R.id.tv_print_workName);
        setWorkerIDAndName();
        /*昨日的日期*/
        textViewDate = findViewById(R.id.tv_print_date);
        editTextDate = findViewById(R.id.et_print_date);
        setTheYesterdayDate();
        /*订单号 和 物料详情*/
        editTextOrderNumber = findViewById(R.id.et_print_order_number);
        textViewMaterialDsc = findViewById(R.id.tv_print_material_dsc);
        /*设为可见*/
        textViewMaterialDsc.setVisibility(View.VISIBLE);
        /*任务码和任务详情*/
        spinnerTaskCode = findViewById(R.id.spinner_print_taskCode);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tasklist, android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskCode.setAdapter(adapter);
        textViewPrintTaskDescription = findViewById(R.id.tv_print_taskDescription);
        /*小时下拉框  和  分钟下拉框 */
        spinnerHour = findViewById(R.id.spinner_print_hour);
        ArrayAdapter<CharSequence> adapterHour = ArrayAdapter.createFromResource(this,
                R.array.hourlist, android.R.layout.simple_spinner_dropdown_item);
        spinnerHour.setAdapter(adapterHour);
        spinnerMinute = findViewById(R.id.spinner_print_minute);
        ArrayAdapter<CharSequence> adapterMinute = ArrayAdapter.createFromResource(this,
                R.array.minutelist, android.R.layout.simple_spinner_dropdown_item);
        spinnerMinute.setAdapter(adapterMinute);
        /*保存按钮*/
        btnSaveToSQLServer = findViewById(R.id.btn_print_save);
    }

    @Override
    public void setTitleBar() {
        textViewTitleBack.setVisibility(View.VISIBLE);
        textViewTitleContent.setText(getString(R.string.print_title_content));
    }

    @Override
    public void setWorkerIDAndName() {
        SharedPreferences preferences = getSharedPreferences("main", Context.MODE_PRIVATE);
        textViewWorkerName.setVisibility(View.VISIBLE);
        String IDStr = preferences.getString("account", getString(R.string.default_account));
        String NameStr = preferences.getString("name", getString(R.string.default_name));
        editTextWorkerID.setText(IDStr);
        textViewWorkerName.setText(NameStr);
        //给默认的id赋值
        defaultWorkerIdValue = IDStr;
        //给默认的姓名赋值
        defaultWorkerNameValue = NameStr;
    }

    /*显示默认的前一天的日期*/
    @Override
    public void setTheYesterdayDate() {
        String yesterday = DatePickerDialogUtil.getYesterday(ConstantUtil.FourTwoTwoDateFormat);
        editTextDate.setText(yesterday);
        defaultDateValue = yesterday;
    }

    /*显示与订单号 相对应的物料*/
    @Override
    public void setMaterialNumber(List<MaterialBean> materialBeans) {
        /*将从printPresenter中的setMaterialNumber函数传回来的物料设置到文本框上*/
        textViewMaterialDsc.setText(materialBeans.get(0).getItemName());
        /*物料号显示后激活保存按钮*/
        btnSaveToSQLServer.setEnabled(true);
        defaultMaterialNumberValue = materialBeans.get(0).getItem();
        defaultMaterialDscValue = materialBeans.get(0).getItemName();
    }

    /*显示与订单号 相对应的新物料*/
    @Override
    public void setNewMaterialNumber(List<NewMaterialBean> newMaterialBeans) {

        // Toast.makeText(this, newMaterialBeans.get(0).getItemname(), Toast.LENGTH_SHORT).show();
        textViewMaterialDsc.setText(newMaterialBeans.get(0).getItemname());
        btnSaveToSQLServer.setEnabled(true);
        defaultMaterialNumberValue = newMaterialBeans.get(0).getItemname();


    }

    /*显示与任务代码 相对应的任务详情*/
    @Override
    public void setTaskDescription(List<TaskBean> taskBeans) {
        textViewPrintTaskDescription.setVisibility(View.VISIBLE);
        textViewPrintTaskDescription.setText(taskBeans.get(0).getName());
        /*如果返回的taskBeans的Relation字段的值是N
         * 就清空掉订单编辑框 和 itemname文本框中的内容
         * 并将默认值重置为“”空字符串
         *
         *
         * */

        if (taskBeans.get(0).getRelation().equals("N")) {
            editTextOrderNumber.setText("");
            editTextOrderNumber.setEnabled(false);
            textViewMaterialDsc.setText("");

            defaultOrderNumberValue = "";
            defaultMaterialDscValue = "";
           // tempContainer = "";


        } else {
            editTextOrderNumber.setEnabled(true);

        }



    }

    @Override
    public void setSaveInfoResultToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
       restoreFieldToDefaultValue();
        //clearWidgetValues();
    }

    @Override
    public void setOrderNumberIsNoExist(String failedMessage) {
        Toast.makeText(this, failedMessage, Toast.LENGTH_SHORT).show();
        //订单号不存在的情况下 禁用  保存按钮
        btnSaveToSQLServer.setEnabled(false);
    }
    /*日期发生更改后的回调函数*/

    /**
     * @param view       the picker associated with the dialog
     * @param year       the selected year
     * @param month      the selected month (0-11 for compatibility with
     *                   {@linkCalendar#MONTH})
     * @param dayOfMonth th selected day of the month (1-31, depending on
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateStr = DatePickerDialogUtil.getFormattedTheDateFromDatePickerDialog(year, month, dayOfMonth);
        editTextDate.setText(dateStr);
        defaultDateValue = dateStr;
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }




    /*新增一个函数  用来将字段的值 恢复到默认值*/

    public void restoreFieldToDefaultValue() {



                defaultOrderNumberValue = "";
                defaultMaterialNumberValue = "";
                defaultMaterialDscValue = "";
                defaultOpnoValue = "10";
                defaultOkQtyValue = "0";
                defaultNgQtyValue = "0";
                defaultWorkHourValue = "0";
                defaultWorkMinValue = "0";
                defaultRemark = "";






    }









    /*自定义一个函数   用来判断 工时 分钟下列框的值
     * 同时为零时
     * 禁用保存按钮
     *
     *
     * */

    public boolean isHourAndMinuteAllZero(String hourValue, String minuteValue) {

        if (hourValue.equals("0") && minuteValue.equals("0")) {


            return true;


        } else {
            return false;
        }


    }


}