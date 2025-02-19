package com.concept.talk.repository;

import com.concept.talk.entity.Payment;
import com.concept.talk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
	List<Payment> findByUser(User user);
}
