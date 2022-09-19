package prj.pt.assignment4.BusinessLayer;

import java.util.List;

public class CompositeProduct extends MenuItem {
    List<BaseProduct> compositeProduct;

    public CompositeProduct(List<BaseProduct> compositeProduct){
        this.compositeProduct = compositeProduct;
    }
    public Integer computePrice() {
        Integer priceSum = 0;
        for (BaseProduct baseProduct : compositeProduct) {
            priceSum += baseProduct.getPrice();
        }
        return priceSum;
    }

}
