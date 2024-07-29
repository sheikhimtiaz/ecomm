package com.sheikhimtiaz.ecomm.repository;

import com.sheikhimtiaz.ecomm.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
