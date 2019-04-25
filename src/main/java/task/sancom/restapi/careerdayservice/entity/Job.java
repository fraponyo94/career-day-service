package task.sancom.restapi.careerdayservice.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.CreationTimestamp;

import task.sancom.restapi.careerdayservice.entity.enumerated.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


import java.time.ZonedDateTime;
import java.util.Date;
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

    @Column(name = "NAME",nullable = false,unique = true)
    @NotNull
    private String jobName;

    @Column(name="DESCRIPTION",nullable = false)
    @NotNull
    private String description;

    @Column(name="TYPE",nullable = false)
    @NotNull
    private String jobType;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="INTERVIEWDATE",unique =true)
    @NotNull(message = "Interview Date required")
    @Temporal(TemporalType.DATE)

    private Date interviewDate;

    @Column(name="INTERVIEWSTARTTIME")
    @NotNull(message = "Interview start time required")
    @Temporal(TemporalType.TIME)
    private Date interviewStartTime;

    @Column(name="INTERVIEWENDDATE",nullable = false)
    @NotNull(message = "Interview End time required")
    @Temporal(TemporalType.TIME)
    private Date interviewEndTime;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="QUALIFICATION",nullable = false)
    private Qualification qualification;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "JOBAPPLICANTS")
    private Set<JobApplicant> jobApplicants;

    public Job() {   }

    public Job(UUID jobId,@NotNull String jobName, @NotNull String description,String jobType, Date interviewDate, @NotNull(message = "Intervie start time required") Date interviewStartTime, @NotNull(message = "Interview End time required") Date interviewEndTime, Qualification qualification) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.jobType = jobType;
        this.description = description;
        this.interviewDate = interviewDate;
        this.interviewStartTime = interviewStartTime;
        this.interviewEndTime = interviewEndTime;
        this.qualification = qualification;
    }

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
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

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Date getInterviewStartTime() {
        return interviewStartTime;
    }

    public void setInterviewStartTime(Date interviewStartTime) {
        this.interviewStartTime = interviewStartTime;
    }

    public Date getInterviewEndTime() {
        return interviewEndTime;
    }

    public void setInterviewEndTime(Date interviewEndTime) {
        this.interviewEndTime = interviewEndTime;
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
