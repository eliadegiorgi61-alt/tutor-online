FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .
RUN javac TutorServer.java
EXPOSE 10000
CMD ["java", "TutorServer"]
