package fr.m2i.medical.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ville", schema = "medical5", catalog = "")
public class VilleEntity {
    private int id;
    private String nom;
    private int codePostal;
    private String pays;

    public VilleEntity(String nom, int codePostal, String pays) {
        this.nom = nom;
        this.codePostal = codePostal;
        this.pays = pays;
    }

    public VilleEntity() {
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nom", nullable = false, length = 200)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "code_postal", nullable = false)
    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    @Basic
    @Column(name = "pays", nullable = false, length = 50)
    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VilleEntity that = (VilleEntity) o;
        return id == that.id && codePostal == that.codePostal && Objects.equals(nom, that.nom) && Objects.equals(pays, that.pays);
    }

    @Override
    public String toString() {
        return "VilleEntity{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", codePostal=" + codePostal +
                ", pays='" + pays + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, codePostal, pays);
    }
}