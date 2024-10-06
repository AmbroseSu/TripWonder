package com.ambrose.tripwonder.dto.request;


import com.ambrose.tripwonder.entities.enums.Gender;
import lombok.Data;

@Data
public class SignUp {
    String email;
    String fullname;
    String phone;
    String password;
    String address;
    Gender gender;
    String fcmtoken;
}
