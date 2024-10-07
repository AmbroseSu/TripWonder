package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.entities.enums.Gender;
import com.ambrose.tripwonder.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUserDTO {
  private long id;
  private String fullname;
  //private String secondname;
  private String email;
  //private String password;
  private String address;
  private String phoneNumber;
  private Gender gender;
  private Role role;
}
