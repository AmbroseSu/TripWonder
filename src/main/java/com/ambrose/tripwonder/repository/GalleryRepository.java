package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
