package trade.gdgrain.grain_test.pojo;

import java.util.List;

public class MyGrain extends Grain {

    private Integer time;


    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MyGrain{" +
                "time=" + time +super.toString()+
                '}';
    }
}
