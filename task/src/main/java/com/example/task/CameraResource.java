package com.example.task;

import com.example.task.model.camera;
import com.example.task.service.CameraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/camera")
public class CameraResource {
    private final CameraService cameraService;

    public CameraResource(CameraService cameraService){
        this.cameraService = cameraService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<camera>> getAllCameras(){
        List<camera> cameras = cameraService.findAllCameras();
        return new ResponseEntity<>(cameras, HttpStatus.OK);
    }

    @GetMapping("/find/{uid}")
    public ResponseEntity<camera> findCameraByUid(@PathVariable("uid") Long uid){
        camera camera = cameraService.findCameraByUid(uid);
        return new ResponseEntity<>(camera, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<camera> addCamera(@RequestBody camera camera){
        camera.setDateCreated(new Date());
        camera newCamera = cameraService.addCamera(camera);
        return new ResponseEntity<>(newCamera, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public  ResponseEntity<camera> updateCamera(@RequestBody camera camera){
        camera updatedCamera = cameraService.updateCamera(camera);
        return new ResponseEntity<>(updatedCamera, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{uid}")
    public  ResponseEntity<?> deleteCamera(@PathVariable("uid") Long uid){
        cameraService.deleteCamera(uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
