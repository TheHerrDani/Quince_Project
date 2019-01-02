package marketPlace.controller;

import marketPlace.controller.model.ProductModel;
import marketPlace.repository.ProductCategory;
import marketPlace.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/Product")
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

    @PostMapping(path = "/Get")
    public @ResponseBody
    ProductModel getProductById(@RequestParam int productId) {
        return productService.getProductById(productId);
    }

    @PostMapping(path = "/GetAll")
    public @ResponseBody
    List<ProductModel> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping(path = "/Delete")
    public @ResponseBody
    String deleteProductById(@RequestParam int productid) {
        return productService.deleteProduct(productid);
    }

    @PostMapping(path = "/Modify")
    public @ResponseBody
    String modifyById(@RequestParam int productId,@RequestBody ProductModel productModel
    ){
        return productService.modifyProduct(productId,productModel);
    }


}
