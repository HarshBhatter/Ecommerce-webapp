package com.example.ecomMyself.ecomMyself.Coupons.Factory;

import com.example.ecomMyself.ecomMyself.Coupons.Categories.*;
import com.example.ecomMyself.ecomMyself.Coupons.Enums.CouponCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory {
    @Autowired
    private Seasonal seasonal;
    @Autowired
    private BulkOrder bulkOrder;
    @Autowired
    private NewUser newUser;
    @Autowired
    private ProductCategory productCategory;
    public Category getCategory(CouponCategory category)
    {
        if(category==CouponCategory.seasonal)
            return seasonal;
        if(category==CouponCategory.bulkOrder)
            return bulkOrder;
        if(category==CouponCategory.newUser)
            return newUser;
        if(category==CouponCategory.productCategory)
            return productCategory;
        return null;
    }

}
