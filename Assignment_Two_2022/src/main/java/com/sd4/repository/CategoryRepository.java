/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sd4.repository;

/**
 *
 * @author calum
 */
import com.sd4.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author calum
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    
}