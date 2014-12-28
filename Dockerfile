FROM dockerfile/java:oracle-java8

# Install maven
RUN apt-get update
RUN apt-get install -y maven graphviz

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml
# Adding source, compile and package into a fat jar
ADD khanasadiagramcreator-webapp /code/khanasadiagramcreator-webapp
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]
RUN ["mvn", "package"]

EXPOSE 4567
CMD ["java", "-jar", "/code/khanasadiagramcreator-webapp/target/khanasadiagramcreator-webapp-1.0-SNAPSHOT.jar"]
