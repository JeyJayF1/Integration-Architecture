package de.hbrs.ia.model;

import org.bson.Document;

import java.time.Year;
import java.util.Date;

public class SocialPerformanceRecord{

    private Integer leadership;
    private Integer openness;
    private Integer behaviour;
    private Integer attitude;
    private Integer communication;
    private Integer integrity;
    private Integer year;

    public SocialPerformanceRecord(int leadership, int openness, int behaviour,
                                   int attitude, int communication, int integrity,
                                   int year){
        this.leadership = leadership;
        this.openness = openness;
        this.behaviour = behaviour;
        this.attitude = attitude;
        this.communication = communication;
        this.integrity = integrity;
        this.year = year;
    }
    public Integer getLeadership() {
        return leadership;
    }

    public void setLeadership(Integer leadership) {
        this.leadership = leadership;
    }

    public Integer getOpenness() {
        return openness;
    }

    public void setOpenness(Integer openness) {
        this.openness = openness;
    }

    public Integer getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(Integer behaviour) {
        this.behaviour = behaviour;
    }

    public Integer getAttitude() {
        return attitude;
    }

    public void setAttitude(Integer attitude) {
        this.attitude = attitude;
    }

    public Integer getCommunication() {
        return communication;
    }

    public void setCommunication(Integer communication) {
        this.communication = communication;
    }

    public Integer getIntegrity() {
        return integrity;
    }

    public void setIntegrity(Integer integrity) {
        this.integrity = integrity;
    }
    public Document toDocument() {
        org.bson.Document document = new Document();
        document.append("leadership", this.leadership);
        document.append("openness", this.openness);
        document.append("behaviour", this.behaviour);
        document.append("attitude", this.attitude);
        document.append("communication", this.communication);
        document.append("integrity", this.integrity);
        document.append("year", this.year);
        return document;
    }


}
