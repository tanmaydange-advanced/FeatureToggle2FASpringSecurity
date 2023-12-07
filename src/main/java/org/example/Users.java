package org.example;

import lombok.Data;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Table(name="USERDETAILS")
@Entity
@Data
public class Users {

    @Id
    @Column(name="ID")
    public int userId;

    @Column(name="USERNAME")
    public String userName;

    @Column(name="PASSWORD")
    public String password;

    @Column(name="ROLE")
    public String role;

    @Column(name="MFA_ENABLED")
    public Boolean mfaEnabled;

    @Column(name="SECRET_KEY")
    public String key;



    public UserDetails castUserToUserDetails(){
        return User.withDefaultPasswordEncoder()
                        .username(userName)
                        .password(password)
                        .roles(role)
                        .build();
    }

}
