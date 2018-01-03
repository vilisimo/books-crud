FROM java:8
ADD build/libs/controller-vault.jar app.jar
ADD src/main/resources/application.yaml application.yaml
RUN sh -c 'touch /app.jar'
CMD ["java", "-jar", "app.jar", "server", "application.yaml"]
EXPOSE 8080
EXPOSE 8081