package com.ambrose.tripwonder.dto.response;

import com.ambrose.tripwonder.dto.UserDTO;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {
  private UserDTO userDTO;
  private String token;
  private String refreshToken;
}
