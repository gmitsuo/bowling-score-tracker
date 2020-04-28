# Bowling Score Tracker 

### Required:
- JDK 11

### How to run:
- Tests:
    - `./mvnw clean test`
- Build:
    - `./mvnw clean package [-DskipTests]`. Jar available at `./target/bowling-score-1.0-SNAPSHOT.jar`.
- The app:
    - `java -jar ./target/bowling-score-1.0-SNAPSHOT.jar sample-input.txt`