package com.ambrose.tripwonder.repository.specification;

import com.ambrose.tripwonder.entities.PackageTour;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class PackageSpecification {
    public static Specification<PackageTour> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }
    
    public  Specification<PackageTour> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) ->
                categoryId == null ? null : criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public  Specification<PackageTour> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    // Lọc theo khoảng giá
    public  Specification<PackageTour> priceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice != null && maxPrice != null)
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            if (minPrice != null)
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            else 
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
    
    
    public Specification<PackageTour> attendanceBetween(int minAttendance, int maxAttendance) {
        return (root, query, criteriaBuilder) ->{
            if(minAttendance == 0 && maxAttendance == 0) return null;
            if (minAttendance!=0 && maxAttendance != 0)
                return criteriaBuilder.between(root.get("attendance"), minAttendance, maxAttendance);
            if(minAttendance != 0)
                return criteriaBuilder.greaterThanOrEqualTo(root.get("attendance"), minAttendance);
            else 
                return criteriaBuilder.lessThanOrEqualTo(root.get("attendance"), maxAttendance);
        };
    }
}
