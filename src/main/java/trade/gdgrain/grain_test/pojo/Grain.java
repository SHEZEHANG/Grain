package trade.gdgrain.grain_test.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "grain")
public class Grain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String varieties;
    private String grade;
    private Integer produceTime;
    private Integer num;
    private String address;
    private Integer firstPrice;
    private Integer newPrice;
    private Integer sign;
    

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVarieties() {
        return varieties;
    }

    public void setVarieties(String varieties) {
        this.varieties = varieties;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(Integer produceTime) {
        this.produceTime = produceTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(Integer firstPrice) {
        this.firstPrice = firstPrice;
    }

    public Integer getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Integer newPrice) {
        this.newPrice = newPrice;
    }


    @Override
    public String toString() {
        return "Grain{" +
                "id=" + id +
                ", varieties='" + varieties + '\'' +
                ", grade='" + grade + '\'' +
                ", produceTime=" + produceTime +
                ", num=" + num +
                ", address='" + address + '\'' +
                ", firstPrice=" + firstPrice +
                ", newPrice=" + newPrice +
                ", sign=" + sign +
                '}';
    }
}
