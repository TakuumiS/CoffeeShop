# Western Governors University - D287

- Name: Takumi Sakai
- Email: tsakai1@wgu.edu
- ID: 012362473

<hr>

# Part C:
Customize the HTML user interface for your customer’s application. The user interface should include the shop name, the product names, and the names of the parts.

- Following updates made in mainscreen.html:  
Line 14: Link to mainscreen.css.   
Line 17: Change title to Takumi's Coffee.    
Line 24: Change header to Takumi's Coffee Shop.    
Line 30: Change header to Ingredients.    
Line 66: Change header to Speciality Lattes.    

<hr> 

# Part D:
Add an “About” page to the application to describe your chosen customer’s company to web viewers and include navigation to and from the “About” page and the main screen.

### mainscreen.html:
Line 26: Add a href to the about page.  

```
<a href="/about" class="btn btn-info">About</a>
```

### MainScreenControllerr.java:  

Line 57: Map to /about

```
  @GetMapping("/about")  
  public String about() { return "about.html"; }  
```

### about.html
Create about.html file in .../resources/templates

<hr> 

# Part E: 
Add a sample inventory appropriate for your chosen store to the application. You should have five parts and five products in your sample inventory and should not overwrite existing data in the database.

### BootStrapData.java:

Lines 32-40: Added InhousePartRepository: 

```
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
```

Lines 42-98: Added insourced and outsourced parts:

```
@Override
    public void run(String... args) throws Exception {

        if(inhousePartRepository.count()==0) {

            InhousePart mochi = new InhousePart();
            mochi.setId(1);
            mochi.setName("Homemade Mochi");
            mochi.setPrice(1);
            mochi.setInv(20);
            mochi.setMinInv(5);
            mochi.setMaxInv(100);
            inhousePartRepository.save(mochi);

            InhousePart brule = new InhousePart();
            brule.setId(2);
            brule.setName("Creme Brule");
            brule.setPrice(2);
            brule.setInv(10);
            brule.setMinInv(5);
            brule.setMaxInv(100);
            inhousePartRepository.save(brule);

            InhousePart strawberry = new InhousePart();
            strawberry.setId(1);
            strawberry.setName("Strawberry puree");
            strawberry.setPrice(6);
            strawberry.setInv(10);
            strawberry.setMinInv(5);
            strawberry.setMaxInv(100);
            inhousePartRepository.save(strawberry);

        }

        if(outsourcedPartRepository.count()==0) {

            OutsourcedPart milk = new OutsourcedPart();
            milk.setCompanyName("Hokkaido Milk Co.");
            milk.setId(4);
            milk.setName("Hokkaido Milk");
            milk.setPrice(1);
            milk.setInv(10);
            milk.setMinInv(2);
            milk.setMaxInv(100);
            outsourcedPartRepository.save(milk);

            OutsourcedPart matcha = new OutsourcedPart();
            matcha.setCompanyName("Matcha Co.");
            matcha.setId(5);
            matcha.setName("Matcha");
            matcha.setPrice(3);
            matcha.setInv(10);
            matcha.setMinInv(3);
            matcha.setMaxInv(10);
            outsourcedPartRepository.save(matcha);

        }
```

Lines: 100-111: Added 5 products:

```
 if(productRepository.count()==0) {
            Product latte = new Product(20,"Hokkaido Latte", 10, 25 );
            Product strawberryLatte = new Product(30, "Strawberry Latte", 12, 30 );
            Product matchaLatte = new Product(40, "Matcha Latte", 14, 35 );
            Product mochiLatte = new Product(50, "Mochi Latte", 16, 40 );
            Product bruleLatte = new Product(60, "Creme Brule Latte", 18, 45 );

            productRepository.save(latte);
            productRepository.save(strawberryLatte);
            productRepository.save(matchaLatte);
            productRepository.save(mochiLatte);
            productRepository.save(bruleLatte);


        }
```

<hr> 

# Part F:
Add a “Buy Now” button to your product list. Your “Buy Now” button must meet each of the following parameters:

### mainscreen.html:
Line 99: Added a buy now button

```
<a th:href="@{/buyProduct(productID=${tempProduct.id})}" class="btn btn-primary btn-sm mb-3">Buy Now</a>

```

### AddProductController.java:
Lines 177-188: Add mapping for buyProduct

```
@GetMapping("/buyProduct")
    public String buyProduct(@RequestParam("productID") int theId, Model theModel) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product product = productService.findById(theId);
        if(product.getInv()>0){
            product.setInv(product.getInv()-1);
            productService.save(product);
            return "purchaseSuccess";
        }
        else{
            return "purchaseFailure";
        }
```

### purchaseSuccess.html
Create an html file to display a purchase success message.

```
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Takumi's Coffee Shop</title>
</head>
<body>
<h1>Purchase Successful! Thanks for shopping with us!</h1>

<a href="http://localhost:8080/">Back to Home</a>
</body>
</html>
```

