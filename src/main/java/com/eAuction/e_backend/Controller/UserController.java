package com.eAuction.e_backend.Controller;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eAuction.e_backend.DTO.UserReq;
import com.eAuction.e_backend.Entity.Users;
import com.eAuction.e_backend.service.UserService;
import com.eAuction.e_backend.vo.Util;
@RestController
@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class UserController {
	@Autowired
	UserService samp;
	
	@Autowired
	Util jwtUtil;
	
	@Autowired
    private PasswordEncoder encoder;
	
	@PostMapping(value = "/register")
	public ResponseEntity<?> save(@RequestBody(required = true) UserReq userData
		// @RequestBody(required = true) String dob, @RequestBody(required = true) String email,
		// 	@RequestBody(required = true) String mobileno, @RequestBody(required = true) String username,
		// 	@RequestBody(required = true) String password, @RequestBody(required = true) String type,
		// 	@RequestBody(required = true) String desx, @RequestBody(required = true) String about, @RequestBody(required = true) String name
			) {
		Users data = new Users();
		Map<String, Object> response = new HashMap<String,Object>();
		try {
			System.out.print(userData.dob+userData.username+userData.password+userData.type+userData.desx+userData.about+userData.name);
			data.setCreatedat(new Timestamp(System.currentTimeMillis()));
			data.setUpdatedat(new Timestamp(System.currentTimeMillis()));
			data.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(userData.dob));
			data.setEmail(userData.email);
			data.setPhno(userData.mobileno);
			data.setUserName(userData.username);
			data.setPassword(encoder.encode(userData.password));
			data.setType(userData.type);
			data.setAbout(userData.about);
			data.setDesx(userData.desx);
			data.setName(userData.name);
			samp.save(data);
			response.put("msg","User Creation Success!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("msg","User Creation Failed!");
			response.put("err",e.getMessage());
			return ResponseEntity.ok(response);
		}
	}
	@GetMapping(value = "/auth/user/getall")
	public List<Users> name() {
		return samp.getAll();
	}
	@GetMapping(value="/auth/user/get/{username}")
	public Users getuser(@PathVariable("username") String username) {
		return samp.getusername(username);
	}
	@PostMapping(value="/login")
	public ResponseEntity<?> login(@RequestParam String username,
			@RequestParam String password) {
		Users data = samp.getUsersingle(username, password);
		password = encoder.encode(password);
		if(data!=null) {
			Map<String ,String> res = new HashMap<String, String>();
			if(encoder.matches(data.getPassword(), password)) {
				res.put("error", "Wrong password!");				
			}else {
				String jwToken = jwtUtil.generateToken(data);
				res.put("token", jwToken);				
				res.put("id", data.getId().toString());		
				res.put("username", data.getUserName());
				res.put("type", data.getType());
				}
//			claims.put("Position",data.getDesx());
//			claims.put("Type",data.getType());			
			return ResponseEntity.ok(res);
		}else {
			Map<String ,String> claims = new HashMap<String, String>();
			claims.put("error", "Wrong username or password!");
			return ResponseEntity.ok(claims);
		}
	}
	@DeleteMapping(value = "/auth/user/delete/{id}")
	public void dell(@RequestParam("id") String id) {
		samp.deleteUser(Integer.parseInt(id));
	}
	@PutMapping(value = "/auth/user/update")
	public void UpdateUser(@RequestBody(required = true) UserReq userData
	//  @RequestBody(required = false) String email,
	// 		@RequestBody(required = false) String phno, @RequestBody(required = true) String username,
	// 		@RequestBody(required = false) String password,@RequestBody(required = false) Integer id,
	// 		@RequestBody(required = false) String desx, @RequestBody(required = false) String about, @RequestBody(required = false) String name
			) {
		Users data = samp.getUsers(userData.username);
		try {
			data.setUpdatedat(new Timestamp(System.currentTimeMillis()));
			if(userData.dob!=null)
				data.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(userData.dob));
			if(userData.email!=null)
				data.setEmail(userData.email);
			if(userData.mobileno!=null)
				data.setPhno(userData.mobileno);
			data.setUserName(userData.username);
			if(userData.password!=null)
				data.setPassword(userData.password);
			if(userData.about!=null)
				data.setAbout(userData.about);
			if(userData.desx!=null)
				data.setDesx(userData.desx);
			if(userData.name!=null)
				data.setName(userData.name);
			samp.savex(data);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
}
