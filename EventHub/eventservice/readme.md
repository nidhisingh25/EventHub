# HUV20.O JAVA Final Assignment

Template Project for JAVA Track

    1.	Dockerfile: It has been updated for maven build.
    
    2.	In .gitlab-ci.yml pipeline: 
            a.	The pipeline has been configured to trigger once the PR is merged to “master” branch only.
            b.	It authenticates with Google cloud service account.
            c.	It calls for cloudbuild.yaml file for deploying the service in Cloud run.
            
    3.	cloudbuild.yaml has been designed to handle following steps.
            a.	Build the docker image with respect to the Dockerfile and tag the docker image as asia.gcr.io/<GCP_PROJECT_ID>/<GITLAB_USER_ID> 
            b.	Push the docker image to google cloud registry.
            c.	Deploy the docker image to Google cloud run as a container and create the endpoint of the spring boot application.
    4. Add the repo name as value in the key named as TAG withouth a space. Go to Settings-->CICD-->Variable--> update the value for KEY named as TAG.

# Application Endpoint:

    1.	The pipeline will get trigger automatically once the PR is merged to master branch. 
    2.	In Gitlab, go to CICD --> Pipeline --> latest pipeline number --> click on **deploy** and it prints the pipeline logs. 
    3.	The endpoint of the application will be reflecting in pipeline logs under step 2.
	        Ex:  Step #2: Service URL: https://tomuser-amxbp6pvia-as.a.run.app

    
# Note:
    1. Database has to be created and add the DB name, user, password in gitlab variables. 
    2. Login to Gitlab using Deloitte mail ID only.
    3. Dont push the .gitlab-ci.yml file to any public repository
    4. Dont change the content in the .gitlab-ci.yml Dockerfile cloudbuild.yaml
    5. To check the pipelines go to the CI/CD -> Pipelines
    6. Everytime when the code is pushed to the Master branch the pipeline will be triggered and will be deployed to the Cloudrun
    7. To access URL Go to CI/CD -> Pipelines -> Click on passed -> deploy -> In the terminal you can see service URL
    8. Make sure only working code is pushed to the Master 
    9. If Connection Reset Peer error came -: Search Services->Run as Admin-> Netskope->Stop/Disable it (or if OAuth issue came) 
    10. Connect to VPN-: To access URL of final deployed app if get an error of forbidden 
    11. Dont upload the endpoint URL to any social media, dont share the URL with anyone   
 

