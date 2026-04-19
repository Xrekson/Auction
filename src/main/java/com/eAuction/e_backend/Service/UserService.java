package com.eAuction.e_backend.Service;

import com.eAuction.e_backend.DTO.UserDTO;
import com.eAuction.e_backend.Entity.Users;
import java.util.List;

public interface UserService {
    public void save(Users input);
    public UserDTO getUserDTO(String uname);
    public Users getUsers(String uname);
    public Users getUsers(Integer id);
    public void savex(Users input);
    public UserDTO getusername(String username);
    public Users getfromusername(String username);
    public Users getUsersingle(String username, String password);
    public void UpdateImage(Integer id);
    public List<UserDTO> getAll();
    public void updateUser(Users input) throws Exception;
    public void deleteUser(Integer id);
}
