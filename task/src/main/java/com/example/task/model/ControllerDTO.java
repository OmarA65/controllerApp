package com.example.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControllerDTO {

    private Long uid;
    private String serialNum;
    private String name;
    private String IP;
    private List<CameraDTO> camList;
}
