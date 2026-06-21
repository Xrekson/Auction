package com.eAuction.e_backend.Controller.pre_auth;

import com.eAuction.e_backend.DTO.LoginReq;
import com.eAuction.e_backend.DTO.UserDTO;
import com.eAuction.e_backend.DTO.UserReq;
import com.eAuction.e_backend.Entity.Users;
import com.eAuction.e_backend.Service.EmailService;
import com.eAuction.e_backend.Service.UserService;
import com.eAuction.e_backend.vo.Util;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "${app.cors.allowed-origins}", methods = { RequestMethod.GET,
        RequestMethod.POST, RequestMethod.PUT })
@RequestMapping("/api/pre-auth/user")
public class UserPreAuthController {

    @Autowired
    UserService samp;

    @Autowired
    Util jwtUtil;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/register")
    public ResponseEntity<Map<String, Object>> savePreAuth(
            @RequestBody(required = true) UserReq userData) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            // Check username duplication
            Users existingUser = samp.getfromusername(userData.username);
            if (existingUser != null) {
                if (existingUser.getVerified()) {
                    response.put("err", "Username already exists!");
                    return ResponseEntity.ok(response);
                }
            }

            // Check email duplication
            Users existingEmailUser = samp.getfromemail(userData.email);
            if (existingEmailUser != null) {
                if (existingEmailUser.getVerified()) {
                    response.put("err", "Email is already registered!");
                    return ResponseEntity.ok(response);
                }
            }

            Users data = (existingUser != null) ? existingUser : new Users();
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

            // Generate OTP code
            String otp = String.format("%06d", new Random().nextInt(1000000));
            data.setOtpCode(otp);
            data.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
            data.setVerified(false);

            // Send OTP email
            emailService.sendOtpEmail(data.getEmail(), data.getName(), otp);

            // Save user only after successfully sending OTP
            samp.save(data);

            response.put("msg", "OTP Sent");
            response.put("username", data.getUserName());
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
                userDTO.getPassword());
        if (data != null) {
            Map<String, String> res = new HashMap<String, String>();
            if (!encoder.matches(userDTO.getPassword(), data.getPassword())) {
                res.put("error", "Wrong password!");
            } else if (Boolean.FALSE.equals(data.getVerified())) {
                res.put("error", "Please verify your email address before logging in.");
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

    @PostMapping(value = "/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(
            @RequestBody Map<String, String> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = payload.get("username");
            String otp = payload.get("otp");

            Users user = samp.getfromusername(username);
            if (user == null) {
                response.put("err", "User not found!");
                return ResponseEntity.ok(response);
            }

            if (user.getVerified()) {
                response.put("msg", "User Creation Success!");
                return ResponseEntity.ok(response);
            }

            if (user.getOtpCode() == null || !user.getOtpCode().equals(otp)) {
                response.put("err", "Invalid OTP code!");
                return ResponseEntity.ok(response);
            }

            if (user.getOtpExpiry() == null || LocalDateTime.now().isAfter(user.getOtpExpiry())) {
                response.put("err", "OTP code has expired!");
                return ResponseEntity.ok(response);
            }

            user.setVerified(true);
            user.setOtpCode(null);
            user.setOtpExpiry(null);
            samp.save(user);

            response.put("msg", "User Creation Success!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("err", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "/resend-otp")
    public ResponseEntity<Map<String, Object>> resendOtp(
            @RequestBody Map<String, String> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = payload.get("username");
            Users user = samp.getfromusername(username);
            if (user == null) {
                response.put("err", "User not found!");
                return ResponseEntity.ok(response);
            }

            if (user.getVerified()) {
                response.put("err", "User is already verified!");
                return ResponseEntity.ok(response);
            }

            String otp = String.format("%06d", new Random().nextInt(1000000));
            user.setOtpCode(otp);
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
            user.setUpdatedat(LocalDateTime.now());

            emailService.sendOtpEmail(user.getEmail(), user.getName(), otp);
            samp.save(user);

            response.put("msg", "OTP Sent");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("err", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}
