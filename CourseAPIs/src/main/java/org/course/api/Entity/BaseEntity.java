package org.course.api.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long  id;

   @CreatedDate
   private Date createdAt;

   @LastModifiedDate
   private Date updatedAt;

}
