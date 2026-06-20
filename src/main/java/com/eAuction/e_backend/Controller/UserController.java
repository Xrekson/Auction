package com.eAuction.e_backend.Controller;

import com.eAuction.e_backend.DTO.UserDTO;
import com.eAuction.e_backend.DTO.UserReq;
import com.eAuction.e_backend.Entity.Users;
import com.eAuction.e_backend.Service.UserService;
import com.eAuction.e_backend.vo.Util;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${app.cors.allowed-origins}", methods = { RequestMethod.GET,
        RequestMethod.POST, RequestMethod.PUT })
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    UserService samp;

    @Autowired
    Util jwtUtil;

    @GetMapping(value = "/all")
    public List<UserDTO> name() {
        return samp.getAll();
    }

    @GetMapping(value = "/get/{username}")
    public UserDTO getuser(@PathVariable("username") String username) {
        return samp.getusername(username);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void dell(@RequestParam("id") String id) {
        samp.deleteUser(Integer.parseInt(id));
    }

    @PutMapping(value = "/update")
    public void UpdateUser(
            @RequestBody(required = true) UserReq userData) {
        Users data = samp.getUsers(userData.username);
        try {
            data.setUpdatedat(LocalDateTime.now());
            if (userData.dob != null) {
                data.setDob(userData.getDob());
            }
            if (userData.email != null)
                data.setEmail(userData.email);
            if (userData.mobileno != null)
                data.setPhno(userData.mobileno);
            data.setUserName(userData.username);
            if (userData.password != null)
                data.setPassword(userData.password);
            if (userData.about != null)
                data.setAbout(userData.about);
            if (userData.desx != null)
                data.setDesx(userData.desx);
            if (userData.name != null)
                data.setName(userData.name);
            samp.savex(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
