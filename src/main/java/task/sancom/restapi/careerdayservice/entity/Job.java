package task.sancom.restapi.careerdayservice.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import task.sancom.restapi.careerdayservice.entity.enumerated.JobType;
import task.sancom.restapi.careerdayservice.entity.enumerated.Status;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Table(name = "JOB")
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "job_id")
    private UUID jobId;

    @Column(name="date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "name")
    private String jobName;

    @Column(name="description")
    private String description;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private JobType type;

    @Column(name="other_jobtype")
    private String otherJobType;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="interview_date",unique = true)
    private ZonedDateTime interviewDate;

    @Column(name="interview_start_time")
    private ZonedDateTime interviewStartTime;

    @Column(name="interview_end_time")
    private ZonedDateTime intervieEndTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicantId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Applicant applicant;

    public Job() {
    }

    public UUID getId() {
        return jobId;
    }

    public void setId(UUID id) {
        this.jobId = id;
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

    public ZonedDateTime getIntervieEndTime() {
        return intervieEndTime;
    }

    public void setIntervieEndTime(ZonedDateTime intervieEndTime) {
        this.intervieEndTime = intervieEndTime;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
