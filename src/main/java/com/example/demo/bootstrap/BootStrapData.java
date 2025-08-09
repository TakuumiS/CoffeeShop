package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.InhousePartRepository;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.OutsourcedPartService;
import com.example.demo.service.OutsourcedPartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 *
 *
 *
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;
    private final InhousePartRepository inhousePartRepository;
    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, InhousePartRepository inhousePartRepository,  OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.inhousePartRepository=inhousePartRepository;
        this.outsourcedPartRepository=outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(inhousePartRepository.count()==0) {

            InhousePart mochi = new InhousePart();
            mochi.setId(1);
            mochi.setName("Homemade Mochi");
            mochi.setPrice(5);
            mochi.setInv(20);
            mochi.setMinInv(0);
            mochi.setMaxInv(100);
            inhousePartRepository.save(mochi);

            InhousePart brule = new InhousePart();
            brule.setId(2);
            brule.setName("Creme Brule");
            brule.setPrice(8);
            brule.setInv(10);
            brule.setMinInv(0);
            brule.setMaxInv(100);
            inhousePartRepository.save(brule);

            InhousePart strawberry = new InhousePart();
            strawberry.setId(3);
            strawberry.setName("Strawberry puree");
            strawberry.setPrice(6);
            strawberry.setInv(10);
            strawberry.setMinInv(0);
            strawberry.setMaxInv(100);
            inhousePartRepository.save(strawberry);

        }

        if(outsourcedPartRepository.count()==0) {

            OutsourcedPart milk = new OutsourcedPart();
            milk.setCompanyName("Hokkaido Milk Co.");
            milk.setId(4);
            milk.setName("Hokkaido Milk");
            milk.setPrice(10);
            milk.setInv(10);
            milk.setMinInv(0);
            milk.setMaxInv(100);
            outsourcedPartRepository.save(milk);

            OutsourcedPart matcha = new OutsourcedPart();
            matcha.setCompanyName("Matcha Co.");
            matcha.setId(5);
            matcha.setName("Matcha");
            matcha.setPrice(12);
            matcha.setInv(10);
            matcha.setMinInv(0);
            matcha.setMaxInv(100);
            outsourcedPartRepository.save(matcha);

        }

        if(productRepository.count()==0) {
            Product latte = new Product("Hokkaido Latte", 10, 25 );
            Product strawberryLatte = new Product("Strawberry Latte", 12, 30 );
            Product matchaLatte = new Product("Matcha Latte", 14, 35 );
            Product mochiLatte = new Product("Mochi Latte", 16, 40 );
            Product bruleLatte = new Product("Creme Brule Latte", 18, 45 );

            productRepository.save(latte);
            productRepository.save(strawberryLatte);
            productRepository.save(matchaLatte);
            productRepository.save(mochiLatte);
            productRepository.save(bruleLatte);


        }




        System.out.println("Started in Bootstrap");
        System.out.println("Number of Products "+productRepository.count());
        System.out.println(productRepository.findAll());
        System.out.println("Number of Parts "+partRepository.count());
        System.out.println(partRepository.findAll());

    }
}
