FROM centos:latest

RUN yum update -y && yum install java-1.8.0-openjdk -y

RUN mkdir -p /bobnet-cms

COPY target/cms-0.0.1-SNAPSHOT.jar /bobnet-cms/cms.jar

COPY start.sh /bobnet-cms/start.sh

USER root

CMD ["/bobnet-cms/start.sh"]