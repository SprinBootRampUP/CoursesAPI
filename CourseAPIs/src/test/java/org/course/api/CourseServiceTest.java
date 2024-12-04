package org.course.api;

import Utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.assertj.core.api.Assertions;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Course> criteriaQuery;

    @Mock
    private Root<Course> courseRoot;

    @Mock
    private TypedQuery<Course> typedQuery;

    @InjectMocks
    private CourseService courseService;


    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private Jwt jwt;

    private CourseDTO courseDTO1;
    private CourseDTO courseDTO2;
    private  Course course1;
    private  Course course2;

     private  List<Course> courses;

    @BeforeEach
    public void setUp(){

        ResourceDTO resource =new ResourceDTO().setName("resource").setUrl("www.sharan.com").setSize(32L);
        LectureDTO lecture = new LectureDTO().setName("lecture1").setResource(resource);
        SectionDTO section = new SectionDTO().setName("section1").setOrderNumber(1L).setLectures(Collections.singletonList(lecture));
        courseDTO1 = new CourseDTO().setTitle("course1")
                .setCourseLevel(CourseLevel.BEGINNER)
                .setDescription("description")
                .setPrice(BigDecimal.valueOf(32000))
                .setSections(Collections.singletonList(section));
        courseDTO2 = new CourseDTO().setTitle("course2")
                .setCourseLevel(CourseLevel.BEGINNER)
                .setDescription("description")
                .setPrice(BigDecimal.valueOf(32000))
                .setSections(Collections.singletonList(section));


        Resource cresource =new Resource().setName("resource").setUrl("www.sharan.com").setSize(32L);
        Lecture clecture = new Lecture().setName("lecture1").setResource(cresource);
        Section csection = new Section().setName("section1").setOrderNumber(1L).setLectures(Collections.singletonList(clecture));


         course1 = new Course().setTitle("course1")
                .setCourseLevel(CourseLevel.BEGINNER)
                .setDescription("description")
                .setPrice(BigDecimal.valueOf(32000))
                 .setApprovalStatus(ApprovalStatus.PENDING)
                .setSections(Collections.singletonList(csection));

         course2 = new Course().setTitle("course2")
                .setCourseLevel(CourseLevel.BEGINNER)
                .setDescription("description")
                .setPrice(BigDecimal.valueOf(32000))
                 .setApprovalStatus(ApprovalStatus.REJECTED)

                .setSections(Collections.singletonList(csection));

        courses = List.of( course1,course2);
    }


    @Test
    void testCourseCreation(){
        MockedStatic<Utils> mockedStatic = mockStatic(Utils.class);
        mockedStatic.when(() -> Utils.getIdFromToken(authentication))
                .thenReturn(1L);
        courseService.createCourse(courseDTO1,authentication);
       verify(courseRepository).save(any(Course.class));
    }

    @Test
    void  testGetCourses(){

      when(courseRepository.findAll()).thenReturn(courses);
     // when(objectMapper.convertValue( course1,eq(CourseDTO.class) )).thenReturn( courseDTO1 );
     // when(objectMapper.convertValue( course2,eq(CourseDTO.class) )).thenReturn( courseDTO2 );

        when(objectMapper.convertValue(any(Course.class), eq(CourseDTO.class)))
                .thenAnswer(invocation -> {
                    Course course = invocation.getArgument(0);
                    if ("course1".equals(course.getTitle())) {
                        return courseDTO1;
                    } else if ("course2".equals(course.getTitle())) {
                        return courseDTO2;
                    }
                    return null;
                });

      List<CourseDTO> result = courseService.getCourses();

        assertEquals( result.get(0).getTitle(), course1.getTitle() );
        assertEquals( result.get(1).getTitle(), course2.getTitle() );

        assertEquals(result.size() ,courses.size());
        System.out.println(result);


    }


    @Test
    void testGetCourseStatus_PENDING(){

        when(courseRepository.findByApprovalStatus(ApprovalStatus.PENDING)).thenReturn(Collections.singletonList(course1));
        when(objectMapper.convertValue( any(Course.class),eq(CourseDTO.class) )).thenReturn( courseDTO1 );
        List<CourseDTO> result = courseService.getCoursesStatus(ApprovalStatus.PENDING);
        assertEquals(result.size() ,1);
        assertEquals( result.get(0).getTitle(), course1.getTitle() );

    }

    @Test
    void testGetCourseStatus_REJECTED(){

        when(courseRepository.findByApprovalStatus(ApprovalStatus.REJECTED)).thenReturn(Collections.singletonList(course2));
        when(objectMapper.convertValue( any(Course.class),eq(CourseDTO.class) )).thenReturn( courseDTO2 );

        List<CourseDTO> result = courseService.getCoursesStatus(ApprovalStatus.REJECTED);
        assertEquals(result.size() ,1);
        assertEquals( result.get(0).getTitle(), course2.getTitle() );

    }

    @Test
   void testApproveCourse_APPROVE(){

        courseService.approveCourse(1L, ApprovalStatus.APPROVED);
        verify(courseRepository, times(1)).updateApprovalStatusByCourseId(eq(1L), eq(ApprovalStatus.APPROVED));
   }

    @Test
    void testApproveCourse_REJECTED(){

        courseService.approveCourse(1L, ApprovalStatus.REJECTED);
        verify(courseRepository, times(1)).updateApprovalStatusByCourseId(eq(1L), eq(ApprovalStatus.REJECTED));
    }

    @Test
    void  testSearchCourses_SUCCESS(){

        String searchTerm = "course1";
        CourseLevel courseLevel = CourseLevel.BEGINNER;
        BigDecimal priceFilter = BigDecimal.valueOf(50);
        PriceFilterCondition priceFilterCondition = PriceFilterCondition.LESS_THAN;
        int pageNo = 0;
        int pageCount = 10;
        SortBy sortBy = SortBy.DATE;
        //String sortOrder = "DESC";

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Course.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Course.class)).thenReturn(courseRoot);
        when(criteriaQuery.select(courseRoot)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);

        when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);

        when(typedQuery.getResultList()).thenReturn(courses);

        when(objectMapper.convertValue(any(Course.class), eq(CourseDTO.class)))
                .thenAnswer(invocation -> {
                    Course course = invocation.getArgument(0);
                    if ("course1".equals(course.getTitle())) {
                        return courseDTO1;
                    }
                    return null;
                });
        List<CourseDTO> result = courseService.searchCourses(searchTerm, courseLevel, priceFilter, priceFilterCondition, pageNo, pageCount, sortBy, SortingOrder.DESC);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("course1", result.get(0).getTitle());
    }

    @Test
    void  testSearchCourses_withInvalidCourseLevel(){

        String searchTerm = "course1";
        CourseLevel courseLevel = CourseLevel.BEGINNER;
        BigDecimal priceFilter = BigDecimal.valueOf(50);
        PriceFilterCondition priceFilterCondition = PriceFilterCondition.LESS_THAN;
        int pageNo = 0;
        int pageCount = 10;
        SortBy sortBy = SortBy.DATE;
        String sortOrder = "DESC";

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Course.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Course.class)).thenReturn(courseRoot);
        when(criteriaQuery.select(courseRoot)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);

        when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);

        when(typedQuery.getResultList()).thenReturn(courses);

        when(objectMapper.convertValue(any(Course.class), eq(CourseDTO.class)))
                .thenAnswer(invocation -> {
                    Course course = invocation.getArgument(0);
                    if ("course1".equals(course.getTitle())) {
                        return courseDTO1;
                    }
                    return null;
                });

        doThrow(new IllegalArgumentException("courseLevel")).when(courseService).searchCourses( searchTerm, eq(courseLevel), eq(priceFilter), eq(priceFilterCondition), eq(pageNo), eq(pageCount), eq(sortBy), eq(SortingOrder.ASC));

//
//        IllegalArgumentException exception=  assertThrows( IllegalArgumentException.class ,() ->{
//            courseService.searchCourses(searchTerm, courseLevel, priceFilter, priceFilterCondition, pageNo, pageCount, sortBy, sortOrder);
//
//        } );

        //System.out.println(exception.getMessage());
      //  List<CourseDTO> result = courseService.searchCourses(searchTerm, courseLevel, priceFilter, priceFilterCondition, pageNo, pageCount, sortBy, sortOrder);

//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("course1", result.get(0).getTitle());
    }


}
