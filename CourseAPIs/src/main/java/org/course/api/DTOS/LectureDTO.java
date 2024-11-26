package org.course.api.DTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class LectureDTO {
    private String name;
   // private SectionDTO section;
    private ResourceDTO resource;

}
