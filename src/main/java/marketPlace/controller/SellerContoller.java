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

    @PostMapping(path = "/Get")
    public @ResponseBody
    SellerModel getSellerById(@RequestParam int sellerId) {
        return sellerServices.getSellerById(sellerId);
    }

    @PostMapping(path = "/GetAll")
    public @ResponseBody
    List<SellerModel> getAllSellers() {
        return sellerServices.getAllSellers();
    }

    @PostMapping(path = "/GetProductBySeller")
    public @ResponseBody
    List<ProductModel> getProductsBySeller(@RequestParam int sellerId) {
        return sellerServices.getProductsBySeller(sellerId);
    }

    @PostMapping(path = "/Modify")
    public @ResponseBody
    String modifySellerById(@RequestParam int sellerId,@RequestBody SellerModel sellerModel) {
        return sellerServices.modifySellerById(sellerId,sellerModel);
    }

    @DeleteMapping(path = "/Delete")
    public @ResponseBody
    String deleteSellerById(@RequestParam int sellerId) {
        return sellerServices.deleteSeller(sellerId);
    }

    @PostMapping(path = "/GetSellersWithSalesData")
    public @ResponseBody
    List<SellerModel> getSellersWithSalesData(
    ){
        return sellerServices.getSellersWithSalesData();
    }

    @PostMapping(path = "/OrderingSellersByAveragingRating")
    public @ResponseBody
    List<SellerModel> orderingProductsBySalesData(@RequestParam(defaultValue = "true") boolean ascending
    ){
        return sellerServices.orderingProductsBySalesData(ascending);
    }

    @PostMapping(path = "/Top5Seller")
    public @ResponseBody
    List<SellerModel> top5Seller(
    ){
        return sellerServices.top5Seller();
    }

}
