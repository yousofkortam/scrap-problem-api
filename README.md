
# XJudge Scrapping Service

XJudge Scrapping Service is a Java-based application that scrapes problem data from Codeforces. It uses Spring Boot for the backend and Maven for dependency management.

## API Endpoints

### Start Scrapping

**Endpoint:** `localhost:7071/problem/{source}-{code}`

**Method:** `GET`

**Description:** This endpoint starts the scrapping process for a specific problem from a contest on the online judge.

**Path Parameters:**

- `source`: The problem source (online judge) ex: CodeForces, AtCoder, etc ...
- `code`: The problem code in online judge.

**Response:** Returns a JSON object representing the `Problem` data. The JSON object has the following structure:

```json
{
    "id": "Long",
    "code": "String",
    "onlineJudge": "String",
    "title": "String",
    "problemLink": "String",
    "contestLink": "String",
    "sections": [
        {
            "title": "String",
            "value": {
                "format": "String",
                "content": "String as HTML"
            }
        }
    ],
    "properties": [
        {
            "name": "String",
            "value": "String"
        }
    ]
}
```

## Setup

To set up the project locally, follow these steps:

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Run `mvn clean install` to build the project and install dependencies.
4. Run the application using `mvn spring-boot:run`.

## Dependencies

- Java
- JavaScript
- Spring Boot
- Maven

## Contributing

Contributions are welcome. Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.