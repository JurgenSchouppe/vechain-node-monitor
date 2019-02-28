#!/bin/bash

/usr/bin/docker run --rm -p 127.0.0.1:8080:8080 --name vechain-monitor -v /opt/docker/vechain-monitor:/config fundrequestio/vechain-node-monitor:master