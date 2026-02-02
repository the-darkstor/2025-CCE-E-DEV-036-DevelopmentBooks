# Development Books Discount Calculator
(by Thales Storino)

This project implements a pricing algorithm for a bookstore offering discounts based on the number of **different book titles** purchased together.

---

## Prerequisites

Make sure you have the following installed:

- Java 17 (or compatible)
- Maven
- An HTTP client (Postman, Insomnia, or curl)

---

## How to run the project

### 1. Open the project

Clone the repository or open the project in your IDE
(IntelliJ IDEA, Eclipse, VS Code, etc.).

---

### 2. Build the project

From the root directory of the project, run:

mvn clean install

This command will:
- compile the project
- run all unit tests
- verify that the application builds correctly

---

### 3. Run the application

Start the Spring Boot application with:

mvn spring-boot:run

Alternatively, you can run the main Spring Boot class directly from your IDE.

By default, the application starts on:

http://localhost:8080

---

## API Usage

### Endpoint

POST http://localhost:8080/api/calculateCart

---

### Request body

Send a JSON array representing the books in the cart.

Each object contains:
- title: the book identifier (A, B, C, D, E)
- quantity: the number of copies of that book

Example request body:

[
{
"title": "A",
"quantity": 2
},
{
"title": "B",
"quantity": 2
},
{
"title": "C",
"quantity": 2
},
{
"title": "D",
"quantity": 1
},
{
"title": "E",
"quantity": 1
}
]

You can freely change the quantity values to test different scenarios.



The output includes:
- discounted book sets
- non-discounted books (full price)
- discounted total
- non-discounted total
- final cart total

## Discount rules

- 2 different books → 5% discount
- 3 different books → 10% discount
- 4 different books → 20% discount
- 5 different books → 25% discount

Rules clarification:
- Discounts apply only to different titles
- Duplicate books do not increase the discount
- Remaining books not part of a discount set are charged at full price
- The algorithm always selects the most advantageous discount combination

---

## Algorithm behavior

The algorithm always chooses the optimal discount combination (for example, **4 + 4** instead of **5 + 3** when it results in a lower total price).

- Discount sets are built first using **distinct book titles**
- While building discount sets, book quantities are **decremented**
- When **five distinct titles** are available, the algorithm performs a **simulation step**:
    - It temporarily simulates taking a set of 5 books
    - If this would leave **exactly 3 distinct titles**, the algorithm avoids the suboptimal **5 + 3** case
    - In that situation, it builds a set of **4** instead, enabling a future **4 + 4** combination
- After all discount sets are built, remaining books are charged at **full price**
- The final cart price is calculated as:
  - final total = discounted total + non-discounted total

---

## Running tests

To run the unit tests separately:

mvn test

The test suite covers:
- all discount tiers
- edge cases (notably 4 + 4 vs 5 + 3)

---

## Technologies used

- Java
- Spring Boot
- Maven
- JUnit 5

---
