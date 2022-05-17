# Order Service

## Swagger Data Contract
- http://localhost:8004/swagger-ui.html

## Step by Step to Use
- Start service [customer-data-service](https://github.com/adisaaa23/customer-data-service.git)
- Start service [products-data-service](https://github.com/adisaaa23/products-data-service.git)
- Start service [order-service](https://github.com/adisaaa23/order-service.git)
- Start service [transactions-data-service](https://github.com/adisaaa23/transactions-data-service.git)

- Get All Customers on customer-data-service
- Get All Products on products-data-service

### Step to Order
- Inquiry based on eligible product for customer with : 
Endpoint : "/orders/inquiry"
Request : {
  "amount": "100000",
  "productCode": "AA3",
  "username": "user1"
}
- Execute to Order with :
Endpoint : "/orders/execute"
Request : {
  "referenceNumber": "122312312312312",
  "username": "user1"
}

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3](https://maven.apache.org)
- [Kafka 2.13-3.1.1](https://www.apache.org/dyn/closer.cgi?path=/kafka/3.1.1/kafka_2.13-3.1.1.tgz)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

## Technology
### Spring Boot Kafka
