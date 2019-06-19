package com.hackley.cf.demo.Model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Post  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    private String body;
    @DateTimeFormat(pattern="yyyy-mm-dd")
    private Date createdAt;

    @ManyToOne
    private ApplicationUser creator;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ApplicationUser getCreator() {
        return creator;
    }

    public void setCreator(ApplicationUser creator) {
        this.creator = creator;
    }

    public Post() {}
    public Post(String body){
        this.body = body;
        this.createdAt = new Date();
    }

}
