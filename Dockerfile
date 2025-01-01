# Step 1: Use the official Selenium standalone image with Chrome
FROM selenium/standalone-chrome:latest

# Step 2: Set up your project directory
WORKDIR /app

# Step 3: Copy your project files into the container
COPY . /app

# Step 4: Install unzip, download Gradle, and set up the environment
RUN apt-get update && apt-get install -y unzip && \
    mkdir -p /opt && \
    wget -q https://services.gradle.org/distributions/gradle-8.4-bin.zip -P /tmp && \
    ls -lh /tmp/gradle-8.4-bin.zip && \
    unzip /tmp/gradle-8.4-bin.zip -d /opt && \
    ln -s /opt/gradle-8.4/bin/gradle /usr/bin/gradle

# Step 5: Make Gradle wrapper executable
RUN chmod +x gradlew

# Step 6: Run tests or any other default command
CMD ["./gradlew", "test"]
