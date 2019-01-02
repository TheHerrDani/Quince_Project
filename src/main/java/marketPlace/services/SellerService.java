package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.controller.model.SellerModel;

import java.util.List;

public interface SellerService {
    String saveSeller(SellerModel sellerModel);

    String deleteSeller(int sellerId);

    SellerModel getSellerById(int sellerId);

    List<SellerModel> getAllSellers();

    List<ProductModel> getProductsBySeller(int sellerId);

    String addRating(int sellerId, int rating);

    String modifySellerById(int sellerId, SellerModel sellerModel);

    List<SellerModel> getSellersWithSalesData();

    List<SellerModel> orderingProductsBySalesData(boolean ascending);

    List<SellerModel> top5Seller();
}
