package org.course.api.Controller;

import jakarta.validation.ConstraintViolationException;
import org.course.api.DTOS.ApiResponse;
import org.course.api.DTOS.CourseDTO;
import org.course.api.Entity.*;
import org.course.api.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<ApiResponse> createCourse(@RequestBody CourseDTO courseDTO, Authentication authentication){
            courseService.createCourse(courseDTO , authentication);
            return  ResponseEntity.ok( new ApiResponse("Courses Created Successfully" , null));
    }

    @GetMapping("/ping")
    public String ping(){
        return "HEllO form API server";
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



    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('AUTHOR')")
    public List<CourseDTO> searchCourses(
                                                     @RequestParam(defaultValue = " ") String searchTerm,
                                                     @RequestParam(defaultValue = "0") int pageNo,
                                                     @RequestParam(defaultValue = "25") int pageCount ,
                                                     @RequestParam(required = false) BigDecimal priceFilter ,
                                                     @RequestParam(required = false) CourseLevel courseLevel ,
                                                     @RequestParam(defaultValue = "LESS_THAN") PriceFilterCondition priceFilterCondition ,
                                                     @RequestParam(defaultValue = "TITLE") SortBy sortBy ,
                                                     @RequestParam(defaultValue = "ASC") SortingOrder sortOrder) {

    // List<Course> courses=courseService.searchCourses(searchTerm pageNo,pageCount,sortBy,sortOrder);
        List<CourseDTO> courses=courseService.searchCourses(searchTerm ,courseLevel,priceFilter,priceFilterCondition,pageNo,pageCount,sortBy,sortOrder);
       System.out.println( "page" +courses);
        return  courses;
    }

}
