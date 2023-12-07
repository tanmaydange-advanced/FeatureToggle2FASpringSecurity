package org.example;

import net.bytebuddy.utility.nullability.AlwaysNull;
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
    private List<Users> getAllUsers()
    {
        return service.getAllUsers();
    }


    //creating a get mapping that retrieves the detail of a specific student
    @GetMapping("/admin/user/{id}")
    private Users getUser(@PathVariable("id") int id)
    {
        return service.getUserById(id);
    }

    //creating a delete mapping that deletes a specific student
    @GetMapping("/admin/deleteUser/{id}")
    private String deleteUser(@PathVariable("id") int id)
    {
        service.delete(id);
        return  "successful";
    }

    //creating post mapping that post the user in the database
    @PostMapping("/admin/createUser")
    private String saveStudent(@ModelAttribute("users") @RequestBody Users users)
    {
        service.saveOrUpdate(users);
        userDetailsManager.createUser(users.castUserToUserDetails());
        return "successful";
    }



    @GetMapping("/admin/manage-users")
    public String viewHomePage(Model model) {
        model.addAttribute("allUserlist", service.getAllUsers());
        return "manage-users";
    }

    @GetMapping("/admin/create-user")
    public String createUserPage(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "create-user";
    }


    @GetMapping("/admin/register-mfa/{username}")
    public String registerMFA(@PathVariable String username, Model model) {
        System.out.println("Username passsed "+ username);
        Users user = new Users();
        user.setUserName(username);
        model.addAttribute("user", user);
        return "register-mfa";
    }


    @GetMapping("/validate/otp/{username}")
    public String viewHomePage(@PathVariable String username, Model model) {
        ValidateCodeDto dto = new ValidateCodeDto();
        dto.setUsername(username);
        model.addAttribute("dto", dto);
        return "validate-otp";
    }


    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
