package org.course.api.Controller;

import org.course.api.Service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@WebMvcTest(SecuredController.class)
class CourseControllerTest {

@Autowired
    private MockMvc mockMvc;

@Mock
private CourseService courseService;


@Mock
private Authentication authentication;

@InjectMocks
private CourseController courseController;


void setUp(){
    mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
}

    @Test
    @WithMockUser(roles = "AUTHOR") // Simulate a user with AUTHOR role
    void createCourse() {



    }

    @Test
    void getCourses() {
    }

    @Test
    void getCoursesStatus() {
    }

    @Test
    void approvecourse() {
    }
}