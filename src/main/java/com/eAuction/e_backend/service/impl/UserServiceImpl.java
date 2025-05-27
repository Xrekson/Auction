package com.eAuction.e_backend.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eAuction.e_backend.Entity.Users;
import com.eAuction.e_backend.Repository.UserRepo;
import com.eAuction.e_backend.service.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo userRepo;
	
	@Transactional
	public void save(Users input) {
		try {
			userRepo.save(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Transactional
	public Users getUsers(String uname) {
		return userRepo.findByUserName(uname).get(0);
	}
	@Transactional
	public Users getUsers(Integer id) {
		return userRepo.findById(id).get();
	}
	@Transactional
	public void savex(Users input) throws IOException {
		userRepo.save(input);
	}
	@Transactional
	public Users getusername(String username) {
		return userRepo.findByUserName(username).get(0);
	}
	@Transactional
	public Users getUsersingle(String username,String password){
		List<Users> data = userRepo.findByUserName(username);
		return data.size()>0 ? data.get(0):null;
	}
	@Transactional
	public void UpdateImage(Integer id) {
		try {
			Users data = userRepo.findById(id).get();
			userRepo.save(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Transactional
	public List<Users> getAll() {
		return userRepo.findAll();
	}
	@Transactional
	public void updateUser(Users input) throws Exception {
		Users data = userRepo.findById(input.getId()).get();
		if(data!=null) {
			data.setUpdatedat(new Timestamp(System.currentTimeMillis()));
			data.setDob(input.getDob());
			data.setPassword(input.getPassword());
			data.setPhno(input.getPhno());
			data.setName(input.getName());
			data.setAbout(input.getAbout());
			data.setDesx(input.getDesx());
			userRepo.save(input);			
		}else {
			throw new Exception("Wrong Id");
		}
	}
	@Transactional
	public void deleteUser(Integer id) {
		try {
			Users dataUsers = userRepo.findById(id).get();
			userRepo.delete(dataUsers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
