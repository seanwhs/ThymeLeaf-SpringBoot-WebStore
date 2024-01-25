// ProductDto.java
package springboot_thymeleaf.webstore.model;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Service
public class ProductDto {
    
    @NotEmpty(message="Name is Required!!!")
    private String name;
    
    @NotEmpty(message="Brand is Required!!!")
    private String brand;
    
    @NotEmpty(message="Category is Required!!!")
    private String category;
    
    @Min(10)
    private double price;

    @Size(min=10, message="min 10 characters")
    @Size(max=2000, message="max 2000 characters")
    private String description;

    private MultipartFile imageFile;
}
