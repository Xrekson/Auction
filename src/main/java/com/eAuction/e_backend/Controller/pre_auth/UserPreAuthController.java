package com.eAuction.e_backend.Controller.pre_auth;

import com.eAuction.e_backend.DTO.LoginReq;
import com.eAuction.e_backend.DTO.UserDTO;
import com.eAuction.e_backend.DTO.UserReq;
import com.eAuction.e_backend.Entity.Users;
import com.eAuction.e_backend.Service.UserService;
import com.eAuction.e_backend.vo.Util;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
    origins = { "http://localhost:4200", "http://localhost:5173" },
    methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT }
)
@RequestMapping("/api/pre-auth/user")
public class UserPreAuthController {

    @Autowired
    UserService samp;

    @Autowired
    Util jwtUtil;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping(value = "/register")
    public ResponseEntity<Map<String, Object>> savePreAuth(
        @RequestBody(required = true) UserReq userData
    ) {
        Users data = new Users();
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            System.out.print(
                userData.getDob() +
                    userData.username +
                    userData.password +
                    userData.type +
                    userData.desx +
                    userData.about +
                    userData.name
            );
            data.setCreatedat(LocalDateTime.now());
            data.setUpdatedat(LocalDateTime.now());
            data.setDob(userData.getDob());
            data.setEmail(userData.email);
            data.setPhno(userData.mobileno);
            data.setUserName(userData.username);
            data.setPassword(encoder.encode(userData.password));
            data.setType(userData.type);
            data.setAbout(userData.about);
            data.setDesx(userData.desx);
            data.setName(userData.name);
            samp.save(data);
            response.put("msg", "User Creation Success!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("msg", "User Creation Failed!");
            response.put("err", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping(value = "/get/{username}")
    public UserDTO getuserPreAuth(@PathVariable("username") String username) {
        return samp.getusername(username);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginPreAuth(@RequestBody LoginReq userDTO) {
        Users data = samp.getUsersingle(
            userDTO.getUsername(),
            userDTO.getPassword()
        );
        String password = encoder.encode(userDTO.getPassword());
        if (data != null) {
            Map<String, String> res = new HashMap<String, String>();
            if (encoder.matches(data.getPassword(), password)) {
                res.put("error", "Wrong password!");
            } else {
                String jwToken = jwtUtil.generateToken(data);
                res.put("token", jwToken);
                res.put("id", data.getId().toString());
                res.put("username", data.getUserName());
                res.put("type", data.getType());
            }
            return ResponseEntity.ok(res);
        } else {
            Map<String, String> claims = new HashMap<String, String>();
            claims.put("error", "Wrong username or password!");
            return ResponseEntity.ok(claims);
        }
    }
}
