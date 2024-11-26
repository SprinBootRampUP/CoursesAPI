package org.course.api.Controller;

import jakarta.validation.ConstraintViolationException;
import org.course.api.DTOS.ApiResponse;
import org.course.api.DTOS.CourseDTO;
import org.course.api.Entity.ApprovalStatus;
import org.course.api.Entity.Course;
import org.course.api.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<ApiResponse> createCourse(@RequestBody CourseDTO courseDTO, Authentication authentication){
            courseService.CreateCourse(courseDTO , authentication);
            return  ResponseEntity.ok( new ApiResponse("Courses Created Successfully" , null));
    }



    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('AUTHOR')")
    public ResponseEntity<ApiResponse> getCourses(){
            List<CourseDTO> courses=courseService.getCourses();
            return  ResponseEntity.ok( new ApiResponse("Courses Fetched Successfully" , courses));
    }


    @GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getCoursesStatus( @RequestParam ApprovalStatus approvalStatus){
            List<CourseDTO> courses=courseService.getCoursesStatus( approvalStatus );
            return  ResponseEntity.ok( new ApiResponse("Courses Fetched Successfully" , courses));
    }



    @PutMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> approveCourse(@RequestParam Long courseId , ApprovalStatus approvalStatus){
            courseService.approveCourse(courseId ,approvalStatus);
            return  ResponseEntity.ok( new ApiResponse("Courses Approved" , null));
    }
}
