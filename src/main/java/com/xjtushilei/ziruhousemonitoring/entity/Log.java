package com.xjtushilei.ziruhousemonitoring.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * @author scriptshi
 * 2018/6/6
 */
@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date time;
    @Lob
    private String log;
    private String type;

    public Log() {
    }

    public Log(String log, String type) {
        this.log = log;
        this.type = type;
        this.time = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
