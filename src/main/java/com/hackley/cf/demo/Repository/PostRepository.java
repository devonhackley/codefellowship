package com.hackley.cf.demo.Repository;

import com.hackley.cf.demo.Model.ApplicationUser;
import com.hackley.cf.demo.Model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByCreatorId(long id);
    List<Post> findByCreatorIn(Set<ApplicationUser> list);

}
