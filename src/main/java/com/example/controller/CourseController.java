package com.example.controller;

import com.example.annotation.Autowired;
import com.example.annotation.Controller;
import com.example.service.CourseService;

@Controller
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void createCourse() {
        courseService.createCourse();
    }
}
