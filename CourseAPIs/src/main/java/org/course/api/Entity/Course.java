package org.course.api.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.course.api.Entity.CourseLevel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "Course")
@NamedQuery(
        name = "Course.findByTitle",
        query = "SELECT c FROM Course c WHERE c.courseTitle LIKE :title "
)
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

    @ManyToOne
    @JoinColumn( name = "author_id")
    @JsonBackReference
    private Author author;

    @OneToMany(mappedBy = "course" ,cascade = CascadeType.ALL  )
    @JsonManagedReference
    @Fetch(FetchMode.JOIN)
    private List<Section> sections;

    @Override
    public String toString() {

        return "" ;
    }
}
