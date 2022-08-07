package com.example.task;

import com.example.task.model.ControllerDTO;
import com.example.task.model.controller;
import com.example.task.service.ControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/controller")
public class ControllerResource {
    private final ControllerService controllerService;

    public ControllerResource(ControllerService controllerService){
        this.controllerService = controllerService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<ControllerDTO>> getAllControllers(){
        List<ControllerDTO> controllers = controllerService.findAllControllers();
        return new ResponseEntity<>(controllers, HttpStatus.OK);
    }

    @GetMapping("/find/{uid}")
    public ResponseEntity<controller> findControllerBySerialNum(@PathVariable("uid") Long uid){

        controller controller = controllerService.findControllerById(uid);
        return new ResponseEntity<>(controller, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<controller> addController(@RequestBody ControllerDTO controller) throws Exception {
        controllerService.addController(controller);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{uid}")
    public  ResponseEntity updateController(@RequestBody ControllerDTO controller,@PathVariable(("uid")) Long uid) throws Exception {
        controllerService.updateController(controller, uid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uid}")
    public  ResponseEntity<?> deleteController(@PathVariable("uid") Long uid) throws Exception {
        controllerService.deleteController(uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
