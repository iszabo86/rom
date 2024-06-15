# Room Occupancy Manager (ROM)

## Quick start

> [!NOTE]
> The commands below are for Linux and macOS,
> on Windows please use `gradlew.bat` instead of `./gradlew`

### Build the application
```
./gradlew clean build --exclude-task test
```

### Run the tests
```
./gradlew test
```

### Run the application
```
./gradlew bootRun
```

The application available on port 8080

## REST API documentation
The REST API documentation is available here: http://localhost:8080/swagger-ui/index.html

## Sample curl request
```
curl -X 'POST' \
  'http://localhost:8080/room/occupancy/optimize' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "premiumRoomCount": 3,
  "economyRoomCount": 3,
  "guestOffers": [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]
}'
```