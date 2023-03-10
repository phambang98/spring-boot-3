docker stop $(docker ps -a -q --filter ancestor='spring-boot-3.jar' )
docker rm  -f $(docker ps -a -q --filter ancestor=spring-boot-3.jar )
docker rmi $(docker images 'spring-boot-3.jar' -a -q)
docker build -t spring-boot-3.jar .
docker run -d -p 8988:8988 spring-boot-3.jar



