package com.project.backend.Controller;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import com.project.backend.Entity.Users;
import com.project.backend.service.impl.Servo;
import com.project.backend.vo.Util;
@RestController
@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class UserController {
	@Autowired
	Servo samp;
	
	@Autowired
	Util jwtUtil;
	
	@Autowired
    private PasswordEncoder encoder;
	
	@PostMapping(value = "/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestPart("dob") String dob, @RequestPart("email") String email,
			@RequestPart("mobileno") String phno, @RequestPart("username") String username,
			@RequestPart("password") String password, @RequestPart("type") String type,
			@RequestPart("deg") String desx, @RequestPart("about") String about, @RequestPart("name") String name,
			@RequestParam("file") MultipartFile file) {
		Users data = new Users();
		Map<String, Object> response = new HashMap<String,Object>();
		try {
			System.out.print(dob+username+password+type+desx+about+name+file.getSize());
			data.setCreatedat(new Timestamp(System.currentTimeMillis()));
			data.setUpdatedat(new Timestamp(System.currentTimeMillis()));
			data.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
			data.setEmail(email);
			data.setPhno(phno);
			data.setUserName(username);
			data.setPassword(encoder.encode(password));
			data.setType(type);
			data.setAbout(about);
			data.setDesx(desx);
			data.setName(name);
			samp.save(data, file);
			response.put("msg","User Creation Success!");
			return ResponseEntity.ok(response);
		} catch (ParseException e) {
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
	@PutMapping(value = "/auth/user/update/{userName}")
	public void UpdateUser(@RequestPart(name="dob",required = false) String dob, @RequestPart(name="email",required = false) String email,
			@RequestPart(name="phno",required = false) String phno, @PathVariable(name="userName") String username,
			@RequestPart(name="password",required = false) String password,@RequestPart(name="id",required = false) Integer id,
			@RequestPart(name="desx",required = false) String desx, @RequestPart(name="about",required = false) String about, @RequestPart(name="name",required = false) String name) {
		Users data = samp.getUsers(username);
		try {
			data.setUpdatedat(new Timestamp(System.currentTimeMillis()));
			if(dob!=null)
				data.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
			if(email!=null)
				data.setEmail(email);
			if(phno!=null)
				data.setPhno(phno);
			data.setUserName(username);
			if(password!=null)
				data.setPassword(password);
			if(about!=null)
				data.setAbout(about);
			if(desx!=null)
				data.setDesx(desx);
			if(name!=null)
				data.setName(name);
			samp.savex(data);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	@GetMapping("/auth/user/get/file/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
		byte[] fileEntity = samp.getFile(id).getData();
		if (fileEntity == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileEntity);
	}

	
}
