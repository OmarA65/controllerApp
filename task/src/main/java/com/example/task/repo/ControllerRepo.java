package com.example.task.repo;

import com.example.task.model.controller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ControllerRepo extends JpaRepository<controller, Long> {

    void deleteControllerBySerialNum(String SerialNum);
    Optional<controller> findControllerBySerialNum(String serialNum);

    Long countByIP(String IP);
}
