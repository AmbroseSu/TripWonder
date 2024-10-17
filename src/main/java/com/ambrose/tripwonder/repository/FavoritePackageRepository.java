package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.FavoritePackage;
import com.ambrose.tripwonder.entities.PackageTour;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritePackageRepository extends JpaRepository<FavoritePackage, String> {

  @Query("SELECT fp FROM FavoritePackage fp WHERE fp.user.userId = :userId")
  Page<FavoritePackage> getAllByUserID(@Param("userId") Long userId, Pageable pageable);

}
