package org.course.api;

import org.course.api.DTOS.CourseDTO;
import org.course.api.Entity.ApprovalStatus;
import org.course.api.Entity.AuthorRequest;
import org.course.api.Service.AuthorRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorRequestService authorRequestService;

    private List<AuthorRequest> authorRequests;

    @BeforeEach
    void setUp(){
        authorRequests = List.of(  new AuthorRequest( 1L) ,  new AuthorRequest( 2L));
    }


    @Test
    @WithMockUser(roles = "USER")
    void testRequestAuthorRole() throws Exception {

        doNothing().when( authorRequestService).authorRequest(any(Authentication.class) )  ;

        mockMvc.perform(post("/api/admin/request-author-role"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Author request submitted") )
                .andExpect(jsonPath("$.data").isEmpty());


    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void getAuthorRequestsReturnsOk() throws Exception {

        when(authorRequestService.getAuthorRequests()).thenReturn(authorRequests);
        mockMvc.perform(get("/api/admin/authorRequests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Pending Author approvals") )
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAuthorRequestsReturnsForbidden_USER() throws Exception {
        mockMvc.perform(get("/api/admin/authorRequests"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "AUTHOR")
    void getAuthorRequestsReturnsForbidden_AUTHOR() throws Exception {
        mockMvc.perform(get("/api/admin/authorRequests"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void putApproveAuthorReturnsOk() throws Exception {

        doNothing().when( authorRequestService).addAuthorRole( eq(1L),any(Authentication.class) );

        mockMvc.perform(put("/api/admin/approve")
                .param( "id" , String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Author role added") )
                .andExpect(jsonPath("$.data").isEmpty());

    }


}
