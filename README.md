# career-day-service
A Simple web service that mimics a Career day registration.Through this web service,
a participant can register to participate in an interview marathon by selecting a list of job positions they are interested in.

For testing this career service API,you are recommended to use post man. No security has been implemented therefore no Authentication required

## API Endpoints
### Job Applicant Endpoints
In the API,one can add/Read/Update/Delete JobApplicant resource using the following endpoints
#### POST http://localhost:8080/API/v1/applicant   //Creates JobApplicant Resource
      {
		  "firstName": "required",
		   "lastname": "required",
		   "email": "required",
		   "phoneNumber":"required",
		   "gender": "FEMALE/MALE", //Enum
		   "studyProgramme": "Bachelor of Arts",
		   "qualification":{
		   	
		    "educationLevel": "POSTGRADUATE/GRADUATE/UNDERGRADUATE/OTHER", //Enum
		    "yearsOfExperience": 1
		   }
		 
 #### PUT http://localhost:8080/API/v1/applicants/{applicantID} //Update JobApplicant Resource
 
 #### GET http://localhost:8080/API/v1/applicants //Find All Job Applicants
       "content": [
          {
              "applicantId": "4416a487-6179-4cea-a21d-cebed6512e4f",
              "firstName": "Andrew",
              "lastname": "Wesonga",
              "email": "andrewwesonga@gmail.com",
              "phoneNumber": 700000000000,
              "gender": "MALE",
            
              "studyProgramme": "Bachelor of Arts",
              "dateCreated": null,
              "qualification": {
                  "qualificationId": "b597a13f-5102-43a6-8bde-76ae3b1c062d",
                  "educationLevel": "GRADUATE",
                  "yearsOfExperience": 1
              },
              "jobInterviews": []
          }
      ],
      "pageable": {
          "sort": {
              "sorted": false,
              "unsorted": true,
              "empty": true
          },
          "offset": 0,
          "pageNumber": 0,
          "pageSize": 20,
          "paged": true,
          "unpaged": false
      },
      "last": true,
      "totalElements": 1,
      "totalPages": 1,
      "size": 20,
      "number": 0,
      "sort": {
          "sorted": false,
          "unsorted": true,
          "empty": true
      },
      "numberOfElements": 1,
      "first": true,
      "empty": false
  }
  
  
  #### GET http://localhost:8080/API/v1/applicants/{applicantID} //Get Job Applicant given job Id

          {
        "applicantId": "97f9f6f7-9a30-48a7-8c43-a4bfc1acc3db",
        "firstName": "Andrew",
        "lastname": "Wesonga",
        "email": "andrewwesonga@gmail.com",
        "phoneNumber": 700000000000,
        "gender": "MALE",
        "nationality": "Kenyan",
        "studyProgramme": "Bachelor of Arts",
        "dateCreated": "2019-04-23T23:22:18.87+03:00",
        "qualification": {
            "qualificationId": "ff3f02c9-debf-4849-97c6-c7f880d4d08f",
            "educationLevel": "GRADUATE",
            "yearsOfExperience": 1
        },
        "jobInterviews": null
        }
      
   #### PUT http://localhost:8080/API/v1/j/applicants/{applicantID}/select?id={jobID} //Select job interviews to enroll in
   
   #### PUT http://localhost:8080/API/v1/j/applicants/{jobApplicantId}/deselect?id={jobID} //deselect job interviews already enrolled in enroll
   
   
   #### DELETE http://localhost:8080/API/v1/applicants/{jobID}    //Delete job
   
   #### GET http://localhost:8080/API/v1/applicants/job-interviews?applicant-id={applicantId} //Job interviews an applicant has enrolled
   
   
   ## Jobs Endpoints 
   
   ####  POST http://localhost:8080/API/v1/job //Create a new job
           {
             "jobName": "WEB DEVELOPMENT2",
              "description": "Develop enterprise web based systems",
              "jobType": "API Engineer",
              "status":"OPEN/CLOSED", //Enum
              "interviewDate": "2019-04-26",
              "interviewStartTime":"22:52:52",
              "interviewEndTime": "23:52:52",
              "qualification":{
              	
               "educationLevel": "POSTGRADUATE/GRADUATE/UNDERGRADUATE/OTHER", //Enum
               "yearsOfExperience": 3
              }

#### GET http://localhost:8080/API/v1/jobs //View available jobs
           {
            "content": [
                {
                    "jobId": "fdcb3f5e-528d-4406-857b-c36760fe0bcd",
                    "dateCreated": null,
                    "jobName": "Api development",
                    "description": "Develop an api system",
                    "type": "APIEngineer",
                 
                    "status": "OPEN",
                    "interviewDate": "2019-04-23T23:22:18.87+03:00",
                    "interviewStartTime": "T23:23:18.87+03:00",
                    "interviewEndTime": "T23:23:18.87+03:00",
                    "qualification": {
                        "qualificationId": "ffad2a05-714c-4fe9-af47-36f696749c0d",
                        "educationLevel": "GRADUATE",
                        "yearsOfExperience": 1
                    },
                    "jobApplicants": []
                }
            ],
            "pageable": {
                "sort": {
                    "sorted": false,
                    "unsorted": true,
                    "empty": true
                },
                "offset": 0,
                "pageSize": 20,
                "pageNumber": 0,
                "unpaged": false,
                "paged": true
            },
            "totalElements": 1,
            "last": true,
            "totalPages": 1,
            "size": 20,
            "number": 0,
            "sort": {
                "sorted": false,
                "unsorted": true,
                "empty": true
            },
            "numberOfElements": 1,
            "first": true,
            "empty": false
        }
        
#### GET http://localhost:8080/API/v1/jobs/{jobId} //View available job by Id

         "content": [
                {
                    "jobId": "7cdda11f-3b9f-40e3-a1e2-d0c3ef97e0f6",
                    "dateCreated": null,
                    "jobName": "WEB DEVELOPMENT2",
                    "description": "Develop enterprise web based systems",
                    "jobType": "API Engineer",
                    "status": "OPEN",
                    "interviewDate": "09-10-0031",
                    "interviewStartTime": "01:52:52",
                    "interviewEndTime": "02:52:52",
                    "qualification": {
                        "qualificationId": "7a2f7061-4883-4d23-ab7e-b84175bcd560",
                        "educationLevel": "GRADUATE",
                        "yearsOfExperience": 3
                    },
                    "jobApplicants": []
                }
            ],
            "pageable": {
                "sort": {
                    "unsorted": true,
                    "sorted": false,
                    "empty": true
                },
                "offset": 0,
                "pageSize": 20,
                "pageNumber": 0,
                "unpaged": false,
                "paged": true
            },
            "totalElements": 1,
            "totalPages": 1,
            "last": true,
            "size": 20,
            "number": 0,
            "first": true,
            "sort": {
                "unsorted": true,
                "sorted": false,
                "empty": true
            },
            "numberOfElements": 1,
            "empty": false
        }
#### PUT http://localhost:8080/API/v1/jobs/{jobId}  //Update job resource 

#### DELETE http://localhost:8080/API/v1//jobs/{jobID}    //Delete job

#### GET http://localhost:8080/API/v1//jobs/{jobID}/participants  //View all Participants for a given job Returns Status 302 FOUND

#### GET http://localhost:8080/API/v1//jobs/search/{param = name,intervie-date,job-type,education-level,years-of-experience}
      
