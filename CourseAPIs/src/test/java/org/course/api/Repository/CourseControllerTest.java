package org.course.api.Repository;


import org.course.api.Controller.CourseController;
import org.course.api.Service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;
    @Mock
    private Authentication authentication;

    @Test
    void createCoursesReturnsForbidden() throws Exception {
        mockMvc.perform(get("/api/course")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USERs"))))
                .andExpect(status().isOk());
    }

}
   