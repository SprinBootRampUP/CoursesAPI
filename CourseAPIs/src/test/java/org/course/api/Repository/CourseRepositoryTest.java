package org.course.api.Repository;

import org.course.api.Entity.ApprovalStatus;
import org.course.api.Entity.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CourseRepositoryTest {

    @Mock
    CourseRepository courseRepository;

    @Test
    void updateApprovalStatusByCourseId() {
    }

    @Test
    void findByApprovalStatus() {

        List<Course> courses= courseRepository.findByApprovalStatus(ApprovalStatus.PENDING);

    }

    @Test
    void saveCourse(){
        Course s = new Course();
        courseRepository.save(  s);
    }

}