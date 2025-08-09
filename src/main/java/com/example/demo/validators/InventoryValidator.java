package com.example.demo.validators;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 *
 *
 *
 */
public class InventoryValidator implements ConstraintValidator<ValidInventory, Part> {
    @Autowired
    private ApplicationContext context;
    public static  ApplicationContext myContext;
    @Override
    public void initialize(ValidInventory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Part parts, ConstraintValidatorContext constraintValidatorContext) {
        if (parts.getInv() > parts.getMaxInv()){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Inventory cannot be greater than max inventory").addConstraintViolation();
            return false;
        } else if(parts.getInv()< parts.getMinInv()){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Inventory cannot be less than min inventory").addConstraintViolation();
            return false;
        }
            return true;
    }
}
