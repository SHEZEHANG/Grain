package trade.gdgrain.grain_test.pojo;

public class Success {
    private Integer id;
    private Integer price;

    public Success() {
    }

    public Success(Integer id, Integer price) {
        this.id = id;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Success{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }
}
