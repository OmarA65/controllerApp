package com.example.task.repo;

import com.example.task.model.camera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CameraRepo extends JpaRepository<camera, Long> {

    void deleteCameraByUid(Long uid);
    Optional<camera> findCameraByUid(Long uid);
    List<camera> findCameraByParentId(Long parentId);

    Long countByIP(String IP);

    Optional<List<camera>> findAllByParentId(Long controllerId);
}
