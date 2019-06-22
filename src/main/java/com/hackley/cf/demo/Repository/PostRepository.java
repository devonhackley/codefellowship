package com.hackley.cf.demo.Repository;

import com.hackley.cf.demo.Model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByCreatorId(long id);

}