### purchaseFailure.html
Create an html file to display a purchase failed message.

```
<!DOCTYPE html>
<html lang="en">
<head>

    <title>Takumi's Coffee Shop</title>
</head>
<body>
<h1>Purchase Failed! Sorry, we're currently out of stock! Please come back again later. </h1>

<a href="http://localhost:8080/">Back to Home</a>
</body>
</html>
```

<hr> 

# Part G
Modify the parts to track maximum and minimum inventory.

### Part.java: 

Lines 34-38: Add additional fields to the part entity for maximum and minimum inventory.

```
    @Min(value = 0, message = "Min Inventory value must be positive")
    int minInv;

    @Min(value = 0, message = "Max Inventory value must be positive")
    int maxInv;
```

Lines 95-107: Create getters and setters for min and max inventory.

```
    public int getMinInv() {
        return minInv;
    }

    public void setMinInv(int minInv) {
        this.minInv = minInv;
    }

    public int getMaxInv() {
        return maxInv;
    }

    public void setMaxInv(int maxInv) {
        this.maxInv = maxInv;
    }
    
```

### BootStrapData.java

Lines 42-96: Add sample inventory with min and max inventory values.

```
 @Override
    public void run(String... args) throws Exception {

        if(inhousePartRepository.count()==0) {

            InhousePart mochi = new InhousePart();
            mochi.setId(1);
            mochi.setName("Homemade Mochi");
            mochi.setPrice(1);
            mochi.setInv(20);
            mochi.setMinInv(5);
            mochi.setMaxInv(100);
            inhousePartRepository.save(mochi);

            InhousePart brule = new InhousePart();
            brule.setId(2);
            brule.setName("Creme Brule");
            brule.setPrice(2);
            brule.setInv(10);
            brule.setMinInv(5);
            brule.setMaxInv(100);
            inhousePartRepository.save(brule);

            InhousePart strawberry = new InhousePart();
            strawberry.setId(1);
            strawberry.setName("Strawberry puree");
            strawberry.setPrice(6);
            strawberry.setInv(10);
            strawberry.setMinInv(5);
            strawberry.setMaxInv(100);
            inhousePartRepository.save(strawberry);

        }

        if(outsourcedPartRepository.count()==0) {

            OutsourcedPart milk = new OutsourcedPart();
            milk.setCompanyName("Hokkaido Milk Co.");
            milk.setId(4);
            milk.setName("Hokkaido Milk");
            milk.setPrice(1);
            milk.setInv(10);
            milk.setMinInv(2);
            milk.setMaxInv(100);
            outsourcedPartRepository.save(milk);

            OutsourcedPart matcha = new OutsourcedPart();
            matcha.setCompanyName("Matcha Co.");
            matcha.setId(5);
            matcha.setName("Matcha");
            matcha.setPrice(3);
            matcha.setInv(10);
            matcha.setMinInv(3);
            matcha.setMaxInv(10);
            outsourcedPartRepository.save(matcha);
            
```

### InhousePartForm.html

Lines 25-29: add additional text inputs for the inventory so the user can set the maximum and minimum values.

```
<p> Min Inventory: <input type="text" path="minInv" th:field="*{minInv}" placeholder="Min Inventory" class="form-control mb-4 col-4"/></p>
<p th:if="${#fields.hasErrors('MinInv')}" th:errors="*{minInv}">Min Inventory Error</p>

<p> Max Inventory: <input type="text" path="maxInv" th:field="*{maxInv}" placeholder="Max Inventory" class="form-control mb-4 col-4"/></p>
<p th:if="${#fields.hasErrors('maxInv')}" th:errors="*{maxInv}">Max Inventory Error</p>

```

### OutsourcedPartForm.html

Lines 26-30: add additional text inputs for the inventory so the user can set the maximum and minimum values.

```
<p> Min Inventory: <input type="text" path="minInv" th:field="*{minInv}" placeholder="Min Inventory" class="form-control mb-4 col-4"/></p>
<p th:if="${#fields.hasErrors('MinInv')}" th:errors="*{minInv}">Min Inventory Error</p>

<p> Max Inventory: <input type="text" path="maxInv" th:field="*{maxInv}" placeholder="Max Inventory" class="form-control mb-4 col-4"/></p>
<p th:if="${#fields.hasErrors('maxInv')}" th:errors="*{maxInv}">Max Inventory Error</p>

```

### InventoryValidator.java

Lines 19-40: Create InventoryValidator class which checks if inventory is between min and max values.

```
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
```

### ValidInventory.java

Lines 16-24: Create ValidInventory interface for InventoryValidator class returns an inventory error.

```
@Constraint(validatedBy = {InventoryValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidInventory {
    String message() default "Inventory error!";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};

}
```

### Part.java

Line 23: Add @ValidInventory annotation

