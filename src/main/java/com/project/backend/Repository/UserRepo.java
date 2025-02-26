package com.project.backend.Repository;

import com.project.backend.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface UserRepo extends JpaRepository<Users, Integer>{
	public List<Users> findByUserNameAndPassword(String userName,String password);
	public List<Users> findByUserName(String userName);
}
