package org.course.api.Repository;

import org.course.api.Entity.AuthorRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRequestRepository extends JpaRepository<AuthorRequest, Long> {

}
