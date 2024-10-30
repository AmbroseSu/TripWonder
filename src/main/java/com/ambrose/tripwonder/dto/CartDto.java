package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.entities.PackageTour;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
public class CartDto {
    private GenericConverter<PackageTourDTO> cartConverter = new GenericConverter<>(new ModelMapper());
    private GenericConverter<GalleryDto> galleryDtoGenericConverter = new GenericConverter<>(new ModelMapper());
    
    
    private long id;
    private Double totalPrice;
    private int quantity;

    @Setter(AccessLevel.NONE)
    private PackageTourDTO packageTour;
    @Setter(AccessLevel.NONE)
    private List<GalleryDto> galleryDtos;
    
    public void setPackageTour(PackageTour packageTour) {
        this.packageTour = cartConverter.toDTO(packageTour,PackageTourDTO.class);
        this.galleryDtos = packageTour.getGalleries().stream().map(x -> galleryDtoGenericConverter.toDTO(x,GalleryDto.class)).toList();
    }
    
    
}
