package steps.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import dto.Book;
import framework.config.Settings;
import framework.core.Base;
import framework.core.WebElementHelper;
import framework.steps.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

@Slf4j
public class ApiSteps extends Base {

    private ScenarioContext scenarioContext;
    private  ObjectMapper objectMapper;
    public ApiSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
        objectMapper = new ObjectMapper();
    }


    @Given("[API] I have valid authentication credentials and baseurl")
    public void i_have_valid_authentication_credentials() {
        String username = System.getProperty("API_USERNAME");
        String password = System.getProperty("API_PASSWORD");
        String baseUrl = Settings.TESTED_APP_URL;

        if (username == null || password == null || baseUrl == null) {
            throw new IllegalStateException("API_USERNAME or API_PASSWORD or BASE_ULR environment variables are not set");
        }

        scenarioContext.setData("username", username);
        scenarioContext.setData("password", password);
        scenarioContext.setData("baseUrl", baseUrl);
    }

    @When("[API] I request all books from the API {} times")
    public void i_request_all_books_from_the_API_times(int times) {
        List<Response> responses = new ArrayList<>();
        String username = scenarioContext.getData("username").toString();
        String password = scenarioContext.getData("password").toString();
        String baseUlr = scenarioContext.getData("baseUrl").toString();
        String endpoint = "/v1/books";
        for (int i = 1; i <= times; i++) {
            long startTime = System.currentTimeMillis();

            Response response = given()
                    .auth()
                    .preemptive()
                    .basic(username, password)
                    .when()
                    .get(baseUlr + endpoint)
                    .then()
                    .extract()
                    .response();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            long responseTime = response.time();
            log.info("Iteration {}: Request execution time: {} ms, Response time (according to REST-assured): {} ms", i, duration, responseTime);
            responses.add(response);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        scenarioContext.setData("responses", responses);
    }

    @Then("[API] Each response should have a status code of 200")
    public void each_response_should_have_a_status_code_of_200() {
        List<Response> responses = (List<Response>) scenarioContext.getData("responses");
        if (responses == null || responses.isEmpty()) {
            throw new AssertionError("No responses available to validate.");
        }

        for (int i = 0; i < responses.size(); i++) {
            Response response = responses.get(i);
            int actualStatusCode = response.getStatusCode();
            assertEquals(
                    "Status code mismatch at iteration " + (i + 1),
                    200,
                    actualStatusCode
            );
        }
    }

    @And("[API] Each response should contain the expected JSON structure")
    public void each_response_should_contain_the_expected_JSON_structure() {
        List<Response> responses = (List<Response>) scenarioContext.getData("responses");
        if (responses.isEmpty()) {
            throw new AssertionError("No available responses for verification.");
        }
        for (int i = 0; i < responses.size(); i++) {
            Response response = responses.get(i);

            try {
                List<Book> actualBooks = objectMapper.readValue(
                        response.getBody().asString(), new TypeReference<List<Book>>() {
                        });
            } catch (IOException e) {
                throw new AssertionError("Failed to deserialize the response in iteration " + (i + 1) + ": " + e.getMessage());
            }

        }
    }

    @And("[API] I select a random book from the list")
    public void i_select_a_random_book_from_the_list() throws IOException {
        Book selectedBook;
        List<Response> responses = (List<Response>) scenarioContext.getData("responses");
        String responseBody = responses.get(0).getBody().asString();
        List<Book> books = objectMapper.readValue(responseBody, new TypeReference<List<Book>>() {
        });

        if (books.isEmpty()) {
            throw new AssertionError("The book list is empty.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(books.size());
        selectedBook = books.get(randomIndex);

        scenarioContext.setData("selectedBook", selectedBook);
        scenarioContext.setData("selectedBookId", selectedBook.getId());
        log.info("Selected book with ID:" + selectedBook.getId());
    }

    @When("[API] I request the book by its ID")
    public void i_request_the_book_by_its_ID() throws JsonProcessingException {
        String username = scenarioContext.getData("username").toString();
        String password = scenarioContext.getData("password").toString();
        String baseUrl = scenarioContext.getData("baseUrl").toString();
        int bookId = (int) scenarioContext.getData("selectedBookId");
        String endpoint = "/v1/books/" + bookId;

        Response response = given()
                .log().all()
                .auth()
                .preemptive()
                .basic(username, password)
                .when()
                .get(baseUrl + endpoint)
                .then()
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        Book bookById = objectMapper.readValue(responseBody, Book.class);
        scenarioContext.setData("bookById", bookById);
    }


    @Then("[API] The book data should match")
    public void the_book_data_should_match_the_data_from_the_first_GET() {
        Book selectedBook = (Book) scenarioContext.getData("selectedBook");
        Book bookById = (Book) scenarioContext.getData("bookById");

        assertEquals("The data of the book retrieved by ID does not match the data from the initial retrieval.", selectedBook, bookById);
    }


    @When("[API] I create a new book with generated details")
    public void i_create_a_new_book_with_generated_details() throws IOException {
        Faker faker = new Faker();

        String name = faker.book().title();
        String author = faker.book().author();
        String publication = faker.company().name();
        String category = faker.book().genre();
        int pages = faker.number().numberBetween(100, 1000);
        double rawPrice = faker.number().randomDouble(5, 10, 100);
        double price = Math.round(rawPrice * 100.0) / 100.0;

        Book newBook = new Book();
        newBook.setName(name);
        newBook.setAuthor(author);
        newBook.setPublication(publication);
        newBook.setCategory(category);
        newBook.setPages(pages);
        newBook.setPrice(price);

        String requestBody = objectMapper.writeValueAsString(newBook);
        String username = scenarioContext.getData("username").toString();
        String password = scenarioContext.getData("password").toString();
        String baseUrl = scenarioContext.getData("baseUrl").toString();
        String endpoint = "/v1/books";

        Response response = given()
                .log().all()
                .auth()
                .preemptive()
                .basic(username, password)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(baseUrl + endpoint)
                .then()
                .extract()
                .response();

        scenarioContext.setData("createBookResponse", response);
        scenarioContext.setData("newBookRequest", newBook);
    }

    @Then("[API] The book should be created successfully")
    public void the_book_should_be_created_successfully() throws JsonProcessingException {
        Response response = (Response) scenarioContext.getData("createBookResponse");
        String responseBody = response.getBody().asString();
        Book createdBook = objectMapper.readValue(responseBody, Book.class);

        scenarioContext.setData("createdBook", createdBook);
        int statusCode = response.getStatusCode();
        assertEquals("Expected status code 200, but received " + statusCode, 200, statusCode);
    }

    @Then("[API] The response should contain the correct book details")
    public void the_response_should_contain_the_correct_book_details() {
        Book createdBook = (Book) scenarioContext.getData("createdBook");

        Book expectedBook = (Book) scenarioContext.getData("newBookRequest");
        expectedBook.setId(createdBook.getId());
        String comparisonResult = compareBooks(expectedBook, createdBook);
        if (!comparisonResult.isEmpty()) {
            WebElementHelper.MarkScenarioAsFail("The book data in the response does not match the expected values:\n" + comparisonResult);
        }
    }

    private String compareBooks(Book expected, Book actual) {
        StringBuilder mismatches = new StringBuilder();

        if (expected.getId() != actual.getId()) {
            mismatches.append(String.format("ID mismatch: expected %d, but was %d\n", expected.getId(), actual.getId()));
        }
        if (!Objects.equals(expected.getName(), actual.getName())) {
            mismatches.append(String.format("Name mismatch, for book id: '%s', expected '%s', but was '%s'\n",expected.getId(), expected.getName(), actual.getName()));
        }
        if (!Objects.equals(expected.getAuthor(), actual.getAuthor())) {
            mismatches.append(String.format("Author mismatch, for book id: '%s' expected '%s', but was '%s'\n",expected.getId(), expected.getAuthor(), actual.getAuthor()));
        }
        if (!Objects.equals(expected.getPublication(), actual.getPublication())) {
            mismatches.append(String.format("Publication mismatch, for book id: '%s' expected '%s', but was '%s'\n",expected.getId(), expected.getPublication(), actual.getPublication()));
        }
        if (!Objects.equals(expected.getCategory(), actual.getCategory())) {
            mismatches.append(String.format("Category mismatch, for book id: '%s' expected '%s', but was '%s'\n",expected.getId(), expected.getCategory(), actual.getCategory()));
        }
        if (expected.getPages() != actual.getPages()) {
            mismatches.append(String.format("Pages mismatch, for book id: '%s' expected %d, but was %d\n",expected.getId(), expected.getPages(), actual.getPages()));
        }
        if (Double.compare(expected.getPrice(), actual.getPrice()) != 0) {
            mismatches.append(String.format("Price mismatch, for book id: '%s' expected %s, but was %s\n",expected.getId(), expected.getPrice(), actual.getPrice()));
        }

        return mismatches.toString();
    }

    @When("[API] I update the book's {} to a new value")
    public void i_update_the_book_s_field_to_a_new_value(String fieldToUpdate) throws IOException {
        String username = scenarioContext.getData("username").toString();
        String password = scenarioContext.getData("password").toString();
        String baseUrl = scenarioContext.getData("baseUrl").toString();

        Book selectedBook = (Book) scenarioContext.getData("selectedBook");
        int bookId = (int) scenarioContext.getData("selectedBookId");
        String endpoint = "/v1/books/" + bookId;
        Book updatedBook = selectedBook;
        updateBookField(updatedBook, fieldToUpdate);
        String requestBody = objectMapper.writeValueAsString(updatedBook);

        Response response = given()
                .log().all()
                .auth()
                .preemptive()
                .basic(username, password)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put(baseUrl + endpoint)
                .then()
                .extract()
                .response();

        scenarioContext.setData("updateBookResponse", response);
    }


    private void updateBookField(Book book, String fieldToUpdate) {
        Faker faker = new Faker();
        Map<String, Object[]> fieldChanges = new HashMap<>();
        switch (fieldToUpdate.toLowerCase()) {
            case "name":
                String oldName = book.getName();
                String newName = faker.book().title();
                book.setName(newName);
                fieldChanges.put("name", new Object[]{oldName, newName});
                break;
            case "author":
                String oldAuthor = book.getAuthor();
                String newAuthor = faker.book().author();
                book.setAuthor(newAuthor);
                fieldChanges.put("author", new Object[]{oldAuthor, newAuthor});
                break;
            case "publication":
                String oldPublication = book.getPublication();
                String newPublication = faker.company().name();
                book.setPublication(newPublication);
                fieldChanges.put("publication", new Object[]{oldPublication, newPublication});
                break;
            case "category":
                String oldCategory = book.getCategory();
                String newCategory = faker.book().genre();
                book.setCategory(newCategory);
                fieldChanges.put("category", new Object[]{oldCategory, newCategory});
                break;
            case "pages":
                int oldPages = book.getPages();
                int newPages = faker.number().numberBetween(100, 1000);
                book.setPages(newPages);
                fieldChanges.put("pages", new Object[]{oldPages, newPages});
                break;
            case "price":
                double oldPrice = book.getPrice();
                double rawPrice = faker.number().randomDouble(5, 10, 100);
                BigDecimal bdPrice = new BigDecimal(rawPrice).setScale(2, RoundingMode.HALF_UP);
                double newPrice = bdPrice.doubleValue();
                book.setPrice(newPrice);
                fieldChanges.put("price", new Object[]{oldPrice, newPrice});
                break;
            default:
                throw new IllegalArgumentException("Invalid field to update: " + fieldToUpdate);
        }
        scenarioContext.setData("fieldChanges", fieldChanges);
    }

    @Then("[API] The book should be updated successfully")
    public void the_book_should_be_updated_successfully() throws IOException {
        Response response = (Response) scenarioContext.getData("updateBookResponse");
        int statusCode = response.getStatusCode();
        log.info(response.asString());
        assertEquals("Expected status code 200, but received " + statusCode, 200, statusCode);
        Map<String, Object[]> fieldChanges = (Map<String, Object[]>) scenarioContext.getData("fieldChanges");
        StringBuilder changesLog = new StringBuilder("Field Changes:\n");

        if (fieldChanges != null) {
            for (Map.Entry<String, Object[]> entry : fieldChanges.entrySet()) {
                String fieldName = entry.getKey();
                Object[] values = entry.getValue();
                Object oldValue = values[0];
                Object newValue = values[1];
                changesLog.append(String.format("Updated field '%s': from '%s' to '%s'\n", fieldName, oldValue, newValue));
            }
            log.info(changesLog.toString());
        }

        String responseBody = response.getBody().asString();
        Book updatedBookResponse = objectMapper.readValue(responseBody, Book.class);
        scenarioContext.setData("updatedBookResponse", updatedBookResponse);
    }

    @When("[API] I update the book with new generated details")
    public void i_update_the_book_with_new_generated_details() throws IOException {
        String username = scenarioContext.getData("username").toString();
        String password = scenarioContext.getData("password").toString();
        String baseUrl = scenarioContext.getData("baseUrl").toString();

        int bookId = (int) scenarioContext.getData("selectedBookId");
        String endpoint = "/v1/books/" + bookId;

        Faker faker = new Faker();
        String name = faker.book().title();
        String author = faker.book().author();
        String publication = faker.company().name();
        String category = faker.book().genre();
        int pages = faker.number().numberBetween(100, 1000);
        double rawPrice = faker.number().randomDouble(5, 10, 100);
        BigDecimal bdPrice = new BigDecimal(rawPrice).setScale(2, RoundingMode.HALF_UP);
        double price = bdPrice.doubleValue();

        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setName(name);
        updatedBook.setAuthor(author);
        updatedBook.setPublication(publication);
        updatedBook.setCategory(category);
        updatedBook.setPages(pages);
        updatedBook.setPrice(price);

        String requestBody = objectMapper.writeValueAsString(updatedBook);
        Response response = given()
                .auth()
                .preemptive()
                .basic(username, password)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put(baseUrl + endpoint)
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        assertEquals("Expected status code 200, but received " + statusCode, 200, statusCode);

        scenarioContext.setData("updatedBook", updatedBook);
        log.info("Request updatedBook:\n"+updatedBook);
        log.info("Response updatebook:\n"+response.getBody().asString());
        scenarioContext.setData("updateBookResponse", response);
    }

    @Then("[API] The book's details should reflect the new values")
    public void the_book_s_details_should_reflect_the_new_values() throws IOException {
        Book expectedBook = (Book) scenarioContext.getData("updatedBook");

        String username = scenarioContext.getData("username").toString();
        String password = scenarioContext.getData("password").toString();
        String baseUrl = scenarioContext.getData("baseUrl").toString();
        int bookId = expectedBook.getId();
        String endpoint = "/v1/books/" + bookId;

        Response response = given()
                .auth()
                .preemptive()
                .basic(username, password)
                .when()
                .get(baseUrl + endpoint)
                .then()
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        Book actualBook = objectMapper.readValue(responseBody, Book.class);

        String comparisonResult = compareBooks(expectedBook, actualBook);

        if (!comparisonResult.isEmpty()) {
            WebElementHelper.MarkScenarioAsFail("The book details were not updated correctly:\n" + comparisonResult);
        }
    }

    @When("[API] I delete the selected book")
    public void i_delete_the_selected_book() {
        String username = scenarioContext.getData("username").toString();
        String password = scenarioContext.getData("password").toString();
        String baseUrl = scenarioContext.getData("baseUrl").toString();
        Book expectedBook = (Book) scenarioContext.getData("selectedBook");
        int bookId = expectedBook.getId();
        String endpoint = "/v1/books/" + bookId;

        Response response = given()
                .log().all()
                .auth()
                .preemptive()
                .basic(username, password)
                .when()
                .delete(baseUrl + endpoint)
                .then()
                .extract()
                .response();
        log.info(response.asString());
        scenarioContext.setData("deleteBookResponse", response);
    }

    @Then("[API] The book should be deleted successfully")
    public void the_book_should_be_deleted_successfully() {
        Response response = (Response) scenarioContext.getData("deleteBookResponse");
        int statusCode = response.getStatusCode();
        assertEquals("Expected status code 200, but received " + statusCode, 200, statusCode);
        log.info(response.getBody().asString());
    }

    @Then("[API] The book should no longer be retrievable by ID")
    public void the_book_should_no_longer_be_retrievable_by_id() {
        String username = scenarioContext.getData("username").toString();
        String password = scenarioContext.getData("password").toString();
        String baseUrl = scenarioContext.getData("baseUrl").toString();
        Book expectedBook = (Book) scenarioContext.getData("selectedBook");
        int bookId = expectedBook.getId();
        String endpoint = "/v1/books/" + bookId;

        Response response = given()
                .auth()
                .preemptive()
                .basic(username, password)
                .when()
                .get(baseUrl + endpoint)
                .then()
                .extract()
                .response();

        int statusCode = response.getStatusCode();
        assertEquals("Expected status code 404, but received " + statusCode, 404, statusCode);
    }
}