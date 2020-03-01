# Money Transfer for Revolut
>Design and implement a RESTful API (including data model and the backing implementation) for
 money transfers between accounts.

 Explicit requirements:
 1. You can use Java or Kotlin.
 2. Keep it simple and to the point (e.g. no need to implement any authentication).
 3. Assume the API is invoked by multiple systems and services on behalf of end users.
 4. You can use frameworks/libraries if you like (except Spring), but don't forget about
 requirement #2 and keep it simple and avoid heavy frameworks.
 5. The datastore should run in-memory for the sake of this test.
 6. The final result should be executable as a standalone program (should not require a
 pre-installed container/server).
 7. Demonstrate with tests that the API works as expected.
 
 ### Technologies
 - JAX-RS API
 - Dropwizard
 - H2 in memory database
 - Jetty Container
 - Java 8
 - Flyway for db migration
 - Maven as build tool
 - JUnit 4
 - REST Assured for integration tests

### Instructions
Run the following command to compile, test & package the Java project
```ruby
$ mvn clean compile install
```
then, to start the app, simply run the following command which will start the app on port 8080
```ruby
$ mvn compile exec:java
```
Running the following command will simply run the tests
```ruby
$ mvn test
```
### Available Endpoints

| HTTP METHOD | PATH | USAGE | QUERY PARAMS |
| ------------| ---- | ----- | ------------ |
| GET  | `/api/accounts` | list existing accounts | |
| GET  | `/api/account/{id}` | get account details by account id | |
| GET  | `/api/account/{id}/transfers` | list transfers associated with an account | |
| POST | `/api/account/update/{id}` | update the balance on an account | `balance`|
| POST | `/api/account/create` | create an account | `balance`|
| POST | `/api/transfer` | transfer funds from one account to another | 
| GET  | `/api/transfer/{id}` | get transfer by id | |

### Example Usage
#### 
You can get the list of existing accounts in the system by hitting the `/api/accounts` endpoint
```
curl -X GET localhost:8080/api/accounts

[
    {
        "id": "177e41d3-cb1b-4426-aaea-3d3d5623ac6c",
        "balance": 1000.0,
        "creationDate": 1583019488.343000000
    },
    {
        "id": "a933d974-089e-46ef-ab72-91897b47977b",
        "balance": 340.0,
        "creationDate": 1583019488.343000000
    },
    {
        "id": "e2a77256-09a4-4910-b646-d27614829abd",
        "balance": 700.0,
        "creationDate": 1583019488.343000000
    }
]
```

To get the details of a specific account use the `/api/account/{id}` endpoint
```
curl -X GET localhost:8080/api/account/177e41d3-cb1b-4426-aaea-3d3d5623ac6c
{
    "id": "177e41d3-cb1b-4426-aaea-3d3d5623ac6c",
    "balance": 1000.0,
    "creationDate": 1583019488.343000000
}
```

To get all the transfers associated with a specific account, inbound or outbound, use the `/api/account/{id}/transfers` endpoint
```
curl -X GET localhost:8080/api/account/177e41d3-cb1b-4426-aaea-3d3d5623ac6c/transfers
[
    {
        "id": "45803ac4-d9a2-44a7-ac88-ed95b51b6ec0",
        "sourceAccountId": "177e41d3-cb1b-4426-aaea-3d3d5623ac6c",
        "destinationAccountId": "e2a77256-09a4-4910-b646-d27614829abd",
        "amount": 200.0
    },
    {
        "id": "19761b85-492a-404c-987c-29bb8349dca3",
        "sourceAccountId": "a933d974-089e-46ef-ab72-91897b47977b",
        "destinationAccountId": "177e41d3-cb1b-4426-aaea-3d3d5623ac6c",
        "amount": 40.0
    }
]
```

To update the balance on a specific account, simply hit the `/api/account/update/{id}` and the endpoint will return the account with the updated amount
```$xslt
curl -X POST localhost:8080/api/account/update/177e41d3-cb1b-4426-aaea-3d3d5623ac6c?balance=2000

{
    "id": "177e41d3-cb1b-4426-aaea-3d3d5623ac6c",
    "balance": 2000.0,
    "creationDate": 1583019488.343000000
}
```

To create a new account, use the `/api/account/create` endpoint which will return the details of the new account

```
curl -X POST localhost:8080/api/account/create?balance=100

{
    "id": "e424d900-6e45-48e9-bbe5-359fd276c303",
    "balance": 100.0,
    "creationDate": 1583024623.352000000
}
```

To transfer funds between accounts, use the `/api/transfer` endpoint which will return the details of the transfer if successful

```$xslt
curl -X POST localhost:8080/api/transfer --header "Content-Type: application/json"\
 --data-raw "{\"sourceAccountId\": \"177e41d3-cb1b-4426-aaea-3d3d5623ac6c\",\"destinationAccountId\": \"e2a77256-09a4-4910-b646-d27614829abd\",\"amount\": 100.0}"

{
    "id": "3dbe9e76-50bf-429c-b3a2-3588d8f04295",
    "sourceAccountId": "177e41d3-cb1b-4426-aaea-3d3d5623ac6c",
    "destinationAccountId": "e2a77256-09a4-4910-b646-d27614829abd",
    "amount": 100.0
}
```