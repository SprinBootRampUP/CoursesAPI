package org.course.api.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@Table(name = "course")
public class Course extends  BaseEntity {

    @Column(unique = true)
    @Basic(optional = false)
    @Size(max = 50, message = "Title must be less than or equal to 50 characters.")
    private String courseTitle;

    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CourseLevel courseLevel;

    @NotNull(message = "price cannot be null")
    private String price;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    private Long  author_id;

    @OneToMany(mappedBy = "course" ,cascade = CascadeType.ALL  )
    @JsonManagedReference
    @Fetch(FetchMode.JOIN)
    private List<Section> sections;

    @Override
    public String toString() {

        return "" ;
    }
}
