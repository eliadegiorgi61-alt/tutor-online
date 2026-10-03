FROM eclipse-temurin:17
WORKDIR /app
COPY..
RUN javac TutorServer.java
CMD ["java", "TutorServer"]
