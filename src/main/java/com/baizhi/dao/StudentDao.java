package com.baizhi.dao;

import com.baizhi.entity.Student;

import java.util.List;

public interface StudentDao {


    List<Student> getAllExpectCurrent(Integer userId);

    void insert(Integer studentId, Integer courseId);
}
