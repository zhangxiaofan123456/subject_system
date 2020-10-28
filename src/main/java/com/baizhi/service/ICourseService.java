package com.baizhi.service;

import com.baizhi.entity.Course;

import java.util.List;

public interface ICourseService {


    List<Course> getAll();

    void insert(Course course);

    void delete(Integer id);

    Course getCourseDetail(Integer id);

    void update(Course course);

    List<Course> getAllExpectCurrent(Integer id);

    List<Course> getAllNeedApprove();
}
