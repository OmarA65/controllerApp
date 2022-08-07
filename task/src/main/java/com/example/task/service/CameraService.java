package com.example.task.service;

import com.example.task.exception.ControllerNotFoundException;
import com.example.task.model.camera;
import com.example.task.model.controller;
import com.example.task.repo.CameraRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CameraService {
    private final CameraRepo cameraRepo;
@Autowired
    public CameraService(CameraRepo cameraRepo) {
        this.cameraRepo = cameraRepo;
    }

    public camera addCamera(camera camera){
        camera.setDateCreated(new Date());
        return cameraRepo.save(camera);
    }

    public List<camera> findAllCameras(){
        return cameraRepo.findAll();
    }

    public camera updateCamera(camera camera){
        return cameraRepo.save(camera);
    }

    public void deleteCamera(Long uid){
        cameraRepo.deleteCameraByUid(uid);
    }

    public camera findCameraByUid(Long uid) {
        return cameraRepo.findCameraByUid(uid).orElseThrow(
                () -> new ControllerNotFoundException("Camera with the uid " + uid + " was not found"));
    }
}
