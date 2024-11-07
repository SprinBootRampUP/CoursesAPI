package org.course.api.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Resource extends BaseEntity {


    private String name;
    private Long size;
    private String url;

    @OneToOne(mappedBy = "resource" )
    @JsonBackReference
    private Lecture lecture;


}
