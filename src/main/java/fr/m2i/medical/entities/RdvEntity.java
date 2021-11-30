package fr.m2i.medical.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rdv", schema = "medical5", catalog = "")
public class RdvEntity {
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC+1")
    private Timestamp dateheure;
    private Integer duree;
    private String note;

    private String type;
    private PatientEntity patient;

    public RdvEntity() {
    }

    public RdvEntity(Timestamp dateheure, Integer duree, String note, String type, PatientEntity patient) {
        this.dateheure = dateheure;
        this.duree = duree;
        this.note = note;
        this.type = type;
        this.patient = patient;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dateheure", nullable = true)
    public Timestamp getDateheure() {
        return dateheure;
    }

    public void setDateheure(Timestamp dateheure) {
        this.dateheure = dateheure;
    }

    @Basic
    @Column(name = "duree", nullable = true)
    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    @Basic
    @Column(name = "note", nullable = true, length = 225)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 100)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RdvEntity rdvEntity = (RdvEntity) o;
        return id == rdvEntity.id && Objects.equals(dateheure, rdvEntity.dateheure) && Objects.equals(duree, rdvEntity.duree) && Objects.equals(note, rdvEntity.note) && Objects.equals(type, rdvEntity.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateheure, duree, note, type);
    }

    @OneToOne
    @JoinColumn(name = "patient", referencedColumnName = "id")
    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }
}