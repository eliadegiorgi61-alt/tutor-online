FROM eclipse-temurin:17
COPY TutorServer.java .
RUN javac TutorServer.java
CMD ["java", "TutorServer"]
