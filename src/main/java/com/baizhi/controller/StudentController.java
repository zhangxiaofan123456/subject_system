package com.baizhi.controller;

import com.baizhi.annotation.NeedRole;
import com.baizhi.constant.RoleConstant;
import com.baizhi.constant.StatusConstant;
import com.baizhi.entity.Course;
import com.baizhi.entity.Response;
import com.baizhi.entity.Student;
import com.baizhi.service.ICourseService;
import com.baizhi.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private IStudentService iStudentService;
    @Autowired
    private ICourseService iCourseService;


    @RequestMapping("/selectCourse")
    @NeedRole(RoleConstant.ROLE_STUDENT)
    public Response selectCourse(Integer studentId,Integer courseId){
        SimpleDateFormat dateFormat=new SimpleDateFormat("HHmm");
        String format = dateFormat.format(new Date());
        int time = Integer.parseInt(format);
        if (time>800&&time<2100){
            List<Student> list=iStudentService.getAllExpectCurrent(studentId);
            for (int i = 0; i < list.size(); i++) {
                Student student = list.get(i);
                if (student.getCourseId().equals(courseId)){
                    return new Response(400,"不能重复选课",null);
                }else if (!student.getCourseId().equals(courseId)){
                    Course course=iCourseService.getCourseDetail(courseId);
                    List<Course> courses = iCourseService.getAllExpectCurrent(courseId);
                    for (int j = 0; j < courses.size(); j++) {
                        Course cours= courses.get(j);
                        if (cours.getSubjectId().equals(course.getSubjectId())&&cours.getTeacherName().equals(course.getTeacherName())){
                            return new Response(400,"同一个课程只能选一个老师",null);
                        }
                    }
                }
            }
            iStudentService.insert(studentId,courseId);
            return new Response(200,"11",list);


        }else {
            return new Response(400,"不在选课时间",null);
        }

    }




}
