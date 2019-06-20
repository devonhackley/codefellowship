package com.hackley.cf.demo.Controller;

import com.hackley.cf.demo.Model.ApplicationUser;
import com.hackley.cf.demo.Model.Post;
import com.hackley.cf.demo.Repository.ApplicationUserRepository;
import com.hackley.cf.demo.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String getLandingPage(Principal p, Model model){
        String princeipal = p == null ? "" : p.getName();
        model.addAttribute("principal", princeipal);
        return "landing";
    }

    @GetMapping("/signup")
    public String getSignUpPage(Principal p, Model model){
        String princeipal = p == null ? "" : p.getName();
        model.addAttribute("principal", princeipal);
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpPageSubmit(String username,
                                   String password,
                                   String firstname,
                                   String lastname,
                                   String dateOfBirth,
                                   String bio) throws ParseException {
        //check if user exists
        ApplicationUser exisitingUser =  applicationUserRepository.findByUsername(username);
        if(exisitingUser != null){
            return "redirect:/login";
        } else {
            ApplicationUser user = new ApplicationUser(username,
                    passwordEncoder.encode(password));
            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setDateOfBirth(dob);
            user.setBio(bio);
            applicationUserRepository.save(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return String.format("redirect:/users/%s",user.getId());
        }
    }

    @GetMapping("/users/{id}")
    public String getUsersPage(@PathVariable long id, Model model, Principal p){
        ApplicationUser user = applicationUserRepository.findById(id).get();
        List<Post> posts = postRepository.findByCreatorId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);

        String princeipal = p == null ? "" : p.getName();
        model.addAttribute("principal", princeipal);
        return "userDetail";
    }

    @GetMapping("/users/all")
    public String getUsersIndexPage(Model model, Principal p){
        Iterable<ApplicationUser> users = applicationUserRepository.findAll();
        String princeipal = p == null ? "" : p.getName();
        model.addAttribute("principal", princeipal);
        model.addAttribute("users", users);
        return "userIndex";
    }

    @PostMapping("users/{id}/follow")
    public RedirectView followUser(@PathVariable long id, Model model, Principal p){
        // find the followed user and logged in user from db
        ApplicationUser friend = applicationUserRepository.findById(id).get();
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());

        friend.getFollowers().add(user);
        user.getFollowing().add(friend);

        applicationUserRepository.save(friend);
        applicationUserRepository.save(user);

        return new RedirectView("/myprofile");

    }



    @GetMapping("/login")
    public String getLoginPage(Principal p, Model model){
        String princeipal = p == null ? "" : p.getName();
        model.addAttribute("principal", princeipal);
        return "login";
    }

    @GetMapping("/myprofile")
    public String getProfilePage(Principal p, Model model){
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        List<Post> posts = postRepository.findByCreatorId(user.getId());
        Set<ApplicationUser> followers = user.getFollowers();
        Set<ApplicationUser> following = user.getFollowing();
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("followers", followers);
        model.addAttribute("following", following);

        String princeipal = p == null ? "" : p.getName();
        model.addAttribute("principal", princeipal);
        return "userDetail";
    }

}
