# Setup

1. Crate Jenkins Image from [Dockerfile](./Dockerfile), this will give access to run a docker inside Dockerd Jenkins. From [article](https://medium.com/gdgsrilanka/running-jenkins-on-docker-for-a-newbie-855ad376500b):

   ```bash
   sudo docker build -t yourusername/jenkins .
   sudo docker run -d -p8080:8080 -v /var/run/docker.sock:/var/run/docker.sock yourusername/jenkins
   ```

2. Configure Credentials, add:

   - Docker Hub username/pass
   - Git ssh keys for [monorepo-example](https://github.com/whoisYeshua/monorepo-example)
     Helpfull links: [Setup SSH](https://levelup.gitconnected.com/setup-ssh-between-jenkins-and-github-e4d7d226b271), [YouTube Guide](https://www.youtube.com/watch?v=i9KLMQmvZmY&t=582s) and most important [“No ECDSA host key” error](https://community.jenkins.io/t/no-ecdsa-host-key-error-connecting-to-github/3652/4)
