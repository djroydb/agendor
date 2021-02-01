package com.agendor.testeagendor.model;


import com.agendor.testeagendor.model.enums.AssignmentType;


import org.joda.time.DateTime;

import java.util.Calendar;

public class Assignment implements Comparable<Assignment>{

    private Long id;
    private AssignmentType type;
    private DateTime date;
    private String client;
    private String description;
    private boolean done;

    public Assignment(){
    }

    public Assignment(AssignmentType type, DateTime date, String client, String description, boolean isDone){
        this.type = type;
        this.date = date;
        this.client = client;
        this.description = description;
        this.done = isDone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssignmentType getType() {
        return type;
    }

    public void setType(AssignmentType type) {
        this.type = type;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(Integer done) {
        this.done = done == 1;
    }

    public boolean isDone(){
        return done;
    }

    @Override
    public int compareTo(Assignment o) {
        if(this.date.isBefore(o.date)){
            return -1;
        }if (this.date.isAfter(o.getDate())){
            return 1;
        }
        return 0;
    }
}
