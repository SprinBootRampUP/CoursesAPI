package org.course.api.Repository;

import org.course.api.Entity.ApprovalStatus;
import org.course.api.Entity.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/test_db",
        " spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
        " spring.datasource.username=root",
        " spring.datasource.password=root",
        "spring.jpa.hibernate.ddl-auto=create",
        " spring.jpa.show-sql=true"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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