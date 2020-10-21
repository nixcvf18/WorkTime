package com.example.worktime.interfaces;
import com.example.worktime.model.MaterialBean;
import com.example.worktime.model.TaskBean;
import java.util.List;
public interface AmendView {
    void setMaterialNumber(List<MaterialBean> materialBeans);
    void setTaskDescription(List<TaskBean> taskBeans);
    void setAmendInfoResultToast(String toastMessage);
    void setOrderNumberIsNoExist(String failedMessage);
}
