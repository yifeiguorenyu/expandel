package com.chx.yifei.expandablelistviewapplication.biz;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chx.yifei.expandablelistviewapplication.MainActivity;
import com.chx.yifei.expandablelistviewapplication.bean.Chapter;
import com.chx.yifei.expandablelistviewapplication.bean.ChapterItem;
import com.chx.yifei.expandablelistviewapplication.dao.ChapterDao;
import com.chx.yifei.expandablelistviewapplication.dao.ChapterDbHelper;
import com.chx.yifei.expandablelistviewapplication.util.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ChapterBiz  {
    private ChapterDao mChapterDato = new ChapterDao();
    public void loadDatas(final Context context, final CallBack callback, boolean useCache){
        AsyncTask<Boolean,Void,List<Chapter>> asyncTask = new AsyncTask<Boolean, Void, List<Chapter>>() {
           private Exception ex;
            @Override
            protected List<Chapter> doInBackground(Boolean... booleans) {
                boolean isUseCache = booleans[0];
                List<Chapter> chapterList = new ArrayList<Chapter>();
                try {
                    if(isUseCache){
                        //Todo load datas from db
                        List<Chapter> dataoFromDao =mChapterDato.loadFromDb(context);
                        Log.d(MainActivity.Tag,"数据库查询结果为"+dataoFromDao);
                        chapterList.addAll(dataoFromDao);
                    }
                    //Todo load from net
                    if(chapterList.isEmpty()){
                        List<Chapter> chapterListFromNet = loadFromNet(context);
                        chapterList.addAll(chapterListFromNet);
                        mChapterDato.insertToDb(context,chapterListFromNet);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                    this.ex = ex;
                }

                return chapterList;
            }

            @Override
            protected void onPostExecute(List<Chapter> chapters) {
               if(ex!=null){
                   callback.onFailed(ex);
                   return;
               }
               callback.onSuccess(chapters);

            }


        };
        asyncTask.execute(useCache);
    }

    private List<Chapter> loadFromNet(Context context) {
        String Url ="http://www.imooc.com/api/expandablelistview";
        List<Chapter> chapters=new ArrayList<>();
        //1 发请求获取 string数据
        String content =HttpUtils.doGet(Url);
        Log.d(MainActivity.Tag,"获取网络数据成功"+content);
        //2 content-->List<Chapter>
        if (content!=null){
           chapters  = parseContent(content);
        }

        return  chapters;

    }

    private List<Chapter> parseContent(String content) {

        List<Chapter> chapterList = new ArrayList<Chapter>();
        try {
            JSONObject root = new JSONObject(content);
            int errorCode =root.getInt("errorCode");
            if(errorCode==0){
                JSONArray data = root.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject chapterJsonObj = data.getJSONObject(i);
                    int id =chapterJsonObj.optInt("id");
                    String name =chapterJsonObj.optString("name");
                    Chapter chapter = new Chapter(id,name);
                    chapterList.add(chapter);
                    JSONArray childrenJsonArray=chapterJsonObj.optJSONArray("children");
                    if(childrenJsonArray!=null){
                        for (int j = 0; j <childrenJsonArray.length() ; j++) {
                            JSONObject childJsonObj = childrenJsonArray.getJSONObject(j);
                            int childId =childJsonObj.getInt("id");
                            int pi = childJsonObj.getInt("pid");
                            String childName =childJsonObj.getString("name");
                            ChapterItem chapterItem = new ChapterItem(childId,childName);
                            chapter.addChild(chapterItem);
                        }
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chapterList;

    }

    public static interface CallBack{
        void onSuccess(List<Chapter> chapters);
        void onFailed(Exception ex);
    }
}
