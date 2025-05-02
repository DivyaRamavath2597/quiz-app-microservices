# quiz-app-microservices
## 📌 Overview

The Quiz App is a backend system built using Java Spring Boot and Microservices architecture, designed to handle quiz creation, question management, and result evaluation. Users interact with the application by sending HTTP requests through API testing tools such as Postman or similar clients.

When a user wants to take a quiz, they send a request to the system specifying the topic and the number of questions they want. The request is received by the API Gateway, which routes it to the appropriate microservice. The Quiz Service is responsible for creating the quiz. It doesn't store questions directly but communicates with the Question Service through a Feign client to fetch the required number of questions based on the given topic or difficulty.

The Question Service retrieves the questions from its own PostgreSQL database and sends them back to the Quiz Service, which then compiles and returns the complete quiz to the user via the API Gateway. After completing the quiz, the user submits their answers through another API request. The system evaluates the submitted answers, calculates the score, and returns the result to the user.

This approach ensures a smooth and efficient quiz-taking experience while maintaining a clean, scalable backend architecture.

## 🚀 Key Features

- **Microservices Architecture**  
The application is built using a microservices approach with independent services: `quiz-service`, `question-service`, `api-gateway`, and `service-registry`, allowing better scalability, maintainability, and performance.

- **API Gateway**  
The `api-gateway` handles all incoming requests. It acts as a single entry point for users and routes requests to the appropriate service, ensuring better routing and security.

- **Service Registry (Eureka Server)**  
The `service-registry` (Eureka Server) manages service discovery. It allows the services to dynamically register themselves and find each other, ensuring smooth communication between them.

- **Feign Client for Communication**  
The `quiz-service` communicates with the `question-service` using Feign Client. Feign simplifies the process of making HTTP requests between the services, ensuring seamless data transfer.

- **PostgreSQL Database for Each Service**  
Each microservice has its own dedicated PostgreSQL database to store quiz data and questions. This prevents bottlenecks and ensures each service can function independently.

- **Load Balancing**  
Services use load balancing via Eureka, which helps distribute traffic evenly across instances of each service, improving performance and fault tolerance.

---

## 💻 Technologies Used

- **Java 17** - Programming language
- **Spring Boot** - Framework for building the services
- **Spring Cloud Eureka** - Service registry for service discovery
- **Feign Client** - Simplifies communication between services
- **PostgreSQL** - Database for storing data
- **Spring Cloud Gateway** - API Gateway for routing requests
- **Maven** - Build tool
- **Postman** - API testing tool used to interact with the services


## 🚀 How to Run the Application

1. **Clone the Repository**  

2. **Set Up the Eureka Server**  
   - Go to the `service-registry` directory.
   - Start the Eureka Server to enable service discovery. This allows the microservices to register and discover each other.

2. **Run the Microservices**  
   - Run the `quiz-service` and `question-service` separately using Spring Boot.
   - Ensure that both services are registered with the Eureka server for seamless communication.

3. **Configure PostgreSQL Databases**  
   - Create separate PostgreSQL databases for the `quiz-service` and `question-service`.
   - Update the database configurations in the respective services (in `application.yml` or `application.properties`) to point to the correct databases.

4. **Start the Application**  
   - Once the Eureka server and the microservices are running, the API is accessible for creating, updating, and managing quizzes and questions.

## 🔮 Future Enhancements

- Implement **OAuth2 authentication** to secure the microservices.
- Enable a **Circuit Breaker** for improved fault tolerance and reliability.

You're welcome to contribute or suggest any improvements!




