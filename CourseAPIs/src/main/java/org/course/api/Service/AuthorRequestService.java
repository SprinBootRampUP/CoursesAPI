package org.course.api.Service;

import Utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.course.api.DTOS.CourseDTO;
import org.course.api.DTOS.LectureDTO;
import org.course.api.DTOS.SectionDTO;
import org.course.api.Entity.*;
import org.course.api.Repository.AuthorRequestRepository;
import org.course.api.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorRequestService {


    private  WebClient webClient;
    private  WebClient.Builder webClientBuilder;

//
//    @Autowired
//    public AuthorRequestService(WebClient.Builder webClientBuilder) {
//        this.webClientBuilder = webClientBuilder;
//        this.webClient = webClientBuilder.baseUrl("http://localhost:3006/oauth/api").build();
//
//    }


    @Autowired
    private AuthorRequestRepository authorRequestRepository;

    @Autowired
    private ObjectMapper objectMapper;

     public void authorRequest( Authentication authentication){
         Long  author_id = Utils.getIdFromToken(authentication);
         authorRequestRepository.save(new AuthorRequest(author_id));
     }

    public void addAuthorRole(Long id,Authentication authentication){

        String url = "/approve";
        String accessToken = Utils.getToken(authentication);
        this.webClient = webClientBuilder.baseUrl("http://localhost:3006/oauth/api").build();
        Mono<String> responseMono = webClient.put()
                .uri(uriBuilder -> uriBuilder.path(url).queryParam("id", id).build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class);

        responseMono.subscribe(
                response -> {
                    System.out.println("Author role approved: " + response);
                },
                error -> {
                        WebClientResponseException webClientError = (WebClientResponseException) error;
                        System.err.println("Error from OAuth server: " + webClientError.getStatusCode());
                        System.err.println("Error: " + error.getMessage());

                }
        );
    }


    public List<AuthorRequest> getAuthorRequests(){
       return   authorRequestRepository.findAll();
    }


}
