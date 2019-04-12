package task.sancom.restapi.careerdayservice.entity;

import task.sancom.restapi.careerdayservice.entity.enumerated.EducationLevel;
import task.sancom.restapi.careerdayservice.entity.enumerated.Gender;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name="APPLLCANT")
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "applicant_id")
    private UUID applicantId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastname;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private int phoneNumber;

    @Column(name = "education_level")
    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    @Column(name="years_of_experience")
    private int yearsOfExperience;

    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name="study_programme")
    private String studyProgramme;

    @Column(name="date_created")
    private ZonedDateTime dateCreated;


    public Applicant() {
    }

    public UUID getId() {
        return applicantId;
    }

    public void setId(UUID id) {
        this.applicantId = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStudyProgramme() {
        return studyProgramme;
    }

    public void setStudyProgramme(String studyProgramme) {
        this.studyProgramme = studyProgramme;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
