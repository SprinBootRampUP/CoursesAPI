package org.course.api.Repository;

import jakarta.transaction.Transactional;
import org.course.api.Entity.ApprovalStatus;
import org.course.api.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Course c SET c.approvalStatus = :approvalStatus WHERE c.id = :courseId")
    void updateApprovalStatusByCourseId(@Param("courseId") Long courseId ,@Param("approvalStatus") ApprovalStatus approvalStatus);


   // List<Course>  findByApprovedFalse();


    List<Course> findByApprovalStatus(ApprovalStatus approvalStatus);




}
