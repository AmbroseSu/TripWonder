package com.ambrose.tripwonder.services;


import com.ambrose.tripwonder.dto.request.RefreshTokenRequest;
import com.ambrose.tripwonder.dto.request.SignUp;
import com.ambrose.tripwonder.dto.request.SigninRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
  ResponseEntity<?> signin(SigninRequest signinRequest);

  ResponseEntity<?> signinGoogle(String email);
  ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest);

  public ResponseEntity<?> checkEmail(String email);
  public String checkResetVerifyToken(String email, Long id);
  public ResponseEntity<?> saveInfor(SignUp signUp);

  public ResponseEntity<?> checkEmailForgotPassword(String email);
  public ResponseEntity<?> changePassword(String email, String password);
  //public ResponseEntity<?> saveInforGoogle(SignUpGoogle signUpGoogle);
}
