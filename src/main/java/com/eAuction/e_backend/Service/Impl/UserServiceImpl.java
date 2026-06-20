package com.eAuction.e_backend.Service.Impl;

import com.eAuction.e_backend.DTO.UserDTO;
import com.eAuction.e_backend.Entity.Users;
import com.eAuction.e_backend.Mappers.UserMapper;
import com.eAuction.e_backend.Repository.UserRepo;
import com.eAuction.e_backend.Service.UserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserMapper userMappper;

    @Override
    @Transactional
    public void save(Users input) {
        try {
            userRepo.save(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public UserDTO getUserDTO(String uname) {
        var data = userRepo.findByUserName(uname);
        return data.size()>0 ? userMappper.toDto(data.get(0)) :null;
    }

    @Override
    @Transactional
    public Users getUsers(String uname) {
        var data = userRepo.findByUserName(uname);
        return data.size() > 0 ? data.get(0) : null;
    }

    @Override
    @Transactional
    public Users getUsers(Integer id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void savex(Users input) {
        userRepo.save(input);
    }

    @Override
    @Transactional
    public UserDTO getusername(String username) {
        var data = userRepo.findByUserName(username);
        return data.size()>0 ? userMappper.toDto(data.get(0)) :null;
    }

    @Override
    @Transactional
    public Users getUsersingle(String username, String password) {
        List<Users> data = userRepo.findByUserName(username);
        return data.size() > 0 ? data.get(0) : null;
    }

    @Override
    @Transactional
    public void UpdateImage(Integer id) {
        try {
            Users data = userRepo.findById(id).get();
            userRepo.save(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<UserDTO> getAll() {
        return userRepo.findAll().stream().map(userMappper::toDto).toList();
    }

    @Override
    @Transactional
    public void updateUser(Users input) throws Exception {
        Users data = userRepo.findById(input.getId()).get();
        if (data != null) {
            data.setUpdatedat(LocalDateTime.now());
            data.setDob(input.getDob());
            data.setPassword(input.getPassword());
            data.setPhno(input.getPhno());
            data.setName(input.getName());
            data.setAbout(input.getAbout());
            data.setDesx(input.getDesx());
            userRepo.save(input);
        } else {
            throw new Exception("Wrong Id");
        }
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        try {
            Users dataUsers = userRepo.findById(id).get();
            userRepo.delete(dataUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Users getfromusername(String username) {
        var data = userRepo.findByUserName(username);
        return data.size() > 0 ? data.get(0) : null;
    }
}
