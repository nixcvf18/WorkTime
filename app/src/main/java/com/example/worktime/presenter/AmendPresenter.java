package com.example.worktime.presenter;
import com.example.worktime.interacor.AmendInteractor;
import com.example.worktime.interfaces.AmendView;
import com.example.worktime.model.MaterialBean;
import com.example.worktime.model.MaterialBeanBackup;
import com.example.worktime.model.TaskBean;
import java.util.List;
public class AmendPresenter implements AmendInteractor.AmendCreateFinishListener {
    private AmendView amendView;
    private AmendInteractor amendInteractor;
    public AmendPresenter(AmendView amendView, AmendInteractor amendInteractor) {
        this.amendView = amendView;
        this.amendInteractor = amendInteractor;
    }
    public void onDestroy() {
        amendView = null;
    }
    /*自定义函数  用来将修改后的内容  保存进SQL数据表中 */
    public void passTheValueToInteractor(String IdSign, String date,
                                         String workerId,
                                         String workerName,
                                         String taskCode,
                                         String orderNumber,
                                         String materialNumber,
                                         String materialDsc
                                         ,
                                         String opNO,
                                         String OKQty,
                                         String NGQty,
                                         String WorkHour,
                                         String WorkMin,
                                         String Remark
    ) {
        amendInteractor.amendInfoToSQLSERVER(
                IdSign,
                date,
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
                Remark,this
                );
    }
    /*自定义的函数  用来传递网络请求需要的参数给 负责业务逻辑的Interactor*/
    public void passOrderNumberToInteractor(String orderNumber) {
        /*将参数传入printInteractor类中的函数*/
       amendInteractor.getMaterialFromWebService(orderNumber, this);
    }
    /*自定义的函数  用来传递网络请求需要的参数给 负责业务逻辑的Interactor*/
    public void passTaskCodeToInteractor(String taskCode) {
        amendInteractor.getTaskDescriptionFromWebService(taskCode, this);
    }
    @Override
    public void onGetMaterialSuccess(List<MaterialBean> materialBeans) {
        amendView.setMaterialNumber(materialBeans);
    }
    @Override
    public void onGetMaterialFailed(String failedMessage) {
        amendView.setOrderNumberIsNoExist(failedMessage);
    }
    @Override
    public void onGetTaskSuccess(List<TaskBean> taskBeans) {
        amendView.setTaskDescription(taskBeans);
    }
    @Override
    public void onAmendInfoToSQLServerSuccess(String successMessage) {
        amendView.setAmendInfoResultToast(successMessage);
    }
}
