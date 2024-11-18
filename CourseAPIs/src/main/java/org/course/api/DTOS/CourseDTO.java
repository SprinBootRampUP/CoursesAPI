package org.course.api.DTOS;

import lombok.Data;
import org.course.api.Entity.CourseLevel;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {

    private String courseTitle;
    private String description;
    private CourseLevel courseLevel;
    private String price;
    private List<SectionDTO> sections;


}
