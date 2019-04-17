package task.sancom.restapi.careerdayservice.entity;


import task.sancom.restapi.careerdayservice.entity.enumerated.JobType;
import task.sancom.restapi.careerdayservice.entity.enumerated.Status;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Table(name = "JOB")
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "JOBID")
    private UUID jobId;

    @Column(name="DATECREATED")
    private ZonedDateTime dateCreated;

    @Column(name = "NAME",nullable = false)
    private String jobName;

    @Column(name="DESCRIPTION",nullable = false)
    private String description;

    @Column(name="TYPE")
    @Enumerated(EnumType.STRING)
    private JobType type;

    @Column(name="OTHERJOBTYPE")
    private String otherJobType;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="INTERVIEWDATE",unique = true,nullable = false)
    private ZonedDateTime interviewDate;

    @Column(name="INTERVIESTARTTIME",nullable = false)
    private ZonedDateTime interviewStartTime;

    @Column(name="INTERVIEWENDDATE",nullable = false)
    private ZonedDateTime interviewEndTime;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="QUALIFICATION",nullable = false)
    private Qualification qualification;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "APPLICANT", nullable = false)
    private Set<JobApplicant> jobApplicants;

    public Job() {
    }



    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public String getOtherJobType() {
        return otherJobType;
    }

    public void setOtherJobType(String otherJobType) {
        this.otherJobType = otherJobType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ZonedDateTime getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(ZonedDateTime interviewDate) {
        this.interviewDate = interviewDate;
    }

    public ZonedDateTime getInterviewStartTime() {
        return interviewStartTime;
    }

    public void setInterviewStartTime(ZonedDateTime interviewStartTime) {
        this.interviewStartTime = interviewStartTime;
    }

    public ZonedDateTime getInterviewEndTime() {
        return interviewEndTime;
    }

    public void setInterviewEndTime(ZonedDateTime interviewEndTime) {
        this.interviewEndTime = interviewEndTime;
    }

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public Set<JobApplicant> getJobApplicants() {
        return jobApplicants;
    }

    public void setJobApplicants(Set<JobApplicant> jobApplicants) {
        this.jobApplicants = jobApplicants;
    }
}
