package org.course.api.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name ="section")
public class Section extends BaseEntity {


    private String name;
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;


    @OneToMany(mappedBy = "section",cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<Lecture> lectures;

    @Override
    public String toString() {
        return " ";

    }
}
