package org.course.api.Controller;

import org.course.api.DTOS.ApiResponse;
import org.course.api.DTOS.CourseDTO;
import org.course.api.Entity.AuthorRequest;
import org.course.api.Repository.AuthorRequestRepository;
import org.course.api.Service.AuthorRequestService;
import org.course.api.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/admin")
public class UserController {

    @Autowired
    private AuthorRequestRepository authorRequestRepository;

    @Autowired
    private AuthorRequestService authorRequestService;

    @PostMapping("/requestauthorrole")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> requestAuthorRole(Authentication authentication){
        try{
            authorRequestService.authorRequest(authentication);
            return  ResponseEntity.ok( new ApiResponse("Author request submitted" , null));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ApiResponse( e.getMessage(),null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve")
    public ResponseEntity<ApiResponse> approveAuthor(@RequestParam Long id ,Authentication authentication){
        try{
            authorRequestService.addAuthorRole(id,authentication );
            return  ResponseEntity.ok( new ApiResponse("Author role added" , null));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ApiResponse( e.getMessage(),null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/authorRequests")
    public ResponseEntity<ApiResponse> authorRequests(){
        try{
            List<AuthorRequest> authorRequests= authorRequestService.authorRequests();
            return  ResponseEntity.ok( new ApiResponse("Pending Author approvals" , authorRequests));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ApiResponse( e.getMessage(),null));
        }
    }


}