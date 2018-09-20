package com.bfarming.controller;

import com.bfarming.model.User;
import com.bfarming.model.UserAddress;
import com.bfarming.repository.UserAddressRepository;
import com.bfarming.repository.UserRepository;
import com.bfarming.response.ApiResponse;
import com.bfarming.response.ResponseStatus;
import com.bfarming.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.bfarming.config.BcryptGenerator;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
public class UserAccessController {

    private static final Logger logger = LoggerFactory.getLogger(UserAccessController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    UserService userService;

    @Autowired
    BcryptGenerator bcryptGenerator;

    @PostMapping("/users/register-user")
    @ResponseBody
    public ApiResponse<?> registerUser(@Valid @RequestBody User user) {

        logger.info("Inside Register user");
        User userEmailExists = userService.findUserByEmail(user.getEmail());
        User userMobileExists = userService.findUserByMobile(user.getMobile());
        User userPasswordExists = userService.findUserPassword(user.getPassword());
        if (userEmailExists != null) {
            return new ApiResponse<>("\"There is already a user registered with the email provided\"", com.bfarming.response.ResponseStatus.getValidResponseStatus(HttpStatus.OK));

        } else if (userMobileExists != null) {
            return new ApiResponse<>("\"There is already a user registered with the mobile provided\"", ResponseStatus.getValidResponseStatus(HttpStatus.OK));
        } else if(userPasswordExists != null){
            return new ApiResponse<>("\"User password required\"");
        }
        else{
            String password = bcryptGenerator.passwordEncoder(user.getPassword());
            user.setPassword(password);
            userService.save(user);
            logger.info("Done Created the User");
            return new ApiResponse<>("\"User details successfully saved in the database\"");
        }
    }

    @PostMapping("/users/login-user")
    @ResponseBody
    public ApiResponse<?> loginUser(@Valid @RequestBody User user){

        logger.info("Inside Login User");
        User userEmailExists = userService.findUserByEmail(user.getEmail());
        User userMobileExists = userService.findUserByMobile((user.getMobile()));
        String existingPassword =userEmailExists.getPassword();
        String currentPassword=user.getPassword();

        if (userEmailExists.getEmail().isEmpty()) {
            return new ApiResponse<>("\"Oops.! User email not found, please register.\"", com.bfarming.response.ResponseStatus.getValidResponseStatus(HttpStatus.OK));
        }else if(userMobileExists.getMobile().isEmpty()){
            return new ApiResponse<>("\"Oops.! User mobile not found, please register.\"", com.bfarming.response.ResponseStatus.getValidResponseStatus(HttpStatus.OK));
        }else if (bcryptGenerator.passwordDecoder(currentPassword,existingPassword)) {
            return new ApiResponse<>("\"Password Exists, logged-in\"");
        }else {
            return new ApiResponse<>("\"Password didn't match, please enter the correct password, logged-in\"");
        }
    }

    @PostMapping("/users/edit-user/{id}")
    @ResponseBody
    public ApiResponse<?> editUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User user){

        logger.info("Inside Edit User");
        User userExists = userService.findUserById(userId);
        if(userExists == null){

            return new ApiResponse<>("\"Oops.! User email/mobile not found, please register.\"", com.bfarming.response.ResponseStatus.getValidResponseStatus(HttpStatus.OK));
        }else if(userExists.getId() == user.getId()){
             userService.updateUser(user);
         }
        return new ApiResponse<>(null, com.bfarming.response.ResponseStatus.getValidResponseStatus(HttpStatus.OK),"\"The details entered are updated successfully.\"");
    }


    @GetMapping("/users/get-user/{id}")
    @ResponseBody
    public ApiResponse<?> getUser(@PathVariable(value = "id") Long userId){

        logger.info("Inside get-user API call");
        User userExists = userService.findUserById(userId);
        if(userExists == null){
            return new ApiResponse<>("\"Oops.! Unable to fetch your data.\"", com.bfarming.response.ResponseStatus.getValidResponseStatus(HttpStatus.OK));
        }
        return new ApiResponse<>(userExists,com.bfarming.response.ResponseStatus.getValidResponseStatus(HttpStatus.OK));
    }


    @PostMapping("/users/add-address/{id}")
    @ResponseBody
    public ApiResponse<?> addAddress(@PathVariable(value = "id") Long userId, @Valid @RequestBody UserAddress address){
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYy");
        System.out.println(address.getUserId());
        System.out.println(address.getCity());
        logger.info("Inside add Address");
        userService.save(address);

        return new ApiResponse<>(null, com.bfarming.response.ResponseStatus.getValidResponseStatus(HttpStatus.OK),"\"Address added successfully.\"");
    }
}
