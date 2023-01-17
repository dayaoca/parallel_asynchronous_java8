package com.learnjava.service;

import com.learnjava.domain.movie.Inventory;
import com.learnjava.domain.movie.ProductOption;

public class InventoryService {
    public Inventory retrieveInventory(ProductOption productOption){
        return Inventory.builder()
                .count(2).build();
    }
}
