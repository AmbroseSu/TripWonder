package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.UpsertUserDTO;
import com.ambrose.tripwonder.dto.request.SignUp;
import com.ambrose.tripwonder.repository.UserRepository;
import com.ambrose.tripwonder.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
  private final UserService userService;
  private final GenericConverter genericConverter;

  @PostMapping("/edit-profile")
  public ResponseEntity<?> saveInfor(@RequestBody UpsertUserDTO userDTO){
    return userService.editProfile(userDTO);
  }

  @GetMapping("/get-users-by-month-year")
  public ResponseEntity<?> getUsersByMonthAndYear(@RequestParam(value = "month") int month,
      @RequestParam(value = "year") int year,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int limit){
    return userService.getUsersByMonthAndYear(month, year, page, limit);
  }
  @GetMapping("/get-number-of-users-by-month-year")
  public ResponseEntity<?> getNumberOfUsersByMonthAndYear(@RequestParam(value = "month") int month,
      @RequestParam(value = "year") int year){
    return userService.getNumberOfUsersByMonthAndYear(month, year);
  }
}
