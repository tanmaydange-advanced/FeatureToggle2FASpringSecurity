package com.dange.tanmay.controller;

import com.dange.tanmay.dao.User;
import com.dange.tanmay.service.UserService;
import com.dange.tanmay.dao.ValidateCodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    public UserService service;

    @Autowired
    InMemoryUserDetailsManager userDetailsManager;


    @GetMapping("/admin/users")
    private List<User> getAllUsers()
    {
        return service.getAllUsers();
    }


    @GetMapping("/admin/user/{id}")
    private User getUser(@PathVariable("id") int id)
    {
        return service.getUserById(id);
    }


    @GetMapping("/admin/deleteUser/{id}")
    private String deleteUser(@PathVariable("id") int id)
    {
        service.delete(id);
        return  "successful";
    }

    @PostMapping("/admin/createUser")
    private String saveStudent(@ModelAttribute("users") @RequestBody User user)
    {
        service.saveOrUpdate(user);
        userDetailsManager.createUser(user.castUserToUserDetails());
        return "successful";
    }



    @GetMapping("/admin/manage-users")
    public String viewHomePage(Model model) {
        model.addAttribute("allUserlist", service.getAllUsers());
        return "manage-users";
    }

    @GetMapping("/admin/create-user")
    public String createUserPage(Model model) {
        User user = new User();
        user.setMfaEnabled(false);
        model.addAttribute("user", user);
        return "create-user";
    }


    @GetMapping("/admin/register-mfa/{username}")
    public String registerMFA(@PathVariable String username, Model model) {
        User user = new User();
        user.setUserName(username);
        model.addAttribute("user", user);
        return "register-mfa";
    }


    @GetMapping("/validate/otp/{username}")
    public String viewHomePage(@PathVariable String username, Model model) {
        ValidateCodeDao dto = new ValidateCodeDao();
        dto.setUsername(username);
        model.addAttribute("dto", dto);
        return "validate-otp";
    }


    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/error")
    public String error(Model model) {
        return "error";
    }

}
