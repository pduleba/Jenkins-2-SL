docker run ^
  -u root ^
  --rm ^
  -d ^
  -p 8080:8080 ^
  -p 50000:50000 ^
  -v D:/work-pduleba/programs/jenkins-blueocean/jenkins-data:/var/jenkins_home ^
  -v /var/run/docker.sock:/var/run/docker.sock ^
  -v D:/work-pduleba/programs/jenkins-blueocean/home:/home ^
  --name jenkins-blueocean ^
  jenkinsci/blueocean