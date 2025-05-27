package com.eAuction.e_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {

    public String dob;

    public String email;

    public String mobileno;

    public String username;

    public String password;

    public String type;

    public String desx;

    public String about;

    public String name;
}