package com.example.worktime;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.worktime.interacor.AmendInteractor;
import com.example.worktime.interfaces.AmendView;
import com.example.worktime.model.MaterialBean;
import com.example.worktime.model.TaskBean;
import com.example.worktime.presenter.AmendPresenter;
import java.util.List;
public class AmendActivity extends AppCompatActivity implements AmendView {
    AmendPresenter amendPresenter;
    /*将要保存进数据表中的数据默认值*/
    String
            tempContainer="",
            defaultIdSign = "",
            defaultDateValue = "",
            defaultWorkerIdValue = "",
            defaultWorkerNameValue = "",
            defaultTaskCodeValue = "",
            defaultOrderNumberValue = "",
            defaultMaterialNumberValue = "",
            defaultMaterialDscValue="",
            defaultOpnoValue = "10",
            defaultOkQtyValue = "10",
            defaultNgQtyValue = "0",
            defaultWorkHourValue = "0",
            defaultWorkMinValue = "0",
            defaultRemark = "";
    private TextView textViewTitleBack;
    private TextView textViewTitleContent;
    private EditText editTextWorkerID;
    private TextView textViewWorkerName;
    private EditText editTextDate;
    private EditText editTextOrderNumber;
    private TextView textViewMaterialDsc;
    private Spinner spinnerTaskCode;
    private Spinner spinnerHour;
    private Spinner spinnerMinute;
    private Button buttonSave;
    private ArrayAdapter<CharSequence> adapter;
    private ArrayAdapter<CharSequence> adapterHour;
    private ArrayAdapter<CharSequence> adapterMinute;
    private TextView textViewTaskDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        initView();

