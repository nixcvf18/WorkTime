package com.example.worktime.util;
import com.example.worktime.model.MaterialBean;
import com.example.worktime.model.MaterialResult;
import com.example.worktime.model.NewMaterialBean;
import com.example.worktime.model.NewMaterialResult;
import com.example.worktime.model.TBlogBean;
import com.example.worktime.model.TBlogResult;
import com.example.worktime.model.TaskBean;
import com.example.worktime.model.TaskResult;
import com.google.gson.Gson;
import java.util.List;
public class GsonUtil {
    private static  GsonUtil  gsonUtil;
    private GsonUtil() {
    }
    public static GsonUtil getInstance() {
        if (gsonUtil == null) {
           gsonUtil=new GsonUtil();
        }
        return gsonUtil;
    }
    //获取新物料

    public List<NewMaterialBean> getNewMaterialList(String json) {
        Gson gson=new Gson();
        NewMaterialResult result = gson.fromJson(json, NewMaterialResult.class);

        List<NewMaterialBean> newMaterialBeanList = result.getT_blog();

        return newMaterialBeanList;



    }



    //获取物料
    public List<MaterialBean> getMaterialList(String json) {
        Gson gson=new Gson();
        MaterialResult result = gson.fromJson(json, MaterialResult.class);
        List<MaterialBean> materialBeanList = result.getT_blog();
        return materialBeanList;
    }
    //获取任务
    public List<TaskBean> getTasklogList(String json) {
        Gson gson=new Gson();
        TaskResult result = gson.fromJson(json, TaskResult.class);
        List<TaskBean> taskBeanList = result.getT_blog();
        return taskBeanList;
    }
    public List<TBlogBean> getTBlogList(String json) {
        Gson  gson=new Gson();
        TBlogResult result = gson.fromJson(json, TBlogResult.class);
        List<TBlogBean> tBlogBeanList = result.getT_blog();
        return tBlogBeanList;
    }
}
