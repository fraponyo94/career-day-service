package task.sancom.restapi.careerdayservice.entity;


import org.hibernate.annotations.CreationTimestamp;
import task.sancom.restapi.careerdayservice.entity.enumerated.Gender;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="JOBAPPLLCANT")
public class JobApplicant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "APPLICANTID")
    private UUID applicantId;

    @Column(name="FIRSTNAME")
    @NotNull
    private String firstName;

    @Column(name="LASTNAME")
    private String lastname;

    @Column(name="EMAIL",nullable = false)
    @Email
    @NotBlank
    private String email;

    @Column(name="PHONE",length = 20)
    @Pattern(regexp="(^$|[0-9]{10})")
    @Size(min=9,max=12)
    private int phoneNumber;

    @Column(name="GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name="STUDYPROGRAMME")
    private String studyProgramme;

    @Column(name="DATECREATED",insertable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime dateCreated;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="QUALIFICATIONS",nullable = false)
    private Qualification qualification;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "JOBINTERVIEWS")
    private Set<Job> jobInterviews;


    public JobApplicant() {
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

    public UUID getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(UUID applicantId) {
        this.applicantId = applicantId;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
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

    public Set<Job> getJobInterviews() {
        return jobInterviews;
    }

    public void setJobInterviews(Set<Job> jobInterviews) {
        this.jobInterviews = jobInterviews;
    }
}
