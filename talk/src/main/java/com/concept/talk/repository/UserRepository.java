package com.concept.talk.repository;

import com.concept.talk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailToken(String emailToken);
	Optional<User> findByPhoneNumber(String phoneNumber);
	Optional<User> findByResetToken(String token);
}
