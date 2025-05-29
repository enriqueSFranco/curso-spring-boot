package cursoSpringBoot.domain;

public class Product {
    // usar tipos de datos referenciales
    private Integer counter = 0;
    private final Integer ID;
    private String name;
    private Double price;
    private Integer stock;

    public Product() {
        this.ID = ++this.counter;
    }

    public Product(String name, Double price, Integer stock) {
        this.ID = ++this.counter;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
