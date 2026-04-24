# w2076807-CSACW

# 1. Overview of Project.

This project is a RESTful API that I built using JAX-RS with Grizzly. It can manage rooms, sensors and sensor readings for a Smart Campus System. All data is stored in memory using Hashmaps and ArrayLists as required.


# 2. HOW TO RUN THE PROJECT:

1) Clone the repository.
   git clone https://github.com/your-username/smart-campus-api.git cd smart-campus-api
2) Build the project.
   mvn clean install
3) Run the Server.
   mvn exec:java
4) Access the API.
   Base URL:  http://localhost:8080/api/v1/

# 3. API Endpoints and curl Examples:

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
   curl http://localhost:8080/api/v1/rooms/LIB-301

4) Create a Sensor (Linked to a room)
   curl -X POST http://localhost:8080/api/v1/sensors \
   -H "Content-Type: application/json" \
   -d '{"id":"TEMP-001","type":"Temperature","roomId":"LIB-301"}'

5) Filter Sensors by Type
   curl http://localhost:8080/api/v1/sensors?type=Temperature

6) Get Sensor by ID
   curl http://localhost:8080/api/v1/sensors/TEMP-001

7) Add Sensor Reading
   curl -X POST http://localhost:8080/api/v1/sensors/TEMP-001/readings \
   -H "Content-Type: application/json" \
   -d '{"id":"r1","value":23.5}'

8) Get Sensor Readings
   curl http://localhost:8080/api/v1/sensors/TEMP-001/readings

9) Delete Room
    curl -X DELETE http://localhost:8080/api/v1/rooms/LIB-301

# 4. Key Features that I implemented:

   - Full CRUD for Rooms (GET, POST, DELETE)
   - Sensor creation with room validation
   - Query filtering using @QueryParam
   - Sub-resource locator for nested endpoints (/sensors/{id}/readings)
   - Automatic update of sensor currentValue when adding readings
   - Custom error handling using exceptions
   - Proper HTTP status codes (200, 201, 400, 404, 409)
  
# 5. Error Handling:

Custom Exceptions Implemented:
- RoomNotEmptyException -> 409 Conflict
- LinkedResourceNotFoundException -> 422 Unprocessable Entity
- SensorUnavailableException -> 403 Forbidden
All errors return structured JSON using ErrorResponse.

# 6. Answers to the Coursework Questions:

   Part 1:
   
   Question 1: JAX-RS Resource Lifecycle.
   
   By default, JAX-RS creates new instances from a resource class for each request. This helps prevent shared state issues inside the resource classes. In order to avoice concurrency issues, it is good to note that shared data structures like DataStore (HashMaps) have to be managed carefully.
      
   Question 2: HATEOAS.
   
   HATEOS allows clients to naviage an API using links in responses instead of hardcoding URLs. This helps improve the flexivility and reduces the dependency on external documentation.

      
   Part 2:

   Question 1: Returning IDs vs Full Objects.

   Returning only IDs can reduce payload size but it requires additional requests to be made. Returning full objects can increase response size but improves efficiency by reducing the API calls.

   Question 2: DELETE Idempotency.

   DELETE is idempotent because deleting the same resource multiple times can result in the same system states. After the first deletion, subsequent requests will return a "not found" response but do not change the system any further.


   Part 3:

   Question 1: @Consumes JSON.

   If a client sends data in a format other than JSON, JAX-RS returns a 415 Unsupported Media Type error because the API explicity expects JSON.

   Question 2: QueryParam vs Path.

   Query parameters are better for filtering because they can allow flexible queries without changing the resource structure. Path parameters are used for identifying some specific resources.


   Part 4:

   Question 1: Sub-Resource Locator.

   The sub-resource locator pattern can allow seperation of concerns by delegating nested resource logic to another class. This helps improve maintainability and keeps the code clean.

   Question 2: Data Consistency.

   When a new reading is being added, the sensor's currentValue updates to reflect the latest data so that it ensures consistency.


   Part 5:

   Question 1: HTTP 409 Conflict.

   Used when attempting to delete a room that still contains sensors.

   Question 2: HTTP 422 vs 404.

   HTTP 422 is more appropriate because the request is valid, however, it contains an invalid reference (roomId does not exist).

   Question 3: HTTP 403 Forbidden.

   This is used when a sensor is under maintenance and cannot accept readings.

   Question 4: Security Risks of Stack Traces.

   Exposing stack traces can reveal internal implementation details like class names and file paths, which attackers can exploit.

   Question 5: Logging with Filters.

   Using filters centralizes logging logic, avoids duplication and improves maintainability.

   # 7. Notes:

   - Uses Grizzly HTTP server which doesn't require an external server.
   - Uses Jersey (JAX-RS) for REST implementation.
   - No database used, everything uses in-memory storage.
   - JSON serialization is handled through Jackson.
