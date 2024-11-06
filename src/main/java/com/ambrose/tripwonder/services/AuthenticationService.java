package com.ambrose.tripwonder.services;


import com.ambrose.tripwonder.dto.request.*;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> signin(SigninRequest signinRequest);

    ResponseEntity<?> signinGoogle(String email);

    ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest);

    ResponseEntity<?> checkEmail(String email);

    String checkResetVerifyToken(String email, Long id);

    ResponseEntity<?> saveInfor(SignUp signUp);

    ResponseEntity<?> checkEmailForgotPassword(String email);

    ResponseEntity<?> changePassword(String email, String password);

    ResponseEntity<?> saveInforGoogle(SignUpGoogle signUpGoogle);
    ResponseEntity<?> saveInfoStaff(SignUpStaff signUp);
}
