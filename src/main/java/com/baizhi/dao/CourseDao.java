package com.baizhi.dao;

import com.baizhi.entity.Course;

import java.util.List;

public interface CourseDao {
    List<Course> getAll();

    void insert(Course course);

    void delete(Integer id);

    Course getCourseDetail(Integer id);

    void update(Course course);

    List<Course> getAllExpectCurrent(Integer id);

    List<Course> getAllNeedApprove();
}
