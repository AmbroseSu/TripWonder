package com.ambrose.tripwonder.dto.request;

import lombok.Data;

@Data
public class SigninRequest {
  private String login;
  private String password;
}
