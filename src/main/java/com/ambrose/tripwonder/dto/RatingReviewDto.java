package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.entities.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Data
@RequiredArgsConstructor
public class RatingReviewDto {
    
    private final GenericConverter<UserDTO> genericConverter;
    
    private String feedback;
    private int rating;
    private Date ratingDate;
    @Setter(AccessLevel.NONE)
    private UserDTO user; 
    
    public void setUser(User user) {
         this.user = (UserDTO) genericConverter.toDTO(user, UserDTO.class);
    }
}
