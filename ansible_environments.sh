#!/usr/bin/env bash
yum install epel-release

sed -i "s/enabled=1/enabled=0/g" /etc/yum.repos.d/epel.repo

yum install ansible --enablerepo=epel