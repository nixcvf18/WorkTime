package com.example.worktime.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.worktime.AmendActivity;
import com.example.worktime.R;
import com.example.worktime.model.TBlogBean;
import java.util.ArrayList;
import java.util.List;
public class ShowHistoryAdapter extends RecyclerView.Adapter<ShowHistoryAdapter.ViewHolder> {

    private final  int VIEW_TYPE_EMPTY=0;

    /*成员变量*/
    private Context context;
    private List<TBlogBean> tBlogBeans=new ArrayList<>();
    /*实例化集合*/
    public void setData(List<TBlogBean> tBlogBeans) {
        this.tBlogBeans = tBlogBeans;
    }
    public ShowHistoryAdapter(Context context) {
        this.context = context;

    }








    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

   /*     if (viewType != VIEW_TYPE_EMPTY) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_recyclerview_history, parent, false
            ));
    }*/
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recyclerview_history, parent, false
        ));

      /*  return new ShowHistoryAdapter.EmptyMessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recyclerview_history_empty, parent, false
        ));*/
    }
    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*调用自定义的函数bindMetaData()*/
        holder.bindMetaData(tBlogBeans.get(position));
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return tBlogBeans.size();
    }




    class EmptyMessageViewHolder extends  RecyclerView.ViewHolder{

        private TextView textViewEmptyMessage;

        public EmptyMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            /*返回View的子类*/
            textViewEmptyMessage = itemView.findViewById(R.id.tv_empty_message);


        }
    }











    /*声明内部类ViewHolder用来缓存View*/
    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutItemHistory;
        /*需要显示在条目上的内容*/
        TextView tv_date, tv_date_content,
                tv_order_number, tv_order_number_content, tv_material_content,
                tv_task_code, tv_task_code_content, tv_task_description,
                tv_work_hours, tv_work_hours_content,
                tv_work_minutes, tv_work_minutes_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            /*条目最外层布局*/
            layoutItemHistory = itemView.findViewById(R.id.linearOutest_item_history);
            /*第一行条目*/
            tv_date = itemView.findViewById(R.id.tv_history_item_recyclerview_date);
            tv_date_content = itemView.findViewById(R.id.tv_history_item_recyclerview_date_content);
            /*第二行条目*/
            tv_order_number = itemView.findViewById(R.id.tv_history_item_recyclerview_order_number);
            tv_order_number_content = itemView.findViewById(R.id.tv_history_item_recyclerview_order_number_content);
            tv_material_content = itemView.findViewById(R.id.tv_history_item_recyclerview_material_content);
            /*第三行条目*/
            tv_task_code = itemView.findViewById(R.id.tv_history_item_recyclerview_task_code);
            tv_task_code_content = itemView.findViewById(R.id.tv_history_item_recyclerview_task_code_content);
            tv_task_description = itemView.findViewById(R.id.tv_history_item_recyclerview_task_description);
            /*第四行条目*/
            tv_work_hours = itemView.findViewById(R.id.tv_history_item_recyclerview_work_hours);
            tv_work_hours_content = itemView.findViewById(R.id.tv_history_item_recyclerview_work_hours_content);
            /*第五行条目*/
            tv_work_minutes = itemView.findViewById(R.id.tv_history_item_recyclerview_work_minutes);
            tv_work_minutes_content = itemView.findViewById(R.id.tv_history_item_recyclerview_work_minutes_content);
        }
        void bindMetaData(final TBlogBean bean) {
            /*第一行  日期*/
            tv_date_content.setText( bean.getPdate().replace("0:00:00", ""));
            /*第二行  订单号   物料  */
            tv_order_number_content.setText( bean.getOrderNo());
            tv_material_content.setText(bean.getItemname());

            /*第三行   任务代码   任务描述*/
            tv_task_code_content.setText( bean.getTaskCode());
            tv_task_description.setText( bean.getName());
            /*第四行*/
            /*小时*/
            tv_work_hours_content.setText(bean.getWorkHour());
            /*第五行*/
            /*分钟*/
            tv_work_minutes_content.setText(bean.getWorkMin());

      layoutItemHistory.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(context, AmendActivity.class);
                /*修改记录 需要用到的  唯一标识符 */
              intent.putExtra("IdSign", bean.getNO());
              //intent.putExtra("vod_play_url", bean.getVod_play_url());
              intent.putExtra("workerId", bean.getWorkId());
              intent.putExtra("workerName", bean.getWorkName());
              intent.putExtra("date",bean.getPdate().replace("0:00:00", ""));
              intent.putExtra("orderNumber",bean.getOrderNo());
              intent.putExtra("materialNumber", bean.getItem());
              intent.putExtra("materialDsc", bean.getItemname());
              /*传递下拉框中的值*/
              intent.putExtra("spinnertaskCode", bean.getTaskCode());
              intent.putExtra("taskDescription", bean.getName());
              intent.putExtra("spinnerHour", bean.getWorkHour());
              intent.putExtra("spinnerMinute", bean.getWorkMin());
              context.startActivity(intent);
          }
      });
        }
    }
}
