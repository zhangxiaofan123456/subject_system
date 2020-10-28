package com.baizhi.service.Impl;

import com.baizhi.dao.CourseDao;
import com.baizhi.entity.Course;
import com.baizhi.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl  implements ICourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public List<Course> getAll() {


        return courseDao.getAll();
    }

    @Override
    public void insert(Course course) {
        courseDao.insert(course);
    }

    @Override
    public void delete(Integer id) {
        courseDao.delete(id);
    }

    @Override
    public Course getCourseDetail(Integer id) {
        return courseDao.getCourseDetail(id);
    }

    @Override
    public void update(Course course) {
        courseDao.update(course);
    }

    @Override
    public List<Course> getAllExpectCurrent(Integer id) {
        return courseDao.getAllExpectCurrent(id);
    }

    @Override
    public List<Course> getAllNeedApprove() {
        return courseDao.getAllNeedApprove();
    }
}
