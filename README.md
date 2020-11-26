# Spring Boot Bank #

Example of Spring Boot application. 

To build this application, I integrated the Starling Bank API:

https://developer.starlingbank.com/

## How to run the application ##

- Gradle:

```
 ./gradlew bootRun
 ```

 - Docker:

```
 docker build -t springbootbank .
 docker run -p 8080:8080 springbootbank
 ```

 ## How to run the tests and build the app ##

```
./gradlew test
./gradlew build
```

## Endpoint ##

GET http://localhost:8080/starling/round-up

**Example:**

```curl localhost:8080/starling/round-up -H "UserToken: {access_token}"```

**Response:**

```json
{
    "savingsGoalUid": "891eefe2-f058-4c86-8989-9c928548a54f",
    "savingsName": "Starling Test",
    "totalAmount": 40.79,
    "currency": "GBP"
}
```

The UserToken needs to be refreshed/generated on Starling Bank Developers Account.

Generate a new access token and replace it in the header "UserToken: {access_token}"
