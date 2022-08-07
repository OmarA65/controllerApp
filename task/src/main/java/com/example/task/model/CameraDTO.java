package com.example.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraDTO {

    private Long uid;
    private Long parentId;
    private String vendor;
    private Date dateCreated;
    private boolean status;
    private String IP;

}
