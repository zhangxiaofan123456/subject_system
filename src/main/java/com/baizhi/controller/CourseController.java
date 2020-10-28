package com.baizhi.controller;

import com.baizhi.annotation.NeedRole;
import com.baizhi.component.SelectException;
import com.baizhi.constant.ApproveConstant;
import com.baizhi.constant.OperationConstant;
import com.baizhi.constant.RoleConstant;
import com.baizhi.constant.StatusConstant;
import com.baizhi.entity.Course;
import com.baizhi.entity.Response;
import com.baizhi.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {


    @Autowired
    private ICourseService iCourseService;



    @GetMapping("/getAll")
    @NeedRole({RoleConstant.ROLE_MANAGER,RoleConstant.ROLE_STUDENT,RoleConstant.ROLE_TEACHER})
    public Response getAll(){
        List<Course> allCourses=iCourseService.getAll();
        if (allCourses==null&&allCourses.size()<0){
            throw new SelectException("查不到数据");
        }else {
            return new Response(200,"查询数据成功",allCourses);
        }
    }

    @GetMapping("/getCourseDetail")
    @NeedRole({RoleConstant.ROLE_MANAGER,RoleConstant.ROLE_STUDENT,RoleConstant.ROLE_TEACHER})
    public Response getCourseDetail(Integer id){
        Course course=iCourseService.getCourseDetail(id);
        if (course==null){
            throw new SelectException("查不到数据");
        }else {
            return new Response(200,"查询数据成功",course);
        }
    }



    @PostMapping("/managerInsert")
    @NeedRole({RoleConstant.ROLE_MANAGER})
    public Response managerInsert(@RequestBody @Valid Course course, BindingResult bindingResult){
        List<Course> courses = iCourseService.getAll();
        for (int i = 0; i < courses.size(); i++) {
            Course cours= courses.get(i);
            if (cours.getSubjectId().equals(course.getSubjectId())&&cours.getTeacherName().equals(course.getTeacherName())&&cours.getDayOfWeek().equals(course.getDayOfWeek())&&cours.getLessonNo().equals(course.getLessonNo())){
                return new Response(400,"主任增加课程与现有课程重复",null);
            }
            if (i==courses.size()-1){
                course.setStatus(StatusConstant.COURSE_REVIEW_COMPLETED);
                iCourseService.insert(course);
                return new Response(200,"主任增加课程成功",null);
            }
        }
        return new Response(200,"过程结束",null);

    }
    @PostMapping("/managerDelete")
    @NeedRole({RoleConstant.ROLE_MANAGER})
    public Response managerDelete(Integer id){
        iCourseService.delete(id);
        return new Response(200,"删除成功",null);
    }






    @PostMapping("/managerUpdate")
    @NeedRole({RoleConstant.ROLE_MANAGER})
    public Response managerUpdate(@RequestBody @Valid Course course, BindingResult bindingResult){
        List<Course> courses = iCourseService.getAllExpectCurrent(course.getId());
        for (int i = 0; i < courses.size(); i++) {
            Course cours= courses.get(i);
            if (cours.getSubjectId().equals(course.getSubjectId())&&cours.getTeacherName().equals(course.getTeacherName())&&cours.getDayOfWeek().equals(course.getDayOfWeek())&&cours.getLessonNo().equals(course.getLessonNo())){
                return new Response(400,"主任更新课程与现有课程重复",null);
            }
            if (i==courses.size()-1){
                course.setStatus(StatusConstant.COURSE_REVIEW_COMPLETED);
                iCourseService.update(course);
                return new Response(200,"主任更新课程成功",null);
            }
        }
        return new Response(200,"过程结束",null);
    }

    @GetMapping("/needApprove")
    @NeedRole({RoleConstant.ROLE_MANAGER})
    public Response allNeedApprove(){
        List<Course> allNeedApprove = iCourseService.getAllNeedApprove();
        if (allNeedApprove!=null&&allNeedApprove.size()>0){
            return new Response(200,"查询数据成功",allNeedApprove);
        }else {
            throw new SelectException("没有需要审批的课程");
        }
    }

    @PostMapping("/managerApprove")
    @NeedRole({RoleConstant.ROLE_MANAGER})
    public Response managerApprove(Integer id,Integer agreeOrNot){
        Course course=iCourseService.getCourseDetail(id);
        if (course.getOperation()==OperationConstant.OPERATION_C){
            if (agreeOrNot== ApproveConstant.APPROVE_AGREE){
                course.setOperation(null);
                course.setStatus(StatusConstant.COURSE_REVIEW_COMPLETED);
                iCourseService.update(course);
                return new Response(200,"审批通过",null);
            }else {
                iCourseService.delete(id);
                return new Response(200,"审批不通过",null);
            }
        }else if (course.getOperation()==OperationConstant.OPERATION_D){
            if (agreeOrNot== ApproveConstant.APPROVE_AGREE){
                iCourseService.delete(id);
                return new Response(200,"审批通过",null);
            }else {
                course.setOperation(null);
                course.setStatus(StatusConstant.COURSE_REVIEW_COMPLETED);
                iCourseService.update(course);
                return new Response(200,"审批不通过",null);
            }
        }else if (course.getOperation()==OperationConstant.OPERATION_U){
            if (agreeOrNot== ApproveConstant.APPROVE_AGREE){
                Course temp=iCourseService.getCourseDetail(id);
                Integer originId = temp.getOriginId();
                temp.setStatus(StatusConstant.COURSE_REVIEW_COMPLETED);
                temp.setOperation(null);
                temp.setOriginId(null);
                temp.setId(originId);
                iCourseService.update(temp);
                iCourseService.delete(id);
                return new Response(200,"审批通过",null);
            }else {
                iCourseService.delete(id);
                return new Response(200,"审批不通过",null);
            }
        }else {
            return null;
        }


    }









    @PostMapping("/teacherInsert")
    @NeedRole({RoleConstant.ROLE_TEACHER})
    public Response teacherInsert(@RequestBody @Valid Course course, BindingResult bindingResult){
        List<Course> courses = iCourseService.getAll();
        for (int i = 0; i < courses.size(); i++) {
            Course cours= courses.get(i);
            if (cours.getSubjectId().equals(course.getSubjectId())&&cours.getTeacherName().equals(course.getTeacherName())&&cours.getDayOfWeek().equals(course.getDayOfWeek())&&cours.getLessonNo().equals(course.getLessonNo())){
                return new Response(400,"老师增加课程与现有课程重复",null);
            }
            if (i==courses.size()-1){
                course.setStatus(StatusConstant.COURSE_UNDER_REVIEW);
                course.setOperation(OperationConstant.OPERATION_C);
                iCourseService.insert(course);
                return new Response(200,"老师增加课程成功,等待审批",null);
            }
        }
        return new Response(200,"过程结束",null);
    }

    @PostMapping("/teacherDelete")
    @NeedRole({RoleConstant.ROLE_TEACHER})
    public Response teacherDelete(Integer id){
        Course course=iCourseService.getCourseDetail(id);
        course.setStatus(StatusConstant.COURSE_UNDER_REVIEW);
        course.setOperation(OperationConstant.OPERATION_D);
        iCourseService.update(course);
        return new Response(200,"老师删除课程成功，等待审批",null);
    }

    @PostMapping("/teacherUpdate")
    @NeedRole({RoleConstant.ROLE_TEACHER})
    public Response teacherUpdate(@RequestBody @Valid Course course, BindingResult bindingResult){
        List<Course> courses = iCourseService.getAllExpectCurrent(course.getId());
        for (int i = 0; i < courses.size(); i++) {
            Course cours= courses.get(i);
            if (cours.getSubjectId().equals(course.getSubjectId())&&cours.getTeacherName().equals(course.getTeacherName())&&cours.getDayOfWeek().equals(course.getDayOfWeek())&&cours.getLessonNo().equals(course.getLessonNo())){
                return new Response(400,"老师更新课程与现有课程重复",null);
            }
            if (i==courses.size()-1){
                course.setStatus(StatusConstant.COURSE_UNDER_REVIEW);
                course.setOperation(OperationConstant.OPERATION_U);
                course.setOriginId(course.getId());
                iCourseService.insert(course);
                return new Response(200,"老师更新课程成功",null);
            }
        }
        return new Response(200,"过程结束",null);
    }













}
