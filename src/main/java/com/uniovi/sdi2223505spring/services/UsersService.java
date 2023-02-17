package com.uniovi.sdi2223505spring.services;

import com.uniovi.sdi2223505spring.entities.User;
import com.uniovi.sdi2223505spring.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

// Gestiona lo relativo a los usuarios
@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @PostConstruct
    public void init() { }

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUser(Long id) { return usersRepository.findById(id).get(); }

    public void addUser(User user) { usersRepository.save(user); }

    public void deleteUser(Long id) { usersRepository.deleteById(id); }

}
