package org.course.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.course.api.Controller.CourseController;
import org.course.api.DTOS.CourseDTO;
import org.course.api.DTOS.LectureDTO;
import org.course.api.DTOS.ResourceDTO;
import org.course.api.DTOS.SectionDTO;
import org.course.api.Entity.*;
import org.course.api.Service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER")
    void getCoursesReturnsOk() throws Exception {
        ResourceDTO resource =new ResourceDTO().setName("resource").setUrl("www.sharan.com").setSize(32L);
        LectureDTO lecture = new LectureDTO().setName("lecture1").setResource(resource);
        SectionDTO section = new SectionDTO().setName("section1").setOrderNumber("1").setLectures(Collections.singletonList(lecture));
        CourseDTO course1 = new CourseDTO().setCourseTitle("course1")
                .setCourseLevel(CourseLevel.BEGINNER)
                .setDescription("description")
                .setPrice("32,000")
                .setSections(Collections.singletonList(section));

        CourseDTO course2 = new CourseDTO().setCourseTitle("course2")
                .setCourseLevel(CourseLevel.BEGINNER)
                .setDescription("description")
                .setPrice("32,000")
                .setSections(Collections.singletonList(section));

        when(courseService.getCourses()).thenReturn(List.of(course1,course2));

        mockMvc.perform(get("/api/course")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Courses Fetched Successfully") )
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2));
    }



    @Test
    @WithMockUser(roles = "USER")
    void getCoursesReturnsForbidden() throws Exception {
        mockMvc.perform(get("/api/course"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = "AUTHOR")
    void createCoursesReturnsOKForAuthorRole() throws Exception {
        ResourceDTO resource =new ResourceDTO().setName("resource").setUrl("www.sharan.com").setSize(32L);
        LectureDTO lecture = new LectureDTO().setName("lecture1").setResource(resource);
        SectionDTO section = new SectionDTO().setName("section1").setOrderNumber("1").setLectures(Collections.singletonList(lecture));
        CourseDTO courseDTO = new CourseDTO().setCourseTitle("course1")
                .setCourseLevel(CourseLevel.BEGINNER)
                .setDescription("description")
                .setPrice("32,000")
                .setSections(Collections.singletonList(section));
       doNothing().when( courseService).CreateCourse(any(CourseDTO.class), any(Authentication.class) )  ;
        mockMvc.perform(post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.message").value("Courses Created Successfully") );
    }


    @Test
    @WithMockUser(roles = "USER")
    void createCourseReturnsForbiddenForUserRole() throws Exception {
        CourseDTO courseDTO = new CourseDTO().setCourseTitle("course1");
        mockMvc.perform(post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void courseApprovalReturnsOKForAdminRole() throws Exception {

       doNothing().when(courseService).approveCourse( 1L ,ApprovalStatus.APPROVED );

        mockMvc.perform(put("/api/course/approve")
                        .param( "courseId" , String.valueOf(1L))
                        .param( "approvalStatus" , String.valueOf(ApprovalStatus.APPROVED))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void courseApprovalReturnsForbiddenForUSERRole() throws Exception {
        mockMvc.perform(put("/api/course/approve")
                        .param( "courseId" , String.valueOf(1L))
                        .param( "approvalStatus" , String.valueOf(ApprovalStatus.APPROVED))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "AUTHOR")
    void courseApprovalReturnsForbiddenForAuthorRole() throws Exception {
        mockMvc.perform(put("/api/course/approve")
                        .param( "courseId" , String.valueOf(1L))
                        .param( "approvalStatus" , String.valueOf(ApprovalStatus.APPROVED))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getRejectedCoursesReturnsOk() throws Exception{
        ResourceDTO resource =new ResourceDTO().setName("resource").setUrl("www.sharan.com").setSize(32L);
        LectureDTO lecture = new LectureDTO().setName("lecture1").setResource(resource);
        SectionDTO section = new SectionDTO().setName("section1").setOrderNumber("1").setLectures(Collections.singletonList(lecture));
        CourseDTO course1 = new CourseDTO().setCourseTitle("course1")
                .setCourseLevel(CourseLevel.BEGINNER)
                .setDescription("description")
                .setPrice("32,000")
                .setSections(Collections.singletonList(section));

        CourseDTO course2 = new CourseDTO().setCourseTitle("course2")
                .setCourseLevel(CourseLevel.BEGINNER)
                .setDescription("description")
                .setPrice("32,000")
                .setSections(Collections.singletonList(section));

        when(courseService.getCoursesStatus(ApprovalStatus.REJECTED)).thenReturn(List.of(course1,course2));

        mockMvc.perform(get("/api/course/status")
                        .param( "approvalStatus" , String.valueOf(ApprovalStatus.REJECTED))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void getRejectedCoursesReturnsErrorForInvalidStatus() throws Exception{

        mockMvc.perform(get("/api/course/status")
                        .param( "approvalStatus" , "Invalid_status")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.error").value("Invalid Course Status"));
    }


}