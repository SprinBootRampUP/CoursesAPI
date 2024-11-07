package org.course.api.Entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.Date;

@MappedSuperclass
@EqualsAndHashCode
public abstract class BaseEntity {

@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
private long id;


@Column(name = "created_at")
@Temporal(TemporalType.DATE)
private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;


    @PrePersist
    public void onCreate(){
        this.createdAt= new Date();
    }


    @PostPersist
    public void onUpdate(){
        this.createdAt=  new Date();
    }

}
