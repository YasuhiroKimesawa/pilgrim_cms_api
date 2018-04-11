#!/usr/bin/env bash

echo '
  if [ ! -e .ssh ]; then
    mkdir .ssh
    touch .ssh/authorized_keys
    chmod 700 .ssh
    chmod 600 .ssh/authorized_keys
  fi
' > script.sh

ssh root@$WEBSERVER_IP_ADDRESS -p $SSH_PORT 'bash -s' < script.sh
cat $SSH_PUBLIC_KEY_PATH | ssh root@$WEBSERVER_IP_ADDRESS -p $SSH_PORT 'cat >> .ssh/authorized_keys'
rm script.sh