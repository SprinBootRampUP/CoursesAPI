package org.course.api.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Lecture extends BaseEntity {


    private String name;

    @ManyToOne
    @JoinColumn( name = "section_id")
    @JsonBackReference
    private Section section;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id" )
    @JsonManagedReference
    private Resource resource;

    @Override
    public String toString(){
        return " ";

    }

}
