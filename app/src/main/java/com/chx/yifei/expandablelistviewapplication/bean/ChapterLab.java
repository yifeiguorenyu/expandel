package com.chx.yifei.expandablelistviewapplication.bean;

import java.util.ArrayList;
import java.util.List;

public class ChapterLab {
    public static List<Chapter> geneneraMockDatas(){
        List<Chapter> datas = new ArrayList<Chapter>();

        Chapter root1 =new Chapter(1,"Android");
        Chapter root2 =new Chapter(2,"Ios");
        Chapter root3 =new Chapter(3,"Unity 3D");
        Chapter root4 =new Chapter(4,"Cocos2d-x");

        root1.addChild(1,"PullTOFresh");
        root1.addChild(2,"android 8.0通知栏解决方案");
        root1.addChild(3,"PullTOFresh与webView的js交互");
        root1.addChild(4,"Android与UnAutomater 20的入门实战");
        root1.addChild(5,"移动端音频视频入门");

        root2.addChild(6,"iOS开发之LeanCloud");
        root2.addChild(7,"ios开发之传感器");
        root2.addChild(8,"ios开发之 网络协议");
        root2.addChild(9,"ios开发之分享集成");
        root2.addChild(11,"ios开发之FTP上传");

        root3.addChild(12,"Unity 3D 翻牌游戏开发");
        root3.addChild(13,"Untiy 3D 基础之变体的Transform");
        root3.addChild(14,"dkjfsssss");
        root3.addChild(15,"Unity 3D 之游戏开发之脚本系统");
        root3.addChild(16,"Unity 3D 地形编辑器");

        root4.addChild(17,"Cocos-2d在的看法设为提高");
        root4.addChild(18,"Cocos-2d开发出题中拉法基 ");
        root4.addChild(19,"Cocos-2d按摩俄罗斯");
        root4.addChild(21,"Cocos-2d铁壳带着");
        root4.addChild(22,"新春特辑-Cocos抢红包");
datas.add(root1);
        datas.add(root2);
        datas.add(root3);
        datas.add(root4);
        return datas;
    }
}
