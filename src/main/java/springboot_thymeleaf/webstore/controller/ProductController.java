// ProductController.java
package springboot_thymeleaf.webstore.controller;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import springboot_thymeleaf.webstore.model.Product;
import springboot_thymeleaf.webstore.model.ProductDto;
import springboot_thymeleaf.webstore.repository.ProductsRepository;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductsRepository productsRepository;
    private ProductDto productDto;

    public ProductController(ProductsRepository productsRepository, ProductDto productDto){
        this.productsRepository=productsRepository;
        this.productDto = productDto;
    }

    @GetMapping({"", "/"})
    public String showProductList(Model model){
        List<Product> products = productsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products", products);
        return "products/ProductList";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model){
        model.addAttribute("productDto", productDto);
        return "products/CreateProduct";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) throws IOException {
        if (productDto.getImageFile().isEmpty()){
            result.addError(new FieldError("productDto", "imageFile", "Image is required!!!"));
        }

        if (result.hasErrors()){
            return "products/CreateProduct";
        }

        //save image file
        MultipartFile image = productDto.getImageFile();
        Date createdAt = new Date(0);
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);
            
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), 
                    StandardCopyOption.REPLACE_EXISTING);
            } 
            
            catch (Exception ex) {
                System.out.println("Exception:: " + ex.getMessage());
            }
        } finally {
            System.out.println("Image saved");
        }
        
        //save product
        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCreatedAt(createdAt);
        product.setImageFileName(storageFileName);

        productsRepository.save(product);

        return "redirect:/products";
    }
}
