package com.ambrose.tripwonder.services;


import com.ambrose.tripwonder.dto.UpsertUserDTO;
import com.ambrose.tripwonder.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService {
  UserDetailsService userDetailsService();

  void saveUserVerificationToken(User theUser, String verificationToken);
  //void saveUserVerificationTokenSMS(User theUser, String token);

  String validateToken(String theToken, Long id);
  ResponseEntity<?> editProfile(UpsertUserDTO userDTO);
  ResponseEntity<?> getUsersByMonthAndYear(int month, int year, int page, int limit);
  ResponseEntity<?> getNumberOfUsersByMonthAndYear(int month, int year);
  //String validateTokenSms(String theToken, Long id);

  //ResponseEntity<?> findUserByRole(Role role, int page, int limit);
  //ResponseEntity<?> findById(Long id);
  //ResponseEntity<?> findAll(int page, int limit);

}
