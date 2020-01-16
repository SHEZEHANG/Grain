package trade.gdgrain.grain_test.pojo;

public class SuccessGrain extends Grain{

    private Integer message;

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SuccessGrain{" +
                "message=" + message +super.toString()+
                '}';
    }
}
