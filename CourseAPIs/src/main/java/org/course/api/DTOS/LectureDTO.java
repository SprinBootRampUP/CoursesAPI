package org.course.api.DTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LectureDTO {
    private Long id;
    private String name;
    private SectionDTO section;
    private ResourceDTO resource;

}
