package com.example.worktime.interfaces;
import com.example.worktime.model.TBlogBean;
import java.util.List;
public interface ShowHistoryView {
    void setRecyclerViewItems(List<TBlogBean> tBlogBeans);
    void  setTitleBar();
    void setEmptyHistoryLayout();
}
