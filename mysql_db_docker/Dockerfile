FROM mysql:8.0

LABEL maintainer="yourname@example.com"
LABEL description="Custom MySQL 8.0 container with remote access"

ENV MYSQL_ROOT_PASSWORD=1234
ENV MYSQL_DATABASE=myapplication

# Allow remote connections
COPY my.cnf /etc/mysql/conf.d/my.cnf

# Initialize database and permissions
COPY ./mysql-init /docker-entrypoint-initdb.d/

EXPOSE 3306

CMD ["mysqld"]
