package task.sancom.restapi.careerdayservice.entity;

import task.sancom.restapi.careerdayservice.entity.enumerated.EducationLevel;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="QUALIFICATION")
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "QUALIFICATIONID")
    private UUID qualificationId;

    @Column(name = "EDUCATIONLEVEL",nullable = false)
    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    @Column(name = "YEARSOFEXPERIENCE",nullable = false)
    private int yearsOfExperience;


    public Qualification() {
    }

    public UUID getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(UUID qualificationId) {
        this.qualificationId = qualificationId;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}


