package org.course.api.Service;

import Utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.course.api.DTOS.CourseDTO;
import org.course.api.DTOS.LectureDTO;
import org.course.api.DTOS.SectionDTO;
import org.course.api.Entity.*;
import org.course.api.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {


    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void CreateCourse( CourseDTO courseDTO, Authentication authentication) {

        Course course = new Course();
        course.setCourseTitle(courseDTO.getCourseTitle());
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

        Long  author_id = Utils.getIdFromToken(authentication);


        course.setAuthor_id(author_id);
        course.setApproved(false);

        courseRepository.save(course);

    }


    @Transactional
    public  List<CourseDTO>  getCourses(){
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());

    }

    @Transactional
    public  List<CourseDTO>  getUnapprovedCourses(){
        List<Course> courses = courseRepository.findByApprovedFalse();

        return courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());

    }


    public  void approveCourse(Long courseId){
        courseRepository.updateApprovalStatusByCourseId( courseId);
     }


    private CourseDTO convertToCourseDTO(Course course) {
        return objectMapper.convertValue(course, CourseDTO.class);
    }

}
