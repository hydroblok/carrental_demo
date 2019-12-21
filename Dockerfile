FROM java:8-jre
MAINTAINER Jimmy Luo <jimmy.zw.luo@gmail.com>

ADD ./target/auth-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/car-rental-demo.jar"]

EXPOSE 8080