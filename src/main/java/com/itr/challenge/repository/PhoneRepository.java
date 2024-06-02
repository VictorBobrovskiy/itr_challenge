package com.itr.challenge.repository;

import com.itr.challenge.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    List<Phone> findAllByUserId(UUID userId);
}
