package com.eAuction.e_backend.Repository;

import com.eAuction.e_backend.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer>{
	public List<Users> findByUserNameAndPassword(String userName,String password);
	public List<Users> findByUserName(String userName);
}
