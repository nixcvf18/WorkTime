package com.example.worktime.interfaces;
import com.example.worktime.model.MaterialBean;
import com.example.worktime.model.NewMaterialBean;
import com.example.worktime.model.TaskBean;
import java.util.List;
public interface PrintView {
    void setTitleBar();
    void setWorkerIDAndName();
    void setTheYesterdayDate();
    void setMaterialNumber(List<MaterialBean> materialBeans);

    void setNewMaterialNumber(List<NewMaterialBean> newMaterialBeans);
    void setTaskDescription(List<TaskBean> taskBeans);
    void setSaveInfoResultToast(String toastMessage);
    void setOrderNumberIsNoExist(String failedMessage);
}
