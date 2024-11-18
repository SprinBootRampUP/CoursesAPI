package org.course.api.Controller;

import org.course.api.DTOS.ApiResponse;
import org.course.api.DTOS.CourseDTO;
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

        try{
            courseService.CreateCourse(courseDTO , authentication);

            return  ResponseEntity.ok( new ApiResponse("Courses Created Successfully" , null));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('AUTHOR')")
    public ResponseEntity<ApiResponse> getCourses(){
        try{
            List<CourseDTO> courses=courseService.getCourses();
            return  ResponseEntity.ok( new ApiResponse("Courses Fetched Successfully" , courses));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/unapproved")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getUnapprovedCourses(){
        try{
            List<CourseDTO> courses=courseService.getUnapprovedCourses();
            return  ResponseEntity.ok( new ApiResponse("Courses Fetched Successfully" , courses));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @PutMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> approvecourse(@RequestParam Long courseId ){
        try{
            courseService.approveCourse(courseId);
            return  ResponseEntity.ok( new ApiResponse("Courses Approved" , null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


}
