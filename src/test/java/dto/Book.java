package dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Book {
    private int id;
    private String name;
    private String author;
    private String publication;
    private String category;
    private int pages;
    private double price;

}
