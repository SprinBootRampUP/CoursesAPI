package org.course.api.Service;

import Utils.Utils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.course.api.DTOS.CourseDTO;
import org.course.api.DTOS.LectureDTO;
import org.course.api.DTOS.SectionDTO;
import org.course.api.Entity.*;
import org.course.api.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

@Service
public class CourseService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void createCourse( CourseDTO courseDTO, Authentication authentication) {

        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
      //  course.setCourseTitle(courseDTO.getCourseTitle()).setDescription("desc");

        course.setDescription(courseDTO.getDescription());
        course.setCourseLevel(courseDTO.getCourseLevel());
        course.setPrice(courseDTO.getPrice());
        List<Section> sections = new ArrayList<>();

        for( SectionDTO sectionDTO : courseDTO.getSections()){

            Section section = new Section();
            section.setName(sectionDTO.getName());
            section.setOrderNumber(sectionDTO.getOrderNumber());
            section.setCourse(course);
            List<Lecture> lectures = new ArrayList<>();

            for( LectureDTO lectureDTO : sectionDTO.getLectures()){
                Lecture lecture = new Lecture();
                lecture.setName(lectureDTO.getName());
                Resource resource = new Resource();
                resource.setName(lectureDTO.getResource().getName());
                resource.setUrl(lectureDTO.getResource().getUrl());
                resource.setSize(lectureDTO.getResource().getSize());

                lecture.setResource(resource);
                lecture.setSection(section);
                lectures.add(lecture);
            }
            section.setLectures(lectures);
            sections.add(section);

        }

        course.setSections(sections);
        course.setApprovalStatus(ApprovalStatus.APPROVED);
        Long  author_id = Utils.getIdFromToken(authentication);
        course.setAuthor_id(author_id);
        courseRepository.save(course);
    }


    @Transactional
    public  List<CourseDTO>  getCourses(){
        List<Course> courses = courseRepository.findByApprovalStatus(ApprovalStatus.APPROVED);
        return courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public  List<CourseDTO>  getCoursesStatus(ApprovalStatus approvalStatus){
        List<Course> courses = courseRepository.findByApprovalStatus(approvalStatus);

        return courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());

    }


    public  void approveCourse(Long courseId , ApprovalStatus approvalStatus){
        courseRepository.updateApprovalStatusByCourseId( courseId ,approvalStatus);
     }


    public CourseDTO convertToCourseDTO(Course course) {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper.convertValue(course, CourseDTO.class);
    }


    @Transactional
    public List<CourseDTO> searchCourses(String searchTerm ,CourseLevel courseLevel,BigDecimal priceFilter , PriceFilterCondition  priceFilterCondition , int pageNo, int pageCount , SortBy sortBy, SortingOrder sortOrder){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = cb.createQuery(Course.class);
        Root<Course> courseRoot = query.from(Course.class);

        List<Predicate> predicates = new ArrayList<>();



        if (searchTerm != null && !searchTerm.isEmpty() && !searchTerm.equals(" ")) {
            predicates.add(cb.like(cb.lower(courseRoot.get("title")), "%" + searchTerm.toLowerCase() + "%"));

        }

        if(priceFilter != null){

            if(priceFilterCondition.name().equalsIgnoreCase("LESS_THAN")){
                predicates.add(cb.lessThan(courseRoot.get("price"), priceFilter));
            } else {
                predicates.add(cb.greaterThan(courseRoot.get("price"), priceFilter));
            }
        }

        if(courseLevel!=null){
            predicates.add(cb.equal(courseRoot.get("courseLevel"),courseLevel   ));
        }

        predicates.add(cb.equal(courseRoot.get("approvalStatus"), ApprovalStatus.APPROVED));

       query.select(courseRoot)
                .where(
                        cb.and(
                                predicates.toArray(new Predicate[0])
                        )
                );
        Sort.Direction sortdirection= sortOrder.name().equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;

        String sortColumn = getSortColumn(sortBy);

        query.orderBy(sortdirection == Sort.Direction.ASC
                ? cb.asc(courseRoot.get(sortColumn))
                : cb.desc(courseRoot.get( sortColumn )));

        Pageable pageable = PageRequest.of(pageNo,pageCount ,Sort.by(sortdirection,sortColumn));

        List<Course> courses = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());


    }



    private String getSortColumn(SortBy sortBy) {
        return switch (sortBy) {
            case DATE -> "createdAt";
            case PRICE -> "price";
            default -> "title";
        };

    }


    }
