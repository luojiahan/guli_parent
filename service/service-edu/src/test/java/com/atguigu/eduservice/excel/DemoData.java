package com.atguigu.eduservice.excel;

import com.alibaba.excel.annotation.ExcelProperty;

public class DemoData {
    //设置表头名称
    @ExcelProperty(value = "学生编号", index = 0)
    private int sno;

    //设置表头名称
    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "DemoData{" +
                "sno=" + sno +
                ", sname='" + sname + '\'' +
                '}';
    }
}
