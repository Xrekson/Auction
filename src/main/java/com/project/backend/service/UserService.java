package com.project.backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.backend.Entity.Images;
import com.project.backend.Entity.Users;

public interface UserService {
	public void save(Users input, MultipartFile image);
	public Users getUsers(String uname);
	public Users getUsers(Integer id);
	public void savex(Users input) throws IOException;
	public Users getusername(String username);
	public Users getUsersingle(String username,String password);
	public void UpdateImage(Integer id,MultipartFile image);
	public List<Users> getAll();
	public void updateUser(Users input) throws Exception;
	public Images getFile(Integer id);
	public void deleteUser(Integer id);
	
}
