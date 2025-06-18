package service.custom.impl;

import dto.CartTable;
import dto.Product;
import javafx.collections.ObservableList;
import repository.DaoFactory;
import repository.custom.ProductRepository;
import service.BoFactory;
import service.custom.PlaceOrderService;
import service.custom.ProductService;
import util.Repositorytype;
import util.ServiceType;

public class PlaceOrderServiceImpl implements PlaceOrderService {

   ProductService productService= BoFactory.getInstance().getServiceType(ServiceType.PRODUCT);
   ProductRepository productRepository= DaoFactory.getInstance().getRepositoryType(Repositorytype.PRODUCT);

    @Override
    public CartTable addToCart(String id, Long qty) {
        Product product = productService.searchById(id);

        double totalAmount=product.getPrice()*qty;
        double discount= totalAmount * (product.getDiscount()/100);
        return new CartTable(product.getId(), product.getName(), qty, product.getPrice(), product.getDiscount(), (totalAmount-discount));

    }

    @Override
    public CartTable placeOrder(ObservableList<CartTable> list) {
        double netTotalAmount = 0.0;
        double totalAmount = 0.0;

        for (CartTable cartTable : list) {
            netTotalAmount += cartTable.getNetTotalAmount();
            totalAmount += cartTable.getQty() * cartTable.getPrice();
            productRepository.updateStock(cartTable.getId(), cartTable.getQty());

        }
        double discount = totalAmount - netTotalAmount;

        return new CartTable(totalAmount, discount, netTotalAmount);

    }

    @Override
    public boolean checkQtyIsAvailable(String id,Long qty) {
        Product product = productService.searchById(id);
        return product.getStock() >= qty;
    }

    @Override
    public Double calculateBalance(String amount,String totalAmount) {
        return (Double.parseDouble(amount))-( Double.parseDouble(totalAmount));

    }


}
