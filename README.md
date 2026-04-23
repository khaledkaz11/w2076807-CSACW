# w2076807-CSACW

1. Overview of Project.
This project is a RESTful API that I built using JAX-RS with Grizzly. It can manage rooms, sensors and sensor readings for a Smart Campus System. All data is stored in memory using Hashmaps and ArrayLists as required.


2. HOW TO RUN THE PROJECT:
1) Clone the repository.
   git clone https://github.com/your-username/smart-campus-api.git cd smart-campus-api
2) Build the project.
   mvn clean install
3) Run the Server.
   mvn exec:java
4) Access the API.
   Base URL:  http://localhost:8080/api/v1/

3. API Endpoints and curl Examples:
0) API Discovery Endpoint
   curl http://localhost:8080/api/v1/
Returns API metadata including versions and available endpoints.

1) Create a Room
   curl -X POST http://localhost:8080/api/v1/rooms \
   -H "Content-Type: application/json" \
   -d '{"id":"LIB-301","name":"Library","capacity":50}'

2) Get all Rooms
   curl http://localhost:8080/api/v1/rooms

3) Get Room by ID
