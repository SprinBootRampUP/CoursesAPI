package org.course.api.DTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
 public  class SectionDTO {
    private String name;
    private Long orderNumber;
   // private CourseDTO course;
    private List<LectureDTO> lectures;
}
