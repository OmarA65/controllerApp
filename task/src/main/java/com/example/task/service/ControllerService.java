package com.example.task.service;

import com.example.task.exception.ControllerNotFoundException;
import com.example.task.model.CameraDTO;
import com.example.task.model.ControllerDTO;
import com.example.task.model.camera;
import com.example.task.model.controller;
import com.example.task.repo.CameraRepo;
import com.example.task.repo.ControllerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ControllerService {
    private final ControllerRepo controllerRepo;
    private final CameraRepo cameraRepo;
    @Autowired
    public ControllerService(ControllerRepo controllerRepo, CameraRepo cameraRepo) {
        this.controllerRepo = controllerRepo;
        this.cameraRepo = cameraRepo;
    }

    public void addController(ControllerDTO controllerDTO) throws Exception {

        List<CameraDTO> cameraList = controllerDTO.getCamList();

        if(controllerDTO.getCamList().size() > 10) {throw new Exception("Camera devices in controller exceed 10 devices");}

        if(isValidIpFormat(controllerDTO.getIP()) == false) { throw new Exception("This IP is not in a valid format");}

        if(!isIpUnique(controllerDTO.getIP())) { throw new Exception("This IP is already taken");}

        controller controller = mapControllerDTOToEntity(controllerDTO);

        controller = controllerRepo.save(controller);
        final Long controllerId = controller.getUid();
        List<Long> cameraIds = cameraList.stream().map(x -> x.getUid()).collect(Collectors.toList());
        List<camera> cameraDbResult = cameraRepo.findAllById(cameraIds);
        cameraDbResult.stream().forEach(x -> x.setParentId(controllerId));
        //List<camera> cameras = mapCameraToEntity(cameraList, controller.getUid());

        cameraRepo.saveAll(cameraDbResult);
    }
    public boolean isValidIpFormat(String IP)
    {
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        Pattern p = Pattern.compile(regex);

        if (IP == null) {
            return false;
        }

        Matcher m = p.matcher(IP);
        return m.matches();
    }
    public boolean isIpUnique(String IP)
    {
        Long countIP = controllerRepo.countByIP(IP);
        countIP += cameraRepo.countByIP(IP);
        if(countIP>0) {
            return false;
        }else{
            return true;
        }
    }
    public controller mapControllerDTOToEntity(ControllerDTO controllerDTO){

        controller controller = new controller();

        controller.setSerialNum(UUID.randomUUID().toString());
        controller.setName(controllerDTO.getName());
        controller.setIP(controllerDTO.getIP());

        return controller;

    }
    public List<camera> mapCameraToEntity(List<CameraDTO> cameraDTOList, Long controllerID){

        List<camera> cameraList = new ArrayList<camera>();
        for(int i=0;i<cameraDTOList.size();i++)
        {
            camera cam = new camera();
            //cam.setVendor(cameraDTOList.get(i).getVendor());
            //cam.setIP(cameraDTOList.get(i).getIP());
            //cam.setStatus(cameraDTOList.get(i).isStatus());
            //cam.setDateCreated(new Date());
            cam.setParentId(controllerID);
            cameraList.add(cam);
        }

        return cameraList;

    }

    public List<ControllerDTO> findAllControllers(){


        List<controller> controllerList = controllerRepo.findAll();
        List<ControllerDTO> controllerDTOList = new ArrayList<>();

        for(int i=0;i<controllerList.size();i++)
        {
            controllerDTOList.add(mapEntityIntoControllerDTO(controllerList.get(i)));
            List<camera> cameraList = cameraRepo.findCameraByParentId(controllerList.get(i).getUid());
            List<CameraDTO> cameraDTOList = mapEntityIntoCameraDTO(cameraList);
            controllerDTOList.get(i).setCamList(cameraDTOList);

        }
        return controllerDTOList;

    }
    public ControllerDTO mapEntityIntoControllerDTO(controller controller)
    {
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setSerialNum(controller.getSerialNum());
        controllerDTO.setName(controller.getName());
        controllerDTO.setUid(controller.getUid());
        controllerDTO.setIP(controller.getIP());

        return controllerDTO;
    }
    public List<CameraDTO> mapEntityIntoCameraDTO(List<camera> cameraList)
    {
        List<CameraDTO> cameraDTOList = new ArrayList<>();
        for(int i=0;i<cameraList.size();i++)
        {
            CameraDTO cameraDTO = new CameraDTO();
            cameraDTO.setParentId(cameraList.get(i).getParentId());
            cameraDTO.setVendor(cameraList.get(i).getVendor());
            cameraDTO.setDateCreated(cameraList.get(i).getDateCreated());
            cameraDTO.setStatus(cameraList.get(i).isStatus());
            cameraDTO.setIP(cameraList.get(i).getIP());
            cameraDTOList.add(cameraDTO);
        }
        return cameraDTOList;
    }



    public void updateController(ControllerDTO controllerDTO, Long uid) throws Exception {
        Optional<controller> optionalController = controllerRepo.findById(uid);

        if(optionalController.isEmpty())
        {
            throw new Exception("Controller with id " + uid.toString() + " was not found");
        }
        if(controllerDTO.getCamList().size() > 10) {throw new Exception("Camera devices in controller exceed 10 devices");}

        if(isValidIpFormat(controllerDTO.getIP()) == false) { throw new Exception("This IP is not in a valid format");}
        controller controller = optionalController.get();
        if(!isIpUnique(controllerDTO.getIP()) && !controllerDTO.getIP().equals(controller.getIP())) { throw new Exception("This IP is already taken");}

        controller.setName(controllerDTO.getName());
        controller.setIP(controllerDTO.getIP());
        final Long controllerId = controller.getUid();
        List<camera> cameraList = cameraRepo.findCameraByParentId(controllerId);

        if(!cameraList.isEmpty()){

            cameraList.stream().forEach(x -> x.setParentId(null));
            cameraRepo.saveAll(cameraList);
        }
        List<CameraDTO> cameraDTOList = controllerDTO.getCamList();
        List<Long> cameraIds = cameraDTOList.stream().map(x -> x.getUid()).collect(Collectors.toList());
        List<camera> cameraDbResult = cameraRepo.findAllById(cameraIds);
        cameraDbResult.stream().forEach(x -> x.setParentId(controllerId));
        cameraRepo.saveAll(cameraDbResult);
        controllerRepo.save(controller);
    }

    public void deleteController(Long uid) throws Exception {

        Optional<controller> optional = controllerRepo.findById(uid);
        if(optional.isEmpty()) { throw new Exception("No controller with this ID found"); }
        controller controller = optional.get();
        List<camera> cameraList = cameraRepo.findCameraByParentId(controller.getUid());
        cameraList.stream().forEach(x -> x.setParentId(null));
        cameraRepo.saveAll(cameraList);
        controllerRepo.deleteById(uid);
    }

    public controller findControllerById(Long uid) {
        return controllerRepo.findById(uid).orElseThrow(
                () -> new ControllerNotFoundException("Controller with the serial number " + uid.toString() + " was not found"));
    }
}
