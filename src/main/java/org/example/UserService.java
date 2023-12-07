package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;


    //getting all users records
    public List<Users> getAllUsers()
    {
        List<Users> users = new ArrayList<>();
        repository.findAll().forEach(user -> users.add(user));
        return users;
    }
    //getting a specific record
    public Users getUserById(int id)
    {
        return repository.findById(id).get();
    }
    public void saveOrUpdate(Users users)
    {
        repository.save(users);
    }
    //deleting a specific record
    public String delete(int id)
    {
        repository.deleteById(id);
        return  "Successfully Deleted!";
    }


    public Users getUserByUsername(String username)
    {
        List<Users> usersList = (List<Users>) repository.findAll();

        for (Users user : usersList){
            if (user.userName.equals(username))
                {return user;}
        }
        return null;
    }


}
