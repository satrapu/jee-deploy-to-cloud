jee-deploy-to-cloud
=

Description
--
This applications was written in order to experiment with the ways a JEE web application must be configured to successfully deploy it to various (free) cloud providers like Heroku or OpenShift.
This repo contains a very simple JEE 7 servlet which will display some request related information.

Continuous Integration
--
* drone.io: [![Build Status](https://drone.io/github.com/satrapu/jee-deploy-to-cloud/status.png)](https://drone.io/github.com/satrapu/jee-deploy-to-cloud/latest)

Deployment
--
~~This application is deployed to different cloud providers using [wercker] (http://wercker.com/). This tool currently supports~~ ~~deploying applications to Heroku and OpenShift.~~ <b>Currently, wercker is not correctly setup, but I plan on fixing it shortly.</b>
<br/>
<br/>
This application is deployed to different cloud providers using their native support.
<br/>
* Heroku - link a GitHub repository with your Heroku account and enable automatic deployments from a specific branch (usually from master)
* OpenShift - add your OpenShift git repository URL as a remote on your local repository pointing to GitHub. Then, each time you want your changes from master branch to be deployed to OpenShift, use:
```bash
git push openshift master
```

Live Application
--
* Heroku: https://jee-deploy-to-cloud.herokuapp.com/status
* OpenShift: http://jee-satrapu.rhcloud.com/jee-deploy-to-cloud/status
