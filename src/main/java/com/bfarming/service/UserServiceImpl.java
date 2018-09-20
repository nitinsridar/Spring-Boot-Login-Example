package com.bfarming.service;

import com.bfarming.exception.ResourceNotFoundException;
import com.bfarming.model.User;
import com.bfarming.model.UserAddress;
import com.bfarming.repository.UserAddressRepository;
import com.bfarming.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;


    public User findUserByEmail(String email) {
     return  userRepository.findByEmail(email);
    }
    public User findUserByMobile(String mobile) {

        return  userRepository.findByMobile(mobile);
    }

    @Override
    public User findUserPassword(String password) {
        return userRepository.findByPassword(password);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {

        Long id = user.getId();

        User editUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (editUser.getDob() != null) {
            editUser.setDob(user.getDob());
        }
        if (editUser.getFirstName() != null) {
            editUser.setFirstName(user.getFirstName());

        }
        userRepository.save(editUser);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @Override
    public void save(UserAddress address) {

       UserAddress userAddress = userAddressRepository.save(address);
       System.out.println("XXXXXXXXXXXXXXXX");
       System.out.println(userAddress.getId());
       System.out.println(address.getUserId());



    }


}