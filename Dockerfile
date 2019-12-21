FROM java:8-jre
MAINTAINER Jimmy Luo <jimmy.zw.luo@gmail.com>

ADD ./target/car-rental-demo-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/car-rental-demo.jar"]

EXPOSE 8080