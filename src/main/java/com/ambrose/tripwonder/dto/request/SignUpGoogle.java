package com.ambrose.tripwonder.dto.request;

import com.ambrose.tripwonder.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpGoogle {
  String email;
  String fullname;
  String phone;
  String address;
  String image;
  //Gender gender;
  String fcmtoken;
}
