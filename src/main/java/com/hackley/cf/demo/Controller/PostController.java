package com.hackley.cf.demo.Controller;

import com.hackley.cf.demo.Model.ApplicationUser;
import com.hackley.cf.demo.Model.Post;
import com.hackley.cf.demo.Repository.ApplicationUserRepository;
import com.hackley.cf.demo.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts/add")
    public String getPostForm(Principal p, Model  model){
        String princeipal = p == null ? "" : p.getName();
        model.addAttribute("principal", princeipal);
        return "postForm";
    }

    @PostMapping("/posts")
    public RedirectView addPosts(String body, Principal p){
        // create new post
        Post newPost = new Post(body);

        // link to creator
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        newPost.setCreator(user);

        // save to db and redirect
        postRepository.save(newPost);
        return new RedirectView("/myprofile");

    }
}
