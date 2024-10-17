package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.CategoryDTO;
import com.ambrose.tripwonder.dto.PackageTourDTO;
import com.ambrose.tripwonder.entities.Category;
import com.ambrose.tripwonder.entities.FavoritePackage;
import com.ambrose.tripwonder.entities.PackageTour;
import com.ambrose.tripwonder.repository.CategoryRepository;
import com.ambrose.tripwonder.repository.FavoritePackageRepository;
import com.ambrose.tripwonder.repository.PackageTourRepository;
import com.ambrose.tripwonder.services.FavoritePackageService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoritePackageServiceImpl implements FavoritePackageService {

  private final FavoritePackageRepository favoritePackageRepository;
  private final PackageTourRepository packageTourRepository;
  private final GenericConverter genericConverter;

  @Override
  public ResponseEntity<?> getAllFavoritePackage(Long userId, int page, int limit) {
    try{
      Pageable pageable = PageRequest.of(page - 1, limit);
      Page<FavoritePackage> favoritePackages = favoritePackageRepository.getAllByUserID(userId, pageable);
//      List<PackageTour> packageTours = new ArrayList<>();
//      for (FavoritePackage favoritePackage : favoritePackages){
//        PackageTour packageTour = packageTourRepository.getPackageTourById(favoritePackage.getPackageId().getId());
//        packageTours.add(packageTour);
//      }
//      long count = packageTours.stream().count();
      List<PackageTour> packageTours = favoritePackages
          .getContent()
          .stream()
          .map(favoritePackage -> packageTourRepository.getPackageTourById(favoritePackage.getPackageId().getId()))
          .collect(Collectors.toList());
      List<PackageTourDTO> packageTourDTOS = convertPackageTourtoPackageTourDTO(packageTours);
      return ResponseUtil.getCollection(packageTourDTOS, HttpStatus.OK, "Update Successfully", page, limit, favoritePackages.getTotalElements());
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  public List<PackageTourDTO> convertPackageTourtoPackageTourDTO(List<PackageTour> packageTours){
    List<PackageTourDTO> packageTourDTOS = new ArrayList<>();
    for (PackageTour packageTour : packageTours){
      PackageTourDTO result = (PackageTourDTO) genericConverter.toDTO(packageTour, PackageTourDTO.class);
      packageTourDTOS.add(result);
    }
    return packageTourDTOS;
  }


}