        initWidgetVisible();
        setItemValues();
        amendPresenter=new AmendPresenter(this,new AmendInteractor());
        initListener();
    }
    /*自定义的函数*/
    public void passTheValuesToSqlServer() {




 /*       Log.e("defaultDateValue", defaultDateValue);
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
        Log.e("defaultRemark",  defaultRemark);
*/

        defaultMaterialDscValue = tempContainer;
        Log.e("ItemName", defaultMaterialDscValue);

        amendPresenter.passTheValueToInteractor(
                defaultIdSign,
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
    private void initWidgetVisible() {
        textViewTitleBack.setVisibility(View.VISIBLE);
        textViewWorkerName.setVisibility(View.VISIBLE);
        textViewMaterialDsc.setVisibility(View.VISIBLE);
        textViewTaskDescription.setVisibility(View.VISIBLE);
    }
    private void initListener() {
        textViewTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                    defaultOrderNumberValue = editTextOrderNumber.getText().toString();
                    editTextOrderNumber.clearFocus();
                    amendPresenter.passOrderNumberToInteractor(defaultOrderNumberValue);

                }
            }
        });
        spinnerTaskCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = ((Spinner) parent);
                defaultTaskCodeValue = ((String) spinner.getItemAtPosition(position));
                amendPresenter.passTaskCodeToInteractor(defaultTaskCodeValue);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = ((Spinner) parent);
                defaultWorkHourValue = ((String) spinner.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerMinute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = ((Spinner) parent);
               defaultWorkMinValue = ((String) spinner.getItemAtPosition(position));






            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {














                passTheValuesToSqlServer();
            }
        });
    }
    private void setItemValues() {
        /*修该记录的唯一标识符*/
        defaultIdSign = getIntent().getStringExtra("IdSign");
        /*标题栏的内容 根据不同的界面取显示不同文字*/
        textViewTitleContent.setText(getString(R.string.revise_title_content));
        /*工号*/
        defaultWorkerIdValue = getIntent().getStringExtra("workerId");
        editTextWorkerID.setText(defaultWorkerIdValue);
        /*姓名*/
        defaultWorkerNameValue = getIntent().getStringExtra("workerName");
        textViewWorkerName.setText(defaultWorkerNameValue);
        /*日期*/
        defaultDateValue = getIntent().getStringExtra("date");
        editTextDate.setText(defaultDateValue);
        /*订单号*/
        defaultOrderNumberValue = getIntent().getStringExtra("orderNumber");
        editTextOrderNumber.setText(defaultOrderNumberValue);
        /*物料号*/
        defaultMaterialNumberValue = getIntent().getStringExtra("materialNumber");
        defaultMaterialDscValue = getIntent().getStringExtra("materialDsc");
        //textViewMaterialNumber.setText(defaultMaterialNumberValue);
        textViewMaterialDsc.setText(defaultMaterialDscValue);

        /*任务码*/
        defaultTaskCodeValue = getIntent().getStringExtra("spinnertaskCode");
        int taskPosition = adapter.getPosition(defaultTaskCodeValue);
        spinnerTaskCode.setSelection(taskPosition, true);
        /*任务详情*/
        textViewTaskDescription.setText(getIntent().getStringExtra("taskDescription"));
        /*工作小时*/
        defaultWorkHourValue = getIntent().getStringExtra("spinnerHour");
        int hourPosition = adapterHour.getPosition(defaultWorkHourValue);
        spinnerHour.setSelection(hourPosition, true);
        /*工作分钟*/
        defaultWorkMinValue = getIntent().getStringExtra("spinnerMinute");
        int minutePosition = adapterMinute.getPosition(defaultWorkMinValue);
        spinnerMinute.setSelection(minutePosition, true);
    }
    private void initView() {
        /*标题栏*/
        textViewTitleBack = findViewById(R.id.tv_titlebar_back);
        textViewTitleContent = findViewById(R.id.tv_titlebar_content);
        /*工号和姓名*/
        editTextWorkerID = findViewById(R.id.et_print_workId);
        textViewWorkerName = findViewById(R.id.tv_print_workName);
        //日期
        editTextDate = findViewById(R.id.et_print_date);
        //订单号 和 物料
        editTextOrderNumber = findViewById(R.id.et_print_order_number);
        textViewMaterialDsc = findViewById(R.id.tv_print_material_dsc);
        /*任务码 小时  分钟   下拉框*/
        spinnerTaskCode = findViewById(R.id.spinner_print_taskCode);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.tasklist, android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskCode.setAdapter(adapter);
        textViewTaskDescription = findViewById(R.id.tv_print_taskDescription);
        /*小时下拉框*/
        spinnerHour = findViewById(R.id.spinner_print_hour);
        adapterHour = ArrayAdapter.createFromResource(this,
                R.array.hourlist, android.R.layout.simple_spinner_dropdown_item);
        spinnerHour.setAdapter(adapterHour);
        /*分钟下拉框*/
        spinnerMinute = findViewById(R.id.spinner_print_minute);
        adapterMinute = ArrayAdapter.createFromResource(this,
                R.array.minutelist, android.R.layout.simple_spinner_dropdown_item);
        spinnerMinute.setAdapter(adapterMinute);
        buttonSave = findViewById(R.id.btn_print_save);
    }
    @Override
    public void setMaterialNumber(List<MaterialBean> materialBeans) {
        tempContainer=materialBeans.get(0).getItemName();
        textViewMaterialDsc.setText(tempContainer);


        //物料显示出来后激活 保存按钮
        buttonSave.setEnabled(true);

        defaultMaterialNumberValue=materialBeans.get(0).getItem();
        //textViewMaterialNumber.setText(defaultMaterialNumberValue);
        defaultMaterialDscValue = tempContainer;
    }
    @Override
    public void setTaskDescription(List<TaskBean> taskBeans) {
        textViewTaskDescription.setText(taskBeans.get(0).getName());


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
            tempContainer = "";


        } else {
            editTextOrderNumber.setEnabled(true);

        }







    }
    @Override
    public void setAmendInfoResultToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
       // finish();
    }
    @Override
    public void setOrderNumberIsNoExist(String failedMessage) {
        //订单号不存在的情况下 禁用保存按钮
        Toast.makeText(this, failedMessage, Toast.LENGTH_SHORT).show();
        buttonSave.setEnabled(false);
    }
    @Override
    protected void onDestroy() {
        amendPresenter.onDestroy();
        super.onDestroy();
    }
}