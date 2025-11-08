# Use official Sonatype Nexus 3 base image
FROM sonatype/nexus3:latest

# Set default admin credentials
ENV NEXUS_SECURITY_RANDOMPASSWORD=false
ENV NEXUS_USER=admin
ENV NEXUS_PASSWORD=admin

# Expose web UI and Docker registry ports
EXPOSE 8081

# Default start command
ENTRYPOINT ["/opt/sonatype/nexus/bin/nexus", "run"]
