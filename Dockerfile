FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .
RUN javac --add-modules jdk.httpserver TutorServer.java
EXPOSE 10000
CMD ["java", "--add-modules", "jdk.httpserver", "TutorServer"]
