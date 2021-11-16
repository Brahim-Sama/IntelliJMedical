package fr.m2i.medical.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "patient", schema = "medical5", catalog = "")
public class PatientEntity {
    private int id;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String email;
    private String telephone;
    private String adresse;
    private VilleEntity ville;

    public PatientEntity() {
    }

    public PatientEntity(int id, String nom, String prenom, Date dateNaissance, String email, String telephone, String adresse, VilleEntity ville) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.ville = ville;
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
    @Column(name = "nom", nullable = false, length = 100)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "prenom", nullable = false, length = 100)
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Basic
    @Column(name = "date_naissance", nullable = false)
    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "telephone", nullable = false, length = 20)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "adresse", nullable = false, length = 100)
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientEntity that = (PatientEntity) o;
        return id == that.id && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && Objects.equals(dateNaissance, that.dateNaissance) && Objects.equals(email, that.email) && Objects.equals(telephone, that.telephone) && Objects.equals(adresse, that.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, dateNaissance, email, telephone, adresse);
    }

    @OneToOne
    @JoinColumn(name = "ville", referencedColumnName = "id", nullable = false)
    public VilleEntity getVille() {
        return ville;
    }

    public void setVille(VilleEntity ville) {
        this.ville = ville;
    }
}