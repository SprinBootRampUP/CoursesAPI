package org.course.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.course.api.Controller.CourseController;
import org.course.api.DTOS.CourseDTO;
import org.course.api.DTOS.LectureDTO;
import org.course.api.DTOS.ResourceDTO;
import org.course.api.DTOS.SectionDTO;
import org.course.api.Entity.*;
import org.course.api.Repository.CourseRepository;
import org.course.api.Service.CourseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseController courseController;



    @Autowired
    private ObjectMapper objectMapper;


    private  CourseDTO courseDTO1;
    private  CourseDTO courseDTO2;


    @BeforeEach
    void setUp(){

        ResourceDTO resource =new ResourceDTO().setName("resource").setUrl("www.sharan.com").setSize(32L);
        LectureDTO lecture = new LectureDTO().setName("lecture1").setResource(resource);
        SectionDTO section = new SectionDTO().setName("section1").setOrderNumber("1").setLectures(Collections.singletonList(lecture));
         courseDTO1 = new CourseDTO().setCourseTitle("course1")
                .setCourseLevel(CourseLevel.BEGINNER)
                .setDescription("description")
                .setPrice("32,000")
                .setSections(Collections.singletonList(section));

        courseDTO2 = new CourseDTO().setCourseTitle("course2")
                .setCourseLevel(CourseLevel.BEGINNER)
                .setDescription("description")
                .setPrice("32,000")
                .setSections(Collections.singletonList(section));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getCoursesReturnsOk() throws Exception {

        when(courseService.getCourses()).thenReturn(List.of(courseDTO1,courseDTO2));

        mockMvc.perform(get("/api/course"))
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

       doNothing().when( courseService).createCourse(any(CourseDTO.class), any(Authentication.class) )  ;
        mockMvc.perform(post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(courseDTO1)))
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

        when(courseService.getCoursesStatus(ApprovalStatus.REJECTED)).thenReturn(List.of(courseDTO1,courseDTO2));
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

    @Test
    @WithMockUser(roles = "AUTHOR")
    void testCreateCourseWithSameTitle() throws Exception {

       // doThrow(new SQLIntegrityConstraintViolationException("Duplicate course title")).when(courseService).createCourse(any(CourseDTO.class),any(Authentication.class));

       // doThrow(new DataIntegrityViolationException("Duplicate course title")).when(courseRepository).save(any(Course.class));
        //doThrow(new SQLIntegrityConstraintViolationException("Duplicate SQLIntegrityConstraintViolationException title")).when(courseRepository).save(any(Course.class));
        doThrow(new DataIntegrityViolationException("Duplicate entry")).when(courseService).createCourse(any(CourseDTO.class),any(Authentication.class));

        mockMvc.perform(post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(courseDTO1)))
                .andExpect(status().isNotAcceptable())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.error").value("Course with this title already submitted") );
               // .andExpect(status().isForbidden());

    }


    @Test
    @WithMockUser(roles = "AUTHOR")
    void testCreateCourse_withNullDescription() throws Exception {

      //  doThrow(new ConstraintViolationException(null))  .when(courseService).createCourse(any(CourseDTO.class),any(Authentication.class));

        doThrow(new ConstraintViolationException(
                "description",
                Set.of(
                        new MockConstraintViolation("description", "must not be null")
                )))
                .when(courseService).createCourse(any(CourseDTO.class), any(Authentication.class));

        mockMvc.perform(post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(courseDTO1)))
                .andExpect(status().isNotAcceptable())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.description").value("must not be null") );

    }


    @Test
    @WithMockUser(roles = "AUTHOR")
    void testCreateCourse_withInvalidCourseLevel() throws Exception {

          doThrow(new IllegalArgumentException("courseLevel")).when(courseService).createCourse(any(CourseDTO.class),any(Authentication.class));

        mockMvc.perform(post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(courseDTO1)))
                .andExpect(status().isNotAcceptable())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.error").value("Invalid Course Status") );

    }

    @Test
    @WithMockUser(roles = "AUTHOR")
    void testCreateCourse_withCourseTitleExceedsMaxLength() throws Exception {

        doThrow(new IllegalArgumentException("null")).when(courseService).createCourse(any(CourseDTO.class),any(Authentication.class));

        doThrow(new ConstraintViolationException(
                "courseTitle",
                Set.of(
                        new MockConstraintViolation("description", "Title must be less than or equal to 50 characters")
                )))
                .when(courseService).createCourse(any(CourseDTO.class), any(Authentication.class));


        mockMvc.perform(post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(courseDTO1)))
                .andExpect(status().isNotAcceptable())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.courseTitle").value("Title must be less than or equal to 50 characters") );

    }


}