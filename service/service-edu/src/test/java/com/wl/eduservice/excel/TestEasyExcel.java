package com.wl.eduservice.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        //实现excel写操作
        //1.设置写入文件夹地址和excel文件名称他
        String filaname = "C:\\Users\\wanglei\\Desktop\\write.xlsx";
        //2.调用easyexcel里的方法实现写操作
        EasyExcel.write(filaname, DemoData.class).sheet("学生列表").doWrite(getData());
    }
    //创建方法返回list集合
    private static List<DemoData> getData(){
        ArrayList<DemoData> list= new ArrayList<>();
        for (int i = 0;i<10;i++){
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("wanglei"+i);
            list.add(demoData);
        }
        return list;
    }

    @Test
    public void read(){
        String filename = "C:\\Users\\wanglei\\Desktop\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }
}
