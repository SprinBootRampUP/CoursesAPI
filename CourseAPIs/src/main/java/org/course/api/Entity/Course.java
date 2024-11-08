package org.course.api.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "course")
public class Course extends  BaseEntity {

    @Column(unique = true)
    @Basic(optional = false)
    private String courseTitle;

    @Column( columnDefinition = "VARCHAR(255) default 'Welcome to my course'")
    private String description;

    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;

    private String price;

    private boolean approved;

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
