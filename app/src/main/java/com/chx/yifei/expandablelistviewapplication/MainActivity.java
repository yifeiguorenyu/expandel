package com.chx.yifei.expandablelistviewapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.chx.yifei.expandablelistviewapplication.adapter.ChapterAdapter;
import com.chx.yifei.expandablelistviewapplication.bean.Chapter;
import com.chx.yifei.expandablelistviewapplication.bean.ChapterLab;
import com.chx.yifei.expandablelistviewapplication.biz.ChapterBiz;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
 private ExpandableListView expandableListView;
  private ChapterAdapter chapterAdapter;
 private List<Chapter> mDatas = new ArrayList<Chapter>();
    public static final String Tag ="imooc-ex";
    private ChapterBiz mchapterBiz = new ChapterBiz();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       initViews();
        loadDatas();
    }

    private void loadDatas() {
       mchapterBiz.loadDatas(this, new ChapterBiz.CallBack() {
           @Override
           public void onSuccess(List<Chapter> chapters) {
               mDatas.addAll(chapters);
               Log.d(Tag, "onSuccess: "+chapterAdapter);
               chapterAdapter.notifyDataSetChanged();
           }

           @Override
           public void onFailed(Exception ex) {
                ex.printStackTrace();
           }
       }, false);
    }

    private void initViews() {
        expandableListView = findViewById(R.id.id_expandableListView);
      //  mDatas = ChapterLab.geneneraMockDatas();

        chapterAdapter =new ChapterAdapter(this,mDatas);
        expandableListView.setAdapter(chapterAdapter);
        //控制他只能打开一个组
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count =chapterAdapter.getGroupCount();
                for(int i = 0;i < count;i++){
                    if (i!=groupPosition){
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });


    }
}
