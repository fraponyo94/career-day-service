package task.sancom.restapi.careerdayservice.entity;


import org.hibernate.annotations.CreationTimestamp;
import task.sancom.restapi.careerdayservice.entity.enumerated.JobType;
import task.sancom.restapi.careerdayservice.entity.enumerated.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @Column(name="DATECREATED",insertable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime dateCreated;

    @Column(name = "NAME",nullable = false)
    @NotNull
    private String jobName;

    @Column(name="DESCRIPTION",nullable = false)
    @NotNull
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
    @NotNull(message = "Interview Date required")
    private ZonedDateTime interviewDate;

    @Column(name="INTERVIEWSTARTTIME",nullable = false)
    @NotNull(message = "Intervie start time required")
    private ZonedDateTime interviewStartTime;

    @Column(name="INTERVIEWENDDATE",nullable = false)
    @NotNull(message = "Interview End time required")
    private ZonedDateTime interviewEndTime;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="QUALIFICATION",nullable = false)
    private Qualification qualification;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "JOBAPPLICANTS")
    private Set<JobApplicant> jobApplicants;

    public Job() {   }

    public Job(UUID jobId,@NotNull String jobName, @NotNull String description, @NotNull(message = "Interview Date required") ZonedDateTime interviewDate, @NotNull(message = "Intervie start time required") ZonedDateTime interviewStartTime, @NotNull(message = "Interview End time required") ZonedDateTime interviewEndTime, Qualification qualification) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.description = description;
        this.interviewDate = interviewDate;
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.qualification = qualification;
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
