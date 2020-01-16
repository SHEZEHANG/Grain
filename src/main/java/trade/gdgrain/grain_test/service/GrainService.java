package trade.gdgrain.grain_test.service;

import trade.gdgrain.grain_test.pojo.Grain;
import trade.gdgrain.grain_test.pojo.MyGrain;
import trade.gdgrain.grain_test.pojo.Success;
import trade.gdgrain.grain_test.pojo.SuccessGrain;

import java.util.List;

public interface GrainService {

    public List<MyGrain> getGrainList();

    Grain selectById(Integer id);

    void updataRrice(Integer id, Integer newPrice);


    void changTime(Integer id);

    void joinList(Integer id, Long uuid, Integer price);

    List<SuccessGrain> getJoinList(Long uuid);

    List<Success> checkMessage(Long uuid);
}
