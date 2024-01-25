package springboot_thymeleaf.webstore.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot_thymeleaf.webstore.model.Product;

public interface ProductsRepository extends JpaRepository<Product, Integer>{
    
}
