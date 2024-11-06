package com.ambrose.tripwonder.dto.request;


import com.ambrose.tripwonder.entities.enums.Gender;
import lombok.Data;

@Data
public class SignUpStaff {
    String email;
    String fullname;
    String phone;
    String password;
    String address;
    String image;
    Gender gender;
//    String fcmtoken;
}
