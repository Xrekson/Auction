package com.eAuction.e_backend.service;

import java.io.IOException;
import java.util.List;

import com.eAuction.e_backend.Entity.Users;

public interface UserService {
	public void save(Users input);
	public Users getUsers(String uname);
	public Users getUsers(Integer id);
	public void savex(Users input) throws IOException;
	public Users getusername(String username);
	public Users getUsersingle(String username,String password);
	public void UpdateImage(Integer id);
	public List<Users> getAll();
	public void updateUser(Users input) throws Exception;
	public void deleteUser(Integer id);
}
