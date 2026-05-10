FROM ubuntu:20.04

RUN apt-get update
RUN apt-get install -y python2 bison flex build-essential
RUN update-alternatives --install /usr/bin/python python /usr/bin/python2 1

WORKDIR /roza

