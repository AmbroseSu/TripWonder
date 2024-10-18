package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.entities.User;
import lombok.*;
import org.modelmapper.ModelMapper;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingReviewDto {
    
    private final GenericConverter<UserDTO> genericConverter = new GenericConverter<>(new ModelMapper()) ;
    
    private String feedback;
    private int rating;
    private Date ratingDate;
    @Setter(AccessLevel.NONE)
    private UserDTO user; 
    
    public void setUser(User user) {
         this.user = (UserDTO) genericConverter.toDTO(user, UserDTO.class);
    }
}
