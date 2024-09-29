FROM amazoncorretto:21

RUN mkdir /opt/library
COPY ./library.jar /opt/library/library.jar

# Copy the font file into the container
COPY ./*.ttf /usr/share/fonts/truetype/

# Update the font cache
RUN fc-cache -f -v

EXPOSE 8082

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:1082","-Dspring.profiles.active=prod","-jar","/opt/library/library.jar"]