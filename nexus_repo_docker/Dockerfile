FROM sonatype/nexus3:latest

# Copy our Groovy init scripts into Nexus
COPY nexus-init/ /opt/sonatype/nexus/etc/scripts/

# Expose Nexus port
EXPOSE 8081

# Use default Nexus entrypoint
