FROM openjdk:17
WORKDIR /app
COPY . .
RUN javac TutorServer.java
EXPOSE 10000
CMD ["java", "TutorServer"]
