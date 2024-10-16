package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.UpsertUserDTO;
import com.ambrose.tripwonder.dto.UserDTO;
import com.ambrose.tripwonder.entities.User;
import com.ambrose.tripwonder.entities.VerificationToken;
import com.ambrose.tripwonder.repository.UserRepository;
import com.ambrose.tripwonder.repository.VerificationTokenRepository;
import com.ambrose.tripwonder.services.UserService;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    //private final TravelReferencesRepository travelReferencesRepository;
    //private final UserTravelReferencesRepository userTravelReferencesRepository;
    private final GenericConverter genericConverter;
    //private final OtpSmsRepository otpSmsRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByLogin(username)
                        //.map(UserSignupDetails::new)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }


    @Override
    public String validateToken(String theToken, Long id) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if (token == null || !token.getUser().getUserId().equals(id)) {
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        long to = token.getExpirationTime().getTime();
        long no = calendar.getTime().getTime();
        //Long time = (token.getTokenExpirationTime().getTime() - calendar.getTime().getTime());
        long time = (to - no);
        if (time <= 0) {
            tokenRepository.delete(token);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.delete(token);
        return "Valid";
    }


    private void convertListUserToListUserDTO(List<User> users, List<UserDTO> result) {
        for (User user : users) {
            UserDTO newUserDTO = (UserDTO) genericConverter.toDTO(user, UserDTO.class);
            result.add(newUserDTO);
        }
    }

  public ResponseEntity<?> editProfile(UpsertUserDTO userDTO){
    try {

      User user = userRepository.findUserById(userDTO.getId());
      if(user == null){
        return ResponseUtil.error("User not exist","Failed", HttpStatus.BAD_REQUEST);
      }

      Field[] fields = UpsertUserDTO.class.getDeclaredFields();
      for(Field field : fields){
        field.setAccessible(true);
        if (field.getName().equals("id")) {
          continue;
        }
        if (field.getName().equals("email")) {
          continue;
        }
        if (field.getName().equals("role")) {
          continue;
        }
        Object newValue = field.get(userDTO);
        if(newValue != null){
          Field userField = User.class.getDeclaredField(field.getName());
          userField.setAccessible(true);
          userField.set(user, newValue);
        }
      }

      userRepository.save(user);
      UpsertUserDTO result = (UpsertUserDTO) genericConverter.toDTO(user, UpsertUserDTO.class);
      return ResponseUtil.getObject(result, HttpStatus.OK, "Update Successfully");

    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<?> getUsersByMonthAndYear(int month, int year, int page, int limit){
    try {
      Pageable pageable = PageRequest.of(page - 1, limit);
      List<User> users = userRepository.findUsersByMonthAndYear(month, year, pageable);
      List<UpsertUserDTO> upsertUserDTOS = new ArrayList<>();
      for (User user : users){
        UpsertUserDTO result = (UpsertUserDTO) genericConverter.toDTO(user, UpsertUserDTO.class);
        upsertUserDTOS.add(result);
      }
      long count = upsertUserDTOS.stream().count();
      return ResponseUtil.getCollection(upsertUserDTOS, HttpStatus.OK, "Update Successfully", page, limit, count);
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }
  public ResponseEntity<?> getNumberOfUsersByMonthAndYear(int month, int year){
    try {
      List<User> users = userRepository.findNumberOfUsersByMonthAndYear(month, year);
      long count = users.stream().count();
      return ResponseUtil.getObject(count, HttpStatus.OK, "Update Successfully");
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }


}
