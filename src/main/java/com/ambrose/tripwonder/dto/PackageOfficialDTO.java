package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.entities.Gallery;
import com.ambrose.tripwonder.entities.Province;
import com.ambrose.tripwonder.entities.RatingReview;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageOfficialDTO {
    @Setter
    private Long id;
    @Setter
    private String name;
    @Setter
    private Date date;
    @Setter
    private double price;
    @Setter
    private Date startTime;
    @Setter
    private Date endTime;
    @Setter
    private List<RatingReview> ratingReviews;
    @Setter
    private List<Gallery> gallery;
    private String province;

    public void setProvince(Province province) {
        this.province = province.getName();
    }
}
