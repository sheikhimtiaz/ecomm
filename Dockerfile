FROM maven:3.9.8-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
#Build skipping test, to include running test remove -DskipTests flag
RUN mvn clean package -DskipTests
RUN VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout) && \
    cp target/ecomm-$VERSION.jar /app/ecomm.jar


FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/ecomm.jar /app/ecomm.jar

EXPOSE 8080

# Set the entry point to run the jar file
ENTRYPOINT ["java", "-jar", "ecomm.jar"]
