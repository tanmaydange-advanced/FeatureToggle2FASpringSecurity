package org.example;

import com.warrenstrange.googleauth.ICredentialRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CredentialRepository implements ICredentialRepository {

    @Autowired
    UserService userService;
   // private final Map<String, UserTOTP> usersKeys = new HashMap<>();

    @Override
    public String getSecretKey(String username) {
        return  userService.getUserByUsername(username).getKey();
//        return usersKeys.get(username).getSecretKey();
    }

    @Override
    public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {

        Users users=userService.getUserByUsername(userName);
        UserTOTP userTOTP = new UserTOTP(userName, secretKey, validationCode, scratchCodes);
        users.setKey(userTOTP.secretKey);
        users.setMfaEnabled(true);
        //usersKeys.put(userName, new UserTOTP(userName, secretKey, validationCode, scratchCodes));
        userService.saveOrUpdate(users);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class UserTOTP implements Serializable {

        private String username;
        private String secretKey;
        private int validationCode;
        private List<Integer> scratchCodes;

    }

   /* public UserTOTP getUser(String username) {
        return usersKeys.get(username);
    }*/
}