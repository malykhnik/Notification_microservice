package com.malykhnik.notification_microservice.repository;

import com.malykhnik.notification_microservice.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailRepo extends JpaRepository<Email, UUID> {
}
