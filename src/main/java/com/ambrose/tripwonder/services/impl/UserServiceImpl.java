package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.UserDTO;
import com.ambrose.tripwonder.entities.User;
import com.ambrose.tripwonder.entities.VerificationToken;
import com.ambrose.tripwonder.repository.UserRepository;
import com.ambrose.tripwonder.repository.VerificationTokenRepository;
import com.ambrose.tripwonder.services.UserService;
import java.util.Calendar;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
  public UserDetailsService userDetailsService(){
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username){
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
    if(token == null || !token.getUser().getUserId().equals(id)){
      return "Invalid verification token";
    }
    User user = token.getUser();
    Calendar calendar = Calendar.getInstance();
    Long to = token.getExpirationTime().getTime();
    long no = calendar.getTime().getTime();
    //Long time = (token.getTokenExpirationTime().getTime() - calendar.getTime().getTime());
    Long time = (to - no);
    if( time <= 0){
      tokenRepository.delete(token);
      return "Token already expired";
    }
    user.setEnabled(true);
    userRepository.save(user);
    tokenRepository.delete(token);
    return "Valid";
  }




  private void convertListUserToListUserDTO(List<User> users, List<UserDTO> result){
    for(User user : users){
      UserDTO newUserDTO = (UserDTO) genericConverter.toDTO(user, UserDTO.class);
      result.add(newUserDTO);
    }
  }


}
