package org.course.api.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.course.api.Entity.ApprovalStatus;
import org.course.api.Entity.CourseLevel;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class CourseDTO {

    private String courseTitle;
    private String description;
    private CourseLevel courseLevel;
    private String price;
    private List<SectionDTO> sections;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ApprovalStatus approvalStatus;

}
