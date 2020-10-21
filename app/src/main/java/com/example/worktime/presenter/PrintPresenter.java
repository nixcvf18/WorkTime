package com.example.worktime.presenter;
import com.example.worktime.interacor.PrintInteractor;
import com.example.worktime.interfaces.PrintView;
import com.example.worktime.model.MaterialBean;
import com.example.worktime.model.MaterialBeanBackup;
import com.example.worktime.model.NewMaterialBean;
import com.example.worktime.model.TaskBean;
import java.util.List;
public class PrintPresenter implements PrintInteractor.OnPrintCreateFinishListener {
    private PrintView printView;
    private PrintInteractor printInteractor;
    public PrintPresenter(PrintView printView, PrintInteractor printInteractor) {
        this.printView = printView;
        this.printInteractor = printInteractor;
    }
    public void onDestroy() {
        printView = null;
    }
    /*自定义函数  用于将activity中的请求参数
    * 传给 Interactor中进行请求
    * */
    public void passTheValueToInteractor(String date,
                                         String workerId,
                                         String workerName,
                                         String taskCode,
                                         String orderNumber,
                                         String materialNumber,
                                         String materialDsc,
                                         String opNO,
                                         String OKQty,
                                         String NGQty,
                                         String WorkHour,
                                         String WorkMin,
                                         String Remark
                                         ) {
        printInteractor.saveInfoToSQLSERVER( date,
                 workerId,
                workerName,
                 taskCode,
                orderNumber,
                materialNumber,
                 materialDsc,
                 opNO,
                OKQty,
                 NGQty,
                 WorkHour,
                 WorkMin,
                Remark,
                this);
    }
    /*自定义的函数  用来传递网络请求需要的参数给 负责业务逻辑的Interactor*/
    public void passOrderNumberToInteractor(String orderNumber) {
        /*将参数传入printInteractor类中的函数*/
        printInteractor.getMaterialFromWebService(orderNumber, this);
    }
    /*自定义的函数  用来传递网络请求需要的参数给 负责业务逻辑的Interactor*/
    public void passTaskCodeToInteractor(String taskCode) {
        printInteractor.getTaskDescriptionFromWebService(taskCode, this);
    }

    @Override
    public void onGetNewMaterialSuccess(List<NewMaterialBean> newMaterialBeans) {
        printView.setNewMaterialNumber(newMaterialBeans);

    }

    @Override
    public void onGetNewMaterialFailed(String failedMessage) {

        printView.setOrderNumberIsNoExist(failedMessage);


    }


    /*presenter实现Interactor接口中函数  用于将返回值传给Actiivty*/
    @Override
    public void onGetMaterialSuccess(List<MaterialBean> materialBeans) {
        //将从PrintInteractor的回调函数中传回来的数据显示到具体的View上
        printView.setMaterialNumber(materialBeans);
    }
    /*实现Interactor接口中函数*/
    @Override
    public void onGetMaterialFailed(String failedMessage) {
        printView.setOrderNumberIsNoExist(failedMessage);
    }
    /*实现Interactor接口中函数*/
    @Override
    public void onGetTaskSuccess(List<TaskBean> taskBeans) {
        printView.setTaskDescription(taskBeans);
    }
    /*实现Interactor接口中函数*/
    @Override
    public void onSaveInfoToSQLServerSuccess(String successMessage) {
        printView.setSaveInfoResultToast(successMessage);
    }
}
