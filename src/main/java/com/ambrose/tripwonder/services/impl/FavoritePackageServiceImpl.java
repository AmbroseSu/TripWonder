package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.CategoryDTO;
import com.ambrose.tripwonder.dto.PackageOfficialDTO;
import com.ambrose.tripwonder.dto.PackageTourDTO;
import com.ambrose.tripwonder.entities.Category;
import com.ambrose.tripwonder.entities.FavoritePackage;
import com.ambrose.tripwonder.entities.PackageTour;
import com.ambrose.tripwonder.entities.User;
import com.ambrose.tripwonder.repository.CategoryRepository;
import com.ambrose.tripwonder.repository.FavoritePackageRepository;
import com.ambrose.tripwonder.repository.PackageOfficialRepository;
import com.ambrose.tripwonder.repository.PackageTourRepository;
import com.ambrose.tripwonder.repository.UserRepository;
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
  private final PackageOfficialRepository packageOfficialRepository;
  private final UserRepository userRepository;
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
      List<PackageTour> packageTours =new ArrayList<>() ;
      for (FavoritePackage favoritePackage:
      favoritePackages
          .getContent()) {
        packageTours.add(favoritePackage.getPackageId());
      }
//          .stream()
//          .map(favoritePackage -> packageOfficialRepository.findPackageTourById(favoritePackage.getPackageId().getId()))
//          .collect(Collectors.toList());
      List<PackageOfficialDTO> packageTourDTOS = packageTours.stream().map(x -> (PackageOfficialDTO) genericConverter.toDTO(x,PackageOfficialDTO.class)).collect(
          Collectors.toList());
      return ResponseUtil.getCollection(packageTourDTOS, HttpStatus.OK, "Update Successfully", page, limit, favoritePackages.getTotalElements());
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<?> createFavoritePackage(Long userId, Long packageTourId) {
    try{
      FavoritePackage favoritePackageCheck = favoritePackageRepository.getFavoritePackageByUserIdAndPackageTourId(userId, packageTourId);
      PackageTour packageTour = packageTourRepository.getPackageTourById(packageTourId);
      User user = userRepository.findUserById(userId);
      if (packageTour == null) {
        return ResponseUtil.error("Package Tour not exists","Failed", HttpStatus.BAD_REQUEST);
      }
      if (user == null) {
        return ResponseUtil.error("User not exists","Failed", HttpStatus.BAD_REQUEST);
      }
      if (favoritePackageCheck != null) {
        return ResponseUtil.error("Favorite Package Tour not exists","Failed", HttpStatus.BAD_REQUEST);
      }
      FavoritePackage favoritePackage = new FavoritePackage();
      favoritePackage.setPackageId(packageTourRepository.getPackageTourById(packageTourId));
      favoritePackage.setUser(userRepository.findUserById(userId));
      favoritePackage.setIdDeleted(false);
      favoritePackageRepository.save(favoritePackage);
      return ResponseUtil.getObject("Success", HttpStatus.CREATED, "Successfully Create");
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  public List<PackageOfficialDTO> convertPackageTourtoPackageTourDTO(List<PackageTour> packageTours){
    List<PackageOfficialDTO> packageTourDTOS = new ArrayList<>();
    for (PackageTour packageTour : packageTours){
      PackageOfficialDTO result = (PackageOfficialDTO) genericConverter.toDTO(packageTour, PackageOfficialDTO.class);
      packageTourDTOS.add(result);
    }
    return packageTourDTOS;
  }




}
