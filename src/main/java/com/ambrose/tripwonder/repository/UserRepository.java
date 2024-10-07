package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.User;
import com.ambrose.tripwonder.entities.enums.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("SELECT us FROM User us WHERE us.email LIKE :login")
  Optional<User> findByLogin(String login);
  Optional<User> findByPhoneNumber(String phone);
  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phone);
  User findByRole(Role role);

  @Query("SELECT us FROM User us WHERE us.email LIKE :email")
  User findUserByEmail(String email);

  @Query("SELECT us FROM User us WHERE us.userId = :id")
  User findUserById(Long id);
  @Query("SELECT us FROM User us WHERE us.phoneNumber LIKE :phone")
  User findUserByPhone(String phone);
  @Query("SELECT u FROM User u WHERE EXTRACT(MONTH FROM u.createDate) = :month AND EXTRACT(YEAR FROM u.createDate) = :year")
  List<User> findUsersByMonthAndYear(@Param("month") int month, @Param("year") int year, Pageable pageable);
  @Query("SELECT u FROM User u WHERE EXTRACT(MONTH FROM u.createDate) = :month AND EXTRACT(YEAR FROM u.createDate) = :year")
  List<User> findNumberOfUsersByMonthAndYear(@Param("month") int month, @Param("year") int year);

}
