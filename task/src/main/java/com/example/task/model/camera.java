package com.example.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class camera implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long uid;

    private Long parentId;
    private String vendor;
    private Date dateCreated;
    private boolean status;
    private String IP;

    @Override
    public String toString()
    {
        return "Camera{" +
                ", uid=" + uid + '\'' +
                ", vendor=" + vendor + '\'' +
                ", dateCreated" + dateCreated + '\'' +
                ", status" + status + '\'' +
                ", IP" + IP + '\'' +
                '}';
    }
}
