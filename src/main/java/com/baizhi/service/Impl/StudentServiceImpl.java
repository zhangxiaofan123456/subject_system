package com.baizhi.service.Impl;

import com.baizhi.dao.StudentDao;
import com.baizhi.entity.Student;
import com.baizhi.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private StudentDao studentDao;


    @Override
    public List<Student> getAllExpectCurrent(Integer userId) {
        return studentDao.getAllExpectCurrent(userId);
    }

    @Override
    public void insert(Integer studentId, Integer courseId) {
        studentDao.insert(studentId,courseId);
    }
}