### application.properties

Line 6: Rename the file the persistent storage is saved to.

```
spring.datasource.url=jdbc:h2:file:~/takumi-900
```

<hr> 

# Part H:
Add validation for between or at the maximum and minimum fields.

### InventoryValidator.java

Lines 28-37: Display errors messages when inventory is below minInv or above maxInv.

```
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
```

### InhousePartForm.html

Lines 25-40: Display errors for min and max inventory.

```
<p> Min Inventory: <input type="text" path="minInv" th:field="*{minInv}" placeholder="Min Inventory" class="form-control mb-4 col-4"/></p>
<p th:if="${#fields.hasErrors('MinInv')}" th:errors="*{minInv}">Min Inventory Error</p>

<p> Max Inventory: <input type="text" path="maxInv" th:field="*{maxInv}" placeholder="Max Inventory" class="form-control mb-4 col-4"/></p>
<p th:if="${#fields.hasErrors('maxInv')}" th:errors="*{maxInv}">Max Inventory Error</p>

<p> ID Number:
    <input type="text" th:field="*{partId}" placeholder="Part ID" class="form-control mb-4 col-4"/></p>

    <div th:if="${#fields.hasAnyErrors()}">
        <ul>
            <li
                    th:each="err: ${#fields.allErrors()}" th:text="${err}">
            </li>
        </ul>
    </div>
```

### OutsourcedPartForm.html

Lines 26-41: Display errors for min and max inventory

```
    <p> Min Inventory: <input type="text" path="minInv" th:field="*{minInv}" placeholder="Min Inventory" class="form-control mb-4 col-4"/></p>
    <p th:if="${#fields.hasErrors('MinInv')}" th:errors="*{minInv}">Min Inventory Error</p>

    <p> Max Inventory: <input type="text" path="maxInv" th:field="*{maxInv}" placeholder="Max Inventory" class="form-control mb-4 col-4"/></p>
    <p th:if="${#fields.hasErrors('maxInv')}" th:errors="*{maxInv}">Max Inventory Error</p>


    <p>Company Name: <input type="text" th:field="*{companyName}" placeholder="Company Name" class="form-control mb-4 col-4"/></p>

    <div th:if="${#fields.hasAnyErrors()}">
        <ul>
            <li
                    th:each="err: ${#fields.allErrors()}" th:text="${err}">
            </li>
        </ul>
    </div>
```

### EnufPartsValidator.java

Lines 28-50: Display error messages for low inventory when adding and updating products lowers the part inventory below the minimum.

```
    @Override
    public boolean isValid(Product product, ConstraintValidatorContext constraintValidatorContext) {
        if(context==null) return true;
        if(context!=null)myContext=context;
        ProductService repo = myContext.getBean(ProductServiceImpl.class);

        if (product.getId() != 0) {
            Product myProduct = repo.findById((int) product.getId());
            for (Part p : myProduct.getParts()) {
                if (p.getInv()<(product.getInv()-myProduct.getInv())){
                    return false;
                }
                if (p.getInv()-1 < p.getMinInv()) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Low Inventory! Parts would be less than the required minimum").addConstraintViolation();
                    return false;
                }
            }
            return true;
        }
        else{
                return true;
            }
    }
```

<hr> 

# Part I:
Add at least two unit tests for the maximum and minimum fields to the PartTest class in the test package.

### PartTest.java

Lines 160-194: Add unit tests for min and max inventory.

```
    @Test
    void getMinInv() {
        int min_inv=5;
        partIn.setMinInv(min_inv);
        assertEquals(min_inv,partIn.getMinInv());
        partOut.setMinInv(min_inv);
        assertEquals(min_inv,partOut.getMinInv());
    }

    @Test
    void setMinInv() {
        int min_inv=5;
        partIn.setMinInv(min_inv);
        assertEquals(min_inv,partIn.getMinInv());
        partOut.setMinInv(min_inv);
        assertEquals(min_inv,partOut.getMinInv());
    }

    @Test
    void getMaxInv() {
        int max_inv=100;
        partIn.setMaxInv(max_inv);
        assertEquals(max_inv,partIn.getMaxInv());
        partOut.setMaxInv(max_inv);
        assertEquals(max_inv,partOut.getMaxInv());
    }

    @Test
    void setMaxInv() {
        int max_inv=100;
        partIn.setMaxInv(max_inv);
        assertEquals(max_inv,partIn.getMaxInv());
        partOut.setMaxInv(max_inv);
        assertEquals(max_inv,partOut.getMaxInv());
    }
```

<hr> 

# Part J:
Remove the class files for any unused validators in order to clean your code.

### ValidDeletePart.java

Lines 17-24: Added @constraint annotation so the DeletePartValidator class is used correctly.

```
@Constraint(validatedBy = {DeletePartValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDeletePart {
    String message() default "Part cannot be deleted if used in a product.";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
```

<hr>