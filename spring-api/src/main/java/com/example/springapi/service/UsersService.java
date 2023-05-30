package com.example.springapi.service;

import com.example.springcore.entity.Users;
import com.example.springcore.model.UsersBean;
import com.example.springcore.model.RecordNotFoundException;
import com.example.springcore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public List<UsersBean> getAllUsersBean() {
        return usersRepository.getAllUsersBean();
    }

    public Users getUsersById(Long id) throws RecordNotFoundException {
        Optional<Users> users = usersRepository.findById(id);

        if (users.isPresent()) {
            return users.get();
        } else {
            throw new RecordNotFoundException("No users record exist for given id");
        }
    }

    @Transactional
    public Users createOrUpdateUsers(Users entity) {
        if (entity.getId() == null) {
            return usersRepository.save(entity);
        }
        Optional<Users> users = usersRepository.findById(entity.getId());
        Users newEntity;
        if (!users.isPresent()) {
            newEntity = new Users();
        } else {
            newEntity = users.get();
        }
        newEntity.setEmail(entity.getEmail());
        newEntity = usersRepository.save(newEntity);
        return newEntity;
    }

    @Transactional
    public void deleteUsersById(Long id) throws RecordNotFoundException {
        Optional<Users> users = usersRepository.findById(id);

        if (users.isPresent()) {
            usersRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No users record exist for given id");
        }
    }
}