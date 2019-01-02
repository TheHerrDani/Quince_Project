package marketPlace.controller;

import marketPlace.controller.model.ProductModel;
import marketPlace.controller.model.SellerModel;
import marketPlace.services.SellerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/Seller")
public class SellerContoller {

    private SellerService sellerServices;

    public SellerContoller(SellerService sellerServices) {
        this.sellerServices = sellerServices;
    }

    @PostMapping(path = "/Add")
    public @ResponseBody
    String addNewSeller(@RequestBody SellerModel sellermodel) {
        return sellerServices.saveSeller(sellermodel);
    }

    @PostMapping(path = "/AddRating")
    public @ResponseBody
    String addRating(@RequestParam int sellerId, @RequestParam int rating) {
        return sellerServices.addRating(sellerId, rating);
    }

    @GetMapping(path = "/Get")
    public @ResponseBody
    SellerModel getSellerById(@RequestParam int Id) {
        return sellerServices.getSellerById(Id);
    }

    @GetMapping(path = "/GetAll")
    public @ResponseBody
    List<SellerModel> getAllSellers() {
        return sellerServices.getAllSellers();
    }

    @GetMapping(path = "/GetProductBySeller")
    public @ResponseBody
    List<ProductModel> getProductsBySeller(@RequestParam int sellerId) {
        return sellerServices.getProductsBySeller(sellerId);
    }

    @DeleteMapping(path = "/Delete")
    public @ResponseBody
    String deleteSellerById(@RequestParam int sellerId) {
        return sellerServices.deleteSeller(sellerId);
    }
}
