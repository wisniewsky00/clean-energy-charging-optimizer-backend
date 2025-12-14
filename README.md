# Clean Energy Charging Optimizer – Backend

Backend service for the **Clean Energy Charging Optimizer** application.  
It provides energy mix summaries and calculates the optimal electric vehicle charging window based on forecasted clean energy availability.

---

## Technologies

- Java 17
- Spring Boot
- Spring Web / WebFlux (WebClient)
- Maven
- JUnit 5 & Mockito

---

## Running the Application Locally

### Prerequisites
- Java 17+
- Maven

### Install dependencies & run the application

```bash
mvn clean install
mvn spring-boot:run
```

The backend will be available at:

```text
http://localhost:8080
```

---

## API Endpoints

### Get daily energy mix summary
```http
GET /api/energy/mix/summary?days=3
```

Returns aggregated energy mix data for the given number of days.

---

### Get optimized charging window
```http
GET /api/optimizer/clean/energy/optimize/window?chargingHoursLength=1
```

- `chargingHoursLength`: number of charging hours (1–6)
- Optimization is calculated for the **next 48 hours starting at midnight (UTC)**

---

## Running Tests

```bash
mvn test
```

Unit tests cover:
- Energy mix aggregation logic
- Charging optimization algorithm
- REST controllers

---

## External Data Source

Energy generation forecast data is fetched from:

- https://carbon-intensity.github.io/api-definitions/#get-intensity-from-to

---