package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-05-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程方法
     * @param file 文件路径
     * @param eduSubjectService 服务名称
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            //1 获取文件输入流
            InputStream in = file.getInputStream();
            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 课程分类列表（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        ArrayList<OneSubject> finalOneTwoList = new ArrayList<>();
        // 1获取一级分类的数据 parent_id = 0
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id",0);
        List<EduSubject> oneSubjectsList = baseMapper.selectList(oneWrapper);

        // 2获取二级分类的数据 parent_id != 0
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", 0);
        List<EduSubject> twoSubjectsList = baseMapper.selectList(twoWrapper);

        // 3封装一级分类
        for (int i = 0; i < oneSubjectsList.size(); i++) {
            EduSubject oneSubject = oneSubjectsList.get(i);
            OneSubject finalOneSubject = new OneSubject();
            BeanUtils.copyProperties(oneSubject, finalOneSubject);
            finalOneTwoList.add(finalOneSubject);
            // 4封装二级分类
            ArrayList<TwoSubject> finalTwoSubjectList = new ArrayList<>();
            for (int m = 0; m < twoSubjectsList.size(); m++) {
                EduSubject twoSubject = twoSubjectsList.get(m);
                if(twoSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubject finalTwoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject, finalTwoSubject);
                    finalTwoSubjectList.add(finalTwoSubject);
                }
            }
            finalOneSubject.setChildren(finalTwoSubjectList);
        }

        return finalOneTwoList;
    }
}
