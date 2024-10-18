package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.entities.PackageTour;
import lombok.*;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
    
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final GenericConverter<PackageOfficialDTO> packageOfficialDTOConverter = new GenericConverter<>(new ModelMapper());
    
    private UUID id;
    private String name;
    private String contactEmail;
    private String contactPhone;
    private String address;
    private boolean status;
    @Setter(AccessLevel.NONE)
    private List<PackageOfficialDTO> tours;
    
    public void setTours(List<PackageTour> tours) {
        this.tours = tours
                .stream()
                .map(x -> packageOfficialDTOConverter.toDTO(x,PackageOfficialDTO.class))
                .collect(Collectors.toList());
    }
}
