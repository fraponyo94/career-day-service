package task.sancom.restapi.careerdayservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;
import task.sancom.restapi.careerdayservice.Validation.PhoneNumberConstraint;
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

    @Column(name="PHONE",length = 15)
    @Pattern(regexp = "[0|[+254]]+[7]\\d{8}", message = "please provide a valid phone number.")
    private String phoneNumber;

    @Column(name="GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;


    @Column(name="STUDYPROGRAMME")
    private String studyProgramme;

    @Column(name="DATECREATED",insertable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime dateCreated;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="QUALIFICATIONS",nullable = false)
    private Qualification qualification;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "JOBINTERVIEWS")
    private Set<Job> jobInterviews;


    public JobApplicant() {
    }

    public JobApplicant(@NotNull String firstName, @Email @NotBlank String email, Qualification qualification) {
        this.firstName = firstName;
        this.email = email;
        this.qualification = qualification;
    }

    public JobApplicant(UUID id,@NotNull String firstName, @Email @NotBlank String email, Qualification qualification) {
        this.applicantId =id;
        this.firstName = firstName;
        this.email = email;
        this.qualification = qualification;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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

    @Override
    public String toString() {
        return "JobApplicant{" +
                "firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
