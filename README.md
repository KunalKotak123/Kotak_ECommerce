# Kotak ECommerce Spring Boot Application

This is a Spring Boot application that requires Docker Desktop to be installed and running.
The application uses pre-configured Kafka, DynamoDB, and Redis images in test containers, so there is no need for additional setup.

The application uses Kafka for order processing effectively, DynamoDB for data storage, and Redis for caching.

## Prerequisites

- Docker Desktop
- Java Development Kit (JDK) 8 or later
- Spring Boot 2.x

## Building and Running the Application

To build and run the application, follow these steps:

- Step 1. Open a terminal or command prompt and navigate to the root directory of the application.
- Step 2. Run `mvn install` command to install required dependencies.

## Custom Configuration

All are pre configured but you are open to customise DynamoDB, Kafka and Redis configurations at `project/Config` directory.

## Troubleshooting

If you encounter any issues (200% you will not, if so..), try the following:

- Check the logs for any error messages.
- Ensure that Docker Desktop is running.

Hurray 🎉 
