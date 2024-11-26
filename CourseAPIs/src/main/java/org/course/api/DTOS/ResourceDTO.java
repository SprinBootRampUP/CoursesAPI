package org.course.api.DTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class ResourceDTO {
    private String name;
    private Long size;
    private String url;
   // private LectureDTO lecture;
}
