package org.course.api.DTOS;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.course.api.Entity.ApprovalStatus;
import org.course.api.Entity.CourseLevel;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class CourseDTO {

  //  private Long id;
    private String title;
    private String description;
    private CourseLevel courseLevel;


  private Date createdAt;
    private BigDecimal price;
    private List<SectionDTO> sections;


}
