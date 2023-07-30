# https://medium.com/gdgsrilanka/running-jenkins-on-docker-for-a-newbie-855ad376500b
# sudo docker build -t yourusername/jenkins .
# sudo docker run -d -p8080:8080 -v /var/run/docker.sock:/var/run/docker.sock yourusername/jenkins
FROM jenkins/jenkins:lts
USER root
RUN apt-get update
RUN curl -sSL https://get.docker.com/ | sh