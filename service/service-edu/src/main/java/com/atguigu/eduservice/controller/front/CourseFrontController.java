package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.orderVo.CourseWebVoOrder;
import com.atguigu.eduservice.client.OrderClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontVo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "前台课程管理接口")
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "CourseFrontVo", value = "查询对象", required = false)
            @RequestBody(required = false) CourseFrontVo courseFrontVo){

        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontInfo(pageCourse, courseFrontVo);

        return R.ok().data(map);
    }

    // 课程详情的方法
    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId,
            HttpServletRequest request){
        // 查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        // 根据课程id和用户id判断当前用户是否已支付
        boolean buyCourse = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

        // 根据课程id查询章节和小节
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("course", courseWebVo).data("chapterVoList",  chapterVoList).data("isbuy",buyCourse);
    }

    // 根据课程id查询课程信息
    @ApiOperation(value = "根据课程id查询课程信息")
    @GetMapping("getCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getCourseInfoOrder(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {
        CourseWebVo baseCourseInfo = courseService.getBaseCourseInfo(courseId);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(baseCourseInfo, courseWebVoOrder);
        return courseWebVoOrder;
    }
}
