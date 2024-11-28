//package org.course.api;
//
//import Utils.Utils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.course.api.Entity.AuthorRequest;
//import org.course.api.Repository.AuthorRequestRepository;
//import org.course.api.Service.AuthorRequestService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@ContextConfiguration(classes = TestConfig.class)
//public class AuthorRequestServiceTest {
//
//
//    @Mock
//    private AuthorRequestRepository authorRequestRepository;
//
//    @Mock
//    private WebClient.Builder webClientBuilder;
////
//////
//    @Mock
//    private WebClient webClient;
//
//
//
//    @Mock
//   private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;
////
//    @Mock
//    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;
////
//    @Mock
//    private WebClient.ResponseSpec responseSpec;
//
//    @Mock
//    private ObjectMapper objectMapper;
//
//    @InjectMocks
//    private AuthorRequestService authorRequestService;
//
//    @Mock
//    private Authentication authentication;
//
//    private AuthorRequest authorRequest;
//
//
//    private List<AuthorRequest> authorRequestList;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
//        when(webClientBuilder.build()).thenReturn(webClient);
//    }
//
//    @Test
//    void  testAuthorRequests(){
//
//        when(authorRequestRepository.save(any(AuthorRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        authorRequestList = List.of( new AuthorRequest(1L),  new AuthorRequest(1L)) ;
//
//        when(authorRequestRepository.findAll()).thenReturn( authorRequestList);
//
//      List<AuthorRequest> authorRequests = authorRequestService.authorRequests( );
//
//        System.out.println(authorRequests);
//    }
//
//
//    @Test
//    void  testAuthorRequest_Valid (){
//
//        MockedStatic<Utils> mockedStatic = mockStatic(Utils.class);
//        mockedStatic.when(() -> Utils.getIdFromToken(authentication))
//                .thenReturn(1L);
//
//        authorRequestService.authorRequest(authentication);
//        verify(authorRequestRepository, times(1)).save(any(AuthorRequest.class));
//
//    }
//    @Test
//    void testAddAuthorRole() {
//        Long id = 1L;
//        String accessToken = "testToken";
//
//        // Mock WebClient chain components
//        WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
//        requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
//      responseSpec = mock(WebClient.ResponseSpec.class);
//
//        // Mock static Utils method
//        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
//            mockedStatic.when(() -> Utils.getToken(authentication)).thenReturn(accessToken);
//
//            // Stubbing WebClient behavior
//            when(webClient.put()).thenReturn(requestBodyUriSpec);
//            when(requestBodyUriSpec.uri(anyString())).thenReturn((WebClient.RequestBodySpec) requestHeadersSpec);
//          //  when(requestHeadersSpec.header(anyString(), anyString())).thenReturn( requestHeadersSpec);
//            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
//            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Success"));
//
//            // Call the service method
//            authorRequestService.addAuthorRole(id, authentication);
//
//            // Verify the interactions
//            verify(responseSpec, times(1)).bodyToMono(String.class);
//        }
//    }
//
//
//
////    @Test
////    void testAddAuthorRole() {
////        // Mock authentication and utility method
////        Authentication authentication = mock(Authentication.class);
////        when(authentication.getPrincipal()).thenReturn(mock(Jwt.class));
////        when(Utils.getToken(authentication)).thenReturn("mock-token");
////
////        // Mock WebClient interactions
////        when(webClient.put()).thenReturn((WebClient.RequestBodyUriSpec) requestHeadersUriSpec);
////        when(requestHeadersUriSpec.uri((URI) any())).thenReturn(requestHeadersSpec);
////        when(requestHeadersSpec.header(anyString(), anyString())).thenReturn(requestHeadersSpec);
////        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
////        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Success"));
////
////        // Call the method
////        authorRequestService.addAuthorRole(1L, authentication);
////
////        // Verify the WebClient interactions
////        verify(webClient, times(1)).put();
////        verify(requestHeadersUriSpec, times(1)).uri((URI) any());
////        verify(requestHeadersSpec, times(1)).header(eq("Authorization"), eq("Bearer mock-token"));
////        verify(responseSpec, times(1)).bodyToMono(String.class);
////    }
//
//
//
//}
