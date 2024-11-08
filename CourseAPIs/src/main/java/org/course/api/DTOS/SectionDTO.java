package org.course.api.DTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
 public  class SectionDTO {
    private Long id;
    private String name;
    private String orderNumber;
    private CourseDTO course;
    private List<LectureDTO> lectures;
}
