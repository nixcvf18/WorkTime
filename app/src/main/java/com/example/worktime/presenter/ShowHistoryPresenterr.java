package com.example.worktime.presenter;
import com.example.worktime.interacor.ShowHistoryInteractor;
import com.example.worktime.interfaces.ShowHistoryView;
import com.example.worktime.model.TBlogBean;
import java.util.List;
public class ShowHistoryPresenterr implements ShowHistoryInteractor.OnHistoryCreateFinishListener {
    private ShowHistoryView showHistoryView;
    private ShowHistoryInteractor showHistoryInteractor;
    /*构造函数*/
    public ShowHistoryPresenterr(ShowHistoryView showHistoryView, ShowHistoryInteractor showHistoryInteractor) {
        this.showHistoryView = showHistoryView;
        this.showHistoryInteractor = showHistoryInteractor;
    }
    /*避免内存泄露*/
    public void onDestroy() {
        showHistoryView = null;
    }
    /**/
    public void passDateAndWorkId(String date, String userId) {
        showHistoryInteractor.getHistory(date, userId, this);
    }
    /*历史日期有数据记录的情况*/
    @Override
    public void onGetHistorySuccess(List<TBlogBean> tBlogBeans) {
        showHistoryView.setRecyclerViewItems(tBlogBeans);
    }
    /*历史日期无数据记录的情况*/
    @Override
    public void onGetHistoryFailed() {
        showHistoryView.setEmptyHistoryLayout();

    }
}
