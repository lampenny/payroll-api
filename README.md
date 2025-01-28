# Payroll REST API

Built with Spring Boot. Dependencies Spring Web, Spring Data JPA and H2 Database. This API utilises Spring HATEOAS which seamlessly add links to relevant operations inside the service. It has endpoints for employees and payments with CRUD actions.

Example endpoints:

**GET /employees/{id}** - Get an employee by id

```
{
  "id": 1,
  "firstName": "Betty",
  "lastName": "Suarez,
  "role": "personal assistant",
  "_links": {
    "self": {
      "href": "http://localhost:8080/employees/1"
    },
    "employees": {
      "href": "http://localhost:8080/employees"
    }
  }
}
```

**GET /payments** - Get a list of all payments with their status

```
{
  "_embedded": {
    "paymentList": [
      {
        "id": 1,
        "grossPay": "1,343",
        "status": "IN_PROGRESS",
        "_links": {
          "self": {
            "href": "http://localhost:8080/payments/1"
          },
          "payments": {
            "href": "http://localhost:8080/payments"
          },
          "cancel": {
            "href": "http://localhost:8080/payments/1/cancel"
          },
          "complete": {
            "href": "http://localhost:8080/payments/1/complete"
          }
        }
      },
      {
        "id": 2,
        "grossPay": "2,000",
        "status": "IN_PROGRESS",
        "_links": {
          "self": {
            "href": "http://localhost:8080/payments/2"
          },
          "payments": {
            "href": "http://localhost:8080/payments"
          },
          "cancel": {
            "href": "http://localhost:8080/payments/2/cancel"
          },
          "complete": {
            "href": "http://localhost:8080/payments/2/complete"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/payments"
    }
  }
}
```
