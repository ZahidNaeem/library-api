FROM nginx

EXPOSE 80

RUN rm /etc/nginx/conf.d/default.conf

COPY nginx.conf /etc/nginx/
COPY ./build-js/ /opt/library-ui/

ENTRYPOINT ["nginx","-g","daemon off;"]