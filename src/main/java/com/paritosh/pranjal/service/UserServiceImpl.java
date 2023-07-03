package com.paritosh.pranjal.service;

import com.paritosh.pranjal.entity.User;
import com.paritosh.pranjal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
         return userRepository.save(user);
    }

    @Override
    public User getSingleUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
        {
            return user.get();
        }
        else {
            throw new RuntimeException("user not found for the id");
        }
    }

    @Override
    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
