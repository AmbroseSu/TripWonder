package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.entities.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class PackageOfficialAdminDTO {
    
    @Getter(AccessLevel.NONE)
    private final GenericConverter<RatingReviewDto> converterRatingReview = new GenericConverter<RatingReviewDto>(new ModelMapper());
    @Getter(AccessLevel.NONE)
    private final GenericConverter<GalleryDto> converterGallery= new GenericConverter<GalleryDto>(new ModelMapper());

    private boolean status;
    private Long id;
    private String name;
    private double price;
    private String description;
    private String shortDescription;
    private Date startTime;
    private Date endTime;
    private int attendance;
    
    @Setter(AccessLevel.NONE)
    private String province;
    @Setter(AccessLevel.NONE)
    private List<RatingReviewDto> ratingReviews;
    @Setter(AccessLevel.NONE)
    private List<GalleryDto> galleries;
    @Setter(AccessLevel.NONE)
    private String category;
    private SupplierDTO supplier; 


    public void setProvince(Province province) {
        this.province = province.getName();
    }
    public void setRatingReviews(List<RatingReview> ratingReviews) {
        this.ratingReviews = ratingReviews.stream().
                map(ratingReview -> converterRatingReview.toDTO(ratingReview,RatingReviewDto.class))
                .collect(Collectors.toList());
    }
    public void setGalleries(List<Gallery> gallerys) {
        this.galleries = new ArrayList<>();
        for (Gallery gallery : gallerys) {
            if(!gallery.isDeleted()) {
                this.galleries.add(converterGallery.toDTO(gallery,GalleryDto.class));
            }
        }
    }
    
    public void setCategory(Category category) {
        this.category = category.getName();
    }
}
