package com.baizhi.service;

import com.baizhi.entity.Student;

import java.util.List;

public interface IStudentService {
    List<Student> getAllExpectCurrent(Integer userId);

    void insert(Integer studentId, Integer courseId);
}
