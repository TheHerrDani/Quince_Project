package marketPlace.controller;

import marketPlace.controller.model.ProductModel;
import marketPlace.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/ProductDomain")
public class ProductController {

    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/Add")
    public @ResponseBody
    String addNewProduct(@RequestBody ProductModel productModel) {
        return productService.saveProduct(productModel);
    }

    @GetMapping(path = "/Get")
    public @ResponseBody
    ProductModel getProductById(@RequestParam int productId) {
        return productService.getProductById(productId);
    }

    @GetMapping(path = "/GetAll")
    public @ResponseBody
    List<ProductModel> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping(path = "/Delete")
    public @ResponseBody
    String deleteProductById(@RequestParam int id) {
        return productService.deleteProduct(id);
    }
}
