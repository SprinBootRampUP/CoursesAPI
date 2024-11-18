package org.course.api.DTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceDTO {
    private String name;
    private Long size;
    private String url;
   // private LectureDTO lecture;
}
