FROM tomcat:9.0.43

ADD build/libs/amr-interpreation-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT/
RUN cd /usr/local/tomcat/webapps/ROOT && unzip amr-interpreation-0.0.1-SNAPSHOT.war && rm amr-interpreation-0.0.1-SNAPSHOT.war
ENV JPDA_ADDRESS="*:8000"
ENV JPDA_TRANSPORT="dt_socket"
ENV JPDA_OPTS="-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n"
ENV CATALINA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000"
EXPOSE 8080

CMD ["catalina.sh", "run"]
