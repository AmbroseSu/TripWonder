package com.ambrose.tripwonder.services.impl;


import com.ambrose.tripwonder.config.ConstraintViolationExceptionHandler;
import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.UpsertUserDTO;
import com.ambrose.tripwonder.dto.UserDTO;
import com.ambrose.tripwonder.dto.request.RefreshTokenRequest;
import com.ambrose.tripwonder.dto.request.SignUp;
import com.ambrose.tripwonder.dto.request.SigninRequest;
import com.ambrose.tripwonder.dto.response.JwtAuthenticationResponse;
import com.ambrose.tripwonder.entities.User;
import com.ambrose.tripwonder.entities.VerificationToken;
import com.ambrose.tripwonder.entities.enums.Role;
import com.ambrose.tripwonder.event.RegistrationCompleteEvent;
import com.ambrose.tripwonder.repository.UserRepository;
import com.ambrose.tripwonder.repository.VerificationTokenRepository;
import com.ambrose.tripwonder.services.AuthenticationService;
import com.ambrose.tripwonder.services.JWTService;
import com.ambrose.tripwonder.services.UserService;
import com.google.api.client.util.DateTime;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final GenericConverter genericConverter;
    private final VerificationTokenRepository verificationTokenRepository;
    private final ApplicationEventPublisher publisher;
    //private final OtpSmsRepository otpSmsRepository;
    //private final TwilioConfig twilioConfig;



    public ResponseEntity<?> checkEmail(String email) {
        try {
            if (userRepository.existsByEmail(email)) {
                return ResponseUtil.error("Email is already in use", "Failed", HttpStatus.BAD_REQUEST);
            }
            String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                return ResponseUtil.error("Form email not true", "False", HttpStatus.BAD_REQUEST);
            }
            User user = new User();
            user.setEmail(email);
            userRepository.save(user);
            UpsertUserDTO result = (UpsertUserDTO) genericConverter.toDTO(user, UpsertUserDTO.class);

            return ResponseUtil.getObject(result, HttpStatus.CREATED, "ok");
        } catch (ConstraintViolationException e) {
            return ConstraintViolationExceptionHandler.handleConstraintViolation(e);
        }
    }

    public String checkResetVerifyToken(String email, Long id) {
        try {
            User user = userRepository.findUserByEmail(email);
            VerificationToken verificationToken = verificationTokenRepository.findByUserId(id);
            if (user.getUserId().equals(id) && !user.isEnabled() && verificationToken == null) {
                return "True";
            } else {
                return "False";
            }
        } catch (ConstraintViolationException e) {
            return e.toString();
        }
    }

    public ResponseEntity<?> signin(SigninRequest signinRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getLogin(), signinRequest.getPassword()));
        } catch (Exception e) {
            return ResponseUtil.error("Email or Password not exist", "Login False", HttpStatus.BAD_REQUEST);
        }


        var user = userRepository.findByLogin(signinRequest.getLogin()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        UserDTO userDTO = convertUserToUserDTO(user);

        jwtAuthenticationResponse.setUserDTO(userDTO);
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return ResponseUtil.getObject(jwtAuthenticationResponse, HttpStatus.OK, "Sign in successfully");

    }

    public ResponseEntity<?> signinGoogle(String email) {
        try {
            Optional<User> userDetails = userRepository.findByLogin(email);
            User user = new User();
            if (userDetails.isEmpty()) {

                user.setEmail(email);
                user.setRole(Role.CUSTOMER);
                user.setEnabled(true);
                //UpsertUserDTO result = (UpsertUserDTO) genericConverter.toDTO(user, UpsertUserDTO.class);
                userRepository.save(user);
            } else {
                user = userRepository.findUserByEmail(email);
            }
            String jwt = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            UserDTO userDTO = convertUserToUserDTO(user);
            jwtAuthenticationResponse.setUserDTO(userDTO);
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            return ResponseUtil.getObject(jwtAuthenticationResponse, HttpStatus.OK, "Sign in successfully");

        } catch (Exception ex) {
            return ResponseUtil.error(ex.getMessage(), "Sign In Failed", HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByLogin(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return ResponseUtil.getObject(jwtAuthenticationResponse, HttpStatus.OK, "Token sent successfully");
        }
        return null;
    }

    private UserDTO convertUserToUserDTO(User entity) {
        UserDTO newUserDTO = (UserDTO) genericConverter.toDTO(entity, UserDTO.class);
        return newUserDTO;
    }

//  public ResponseEntity<?> checkPassword(String email, String password){
//    try{
//      User user = userRepository.findUserByEmail(email);
//      if(user == null){
//        return ResponseUtil.error("Email not exist","Failed", HttpStatus.BAD_REQUEST);
//      }
//      //check isenable
//      if(!user.isEnabled()){
//        return ResponseUtil.error("Please verify email before send password", "False", HttpStatus.BAD_REQUEST);
//      }
//      String regex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()])(?=.*[0-9]).{8,32}$";
//
//      Pattern pattern = Pattern.compile(regex);
//      Matcher matcher = pattern.matcher(password);
//
//      if (!matcher.matches()) {
//        return ResponseUtil.error("Password Invalid", "False", HttpStatus.BAD_REQUEST);
//      }
//      user.setPassword(passwordEncoder.encode(password));
//      UpsertUserDTO result = (UpsertUserDTO) genericConverter.toDTO(user, UpsertUserDTO.class);
//      userRepository.save(user);
//      return ResponseUtil.getObject(result, HttpStatus.CREATED, "ok");
//    }catch (ConstraintViolationException e) {
//      return ConstraintViolationExceptionHandler.handleConstraintViolation(e);
//    }
//  }


    public ResponseEntity<?> saveInfor(SignUp signUp) {
        try {
            User user = userRepository.findUserByEmail(signUp.getEmail());
            if (user == null) {
                return ResponseUtil.error("Email not exist", "Failed", HttpStatus.BAD_REQUEST);
            }
            //check isenable
            if (!user.isEnabled()) {
                return ResponseUtil.error("Please verify email before send password", "False", HttpStatus.BAD_REQUEST);
            }

            //user.setCountry(signUp.getCountry());
            String regexPassword = "^(?=.*[A-Z])(?=.*[!@#$%^&*()])(?=.*[0-9]).{8,32}$";

            Pattern patternPassword = Pattern.compile(regexPassword);
            Matcher matcherPassword = patternPassword.matcher(signUp.getPassword());

            if (!matcherPassword.matches()) {
                return ResponseUtil.error("Password Invalid", "False", HttpStatus.BAD_REQUEST);
            }

            String phone = signUp.getPhone();
            String regex = "^(\\+?\\d{1,4})?[-.\\s]?\\(?(\\d{2,3})\\)?[-.\\s]?\\d{3,4}[-.\\s]?\\d{3,4}$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phone);

            if (!matcher.matches()) {
                return ResponseUtil.error("Phone number must be 10 number", "False", HttpStatus.BAD_REQUEST);
            }
            user.setFullname(signUp.getFullname());
            user.setPhoneNumber(signUp.getPhone());
            user.setPassword(passwordEncoder.encode(signUp.getPassword()));
            user.setAddress(signUp.getAddress());
            user.setGender(signUp.getGender());
            //user.setGender(signUp.getGender());
            user.setImage(signUp.getImage());
            Date createDate = Date.from(Instant.now());
            user.setCreateDate(createDate);
            user.setRole(Role.CUSTOMER);
            user.setFcmToken(signUp.getFcmtoken());
            UpsertUserDTO result = (UpsertUserDTO) genericConverter.toDTO(user, UpsertUserDTO.class);
            userRepository.save(user);


            return ResponseUtil.getObject(result, HttpStatus.CREATED, "ok");
        } catch (ConstraintViolationException e) {
            return ConstraintViolationExceptionHandler.handleConstraintViolation(e);
        }
    }

    public ResponseEntity<?> checkEmailForgotPassword(String email) {
        try {
            if (userRepository.existsByEmail(email)) {
                return ResponseUtil.error("Email is already in use", "Failed", HttpStatus.BAD_REQUEST);
            }
            String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                return ResponseUtil.error("Form email not true", "False", HttpStatus.BAD_REQUEST);
            }
            User user = userRepository.findUserByEmail(email);
            try {
                publisher.publishEvent(new RegistrationCompleteEvent(user));
            } catch (Exception ex) {
                return ResponseUtil.error("Can't Send email", "False", HttpStatus.BAD_REQUEST);
            }

            UpsertUserDTO result = (UpsertUserDTO) genericConverter.toDTO(user, UpsertUserDTO.class);

            return ResponseUtil.getObject(result, HttpStatus.CREATED, "ok");
        } catch (ConstraintViolationException e) {
            //return ConstraintViolationExceptionHandler.handleConstraintViolation(e);
            return ResponseUtil.error(ConstraintViolationExceptionHandler.handleConstraintViolation(e).toString(), "False", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> changePassword(String email, String password) {
        try {
            User user = userRepository.findUserByEmail(email);
            if (user == null) {
                return ResponseUtil.error("Email not exist", "Failed", HttpStatus.BAD_REQUEST);
            }
            //check isenable
            if (!user.isEnabled()) {
                return ResponseUtil.error("Please verify email before save information", "False", HttpStatus.BAD_REQUEST);
            }

            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            UpsertUserDTO result = (UpsertUserDTO) genericConverter.toDTO(user, UpsertUserDTO.class);
            return ResponseUtil.getObject(result, HttpStatus.CREATED, "ok");

        } catch (ConstraintViolationException e) {
            return ResponseUtil.error(ConstraintViolationExceptionHandler.handleConstraintViolation(e).toString(), "False", HttpStatus.BAD_REQUEST);
        }
    }


}
