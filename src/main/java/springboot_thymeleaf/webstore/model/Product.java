package springboot_thymeleaf.webstore.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String brand;
    private String category;
    private double price;

    @Column(columnDefinition ="TEXT") //col will be varchar without this annotation
    private String description;

    private Date createdAt;

    @Column(name="image_file_name")
    private String imageFileName;
}
