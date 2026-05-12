# Book Management REST API

A professional Spring Boot REST API for managing books with complete CRUD operations, validation, and error handling.

## Features

- ✅ Create, Read, Update, Delete books
- ✅ Search by title, author, price range
- ✅ Input validation (email, patterns, ranges)
- ✅ Global exception handling
- ✅ H2 in-memory database
- ✅ Professional layered architecture
- ✅ Proper HTTP status codes

## Tech Stack

- **Framework:** Spring Boot 3.5.14
- **Language:** Java 17
- **Database:** H2 (in-memory)
- **Build Tool:** Maven
- **API Testing:** Postman / Insomnia

## Project Structure

```
src/main/java/com/example/demo/
├── controller/          → REST endpoints
├── service/            → Business logic
├── repository/         → Database queries
├── entity/             → Database models
├── dto/                → Data validation & transfer
└── exception/          → Error handling
```

## Installation & Setup

### Prerequisites
- Java 17
- Maven 3.8.1+
- IntelliJ IDEA

### Steps

1. Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/book-management-api.git
cd book-management-api
```

2. Build the project
```bash
mvn clean install
```

3. Run the application
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## API Endpoints

### Get All Books
```
GET /api/v1/books
```

### Get Book by ID
```
GET /api/v1/books/{id}
```

### Search by Title
```
GET /api/v1/books/search/title?title=Clean
```

### Search by Author
```
GET /api/v1/books/search/author?author=Martin
```

### Search by Price Range
```
GET /api/v1/books/search/price?minPrice=10&maxPrice=50
```

### Create Book
```
POST /api/v1/books
Content-Type: application/json

{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "0132350882222",
  "price": 45.99,
  "description": "A Handbook of Agile Software Craftsmanship"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "0132350882222",
  "price": 45.99,
  "description": "A Handbook of Agile Software Craftsmanship",
  "createdAt": "2024-05-12T10:30:00",
  "updatedAt": "2024-05-12T10:30:00"
}
```

### Update Book
```
PUT /api/v1/books/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "price": 50.99
}
```

### Delete Book
```
DELETE /api/v1/books/{id}
```

## Example Responses

### Success (200 OK)
```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "price": 45.99
}
```

### Validation Error (400 Bad Request)
```json
{
  "status": 400,
  "error": "Validation Error",
  "message": "{title=Title cannot be blank, isbn=ISBN must be 13 digits}",
  "timestamp": "2024-05-12T10:30:00",
  "path": "/api/v1/books"
}
```

### Not Found (404)
```json
{
  "status": 404,
  "error": "Book Not Found",
  "message": "Book not found with ID: 99",
  "timestamp": "2024-05-12T10:30:00",
  "path": "/api/v1/books/99"
}
```

## Testing

Use **Postman** or **Insomnia** to test the API:

1. Download Postman: https://www.postman.com/downloads/
2. Create requests for each endpoint above
3. Test CRUD operations
4. Verify validation and error handling

## Learning Outcomes

By building this project, you'll understand:
- ✅ Spring Boot fundamentals
- ✅ REST API design principles
- ✅ MVC architecture pattern
- ✅ Spring Data JPA
- ✅ Dependency Injection
- ✅ Exception handling
- ✅ Input validation
- ✅ Professional code organization
- ✅ Git & GitHub workflow

## Project Statistics

- **Java Classes:** 9
- **Endpoints:** 11
- **Database Tables:** 1 (books)
- **Validation Rules:** 8+
- **HTTP Status Codes:** 5 (200, 201, 204, 400, 404)

## Author

Kranthi Kumar

## License

This project is licensed under the MIT License.

## Contact

For questions or feedback:
- GitHub: [@22kranthi](https://github.com/22kranthi)
- Email: sabavatkranthikumar@gmail.com

## ⭐ Don't Forget to Star!

If you find this project helpful, please **star it on GitHub**! ⭐

Stars help:
- ✅ Show appreciation for the work
- ✅ Help others discover the project
- ✅ Motivate future improvements

**[Click here to star the repository](https://github.com/22kranthi/book-management-api)** ⭐

---

**Happy Coding! 🚀**