package trade.gdgrain.grain_test.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import trade.gdgrain.grain_test.Uitls.CountChange;
import trade.gdgrain.grain_test.mapper.GrainMapper;
import trade.gdgrain.grain_test.pojo.Grain;
import trade.gdgrain.grain_test.pojo.MyGrain;
import trade.gdgrain.grain_test.pojo.Success;
import trade.gdgrain.grain_test.pojo.SuccessGrain;
import trade.gdgrain.grain_test.service.GrainService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GrainServiceImpl implements GrainService{

    @Value("${COUNT_TIME}")
    private String COUNT_TIME;

    @Autowired
    private GrainMapper grainMapper;

    //存放已经应价的map
    Map<Long,List<Success>> succeaaMap = new HashMap<>();

    //存放时间的map
    ConcurrentHashMap<String,Integer> countTimeMap = new ConcurrentHashMap<String,Integer>();

    CountChange countChange = new CountChange();

    @Override
    public List<MyGrain> getGrainList() {

        //查询数据库所有信息
        Example example = new Example(Grain.class);
        List<Grain> grainList = grainMapper.selectByExample(example);
        List MyGrainList = new ArrayList<MyGrain>();

        //遍历设置初始时间
        for (Grain grain : grainList) {
            //myRedisTemplate.opsForValue().set(COUNT_TIME+grain.getId(),120);
            if(StringUtils.isEmpty(countTimeMap.get(COUNT_TIME+grain.getId()))){
                countTimeMap.put(COUNT_TIME+grain.getId(),120);
                //System.out.println(countTimeMap.get(COUNT_TIME + grain.getId()));
                //开启计时线程
                countChange.countChange(grain,countTimeMap);
            }

            MyGrain myGrain = new MyGrain();
            myGrain.setId(grain.getId());
            myGrain.setVarieties(grain.getVarieties());
            myGrain.setGrade(grain.getGrade());
            myGrain.setProduceTime(grain.getProduceTime());
            myGrain.setAddress(grain.getAddress());
            myGrain.setFirstPrice(grain.getFirstPrice());
            myGrain.setNewPrice(grain.getNewPrice());
            //myGrain.setTime((Integer) myRedisTemplate.opsForValue().get(COUNT_TIME+grain.getId()));
            myGrain.setTime((Integer) countTimeMap.get(COUNT_TIME+grain.getId()));
            MyGrainList.add(myGrain);
        }
        return MyGrainList;
    }

    @Override
    public Grain selectById(Integer id) {
        return grainMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updataRrice(Integer id, Integer newPrice) {
        Grain grain = new Grain();
        grain.setId(id);
        grain.setNewPrice(newPrice);
        grainMapper.updateByPrimaryKeySelective(grain);
    }

    @Override
    public void changTime(Integer id) {
        countChange.stopThread(id);
        countTimeMap.put(COUNT_TIME+id,120);
        Grain grain = new Grain();
        grain.setId(id);
        countChange.countChange(grain,countTimeMap);
    }

    @Override
    public void joinList(Integer id, Long uuid, Integer price) {
        if (!succeaaMap.containsKey(uuid)) {
            List<Success> list = new ArrayList<>();
            list.add(new Success(id, price));
            succeaaMap.put(uuid, list);
        } else {
            List<Success> successList = succeaaMap.get(uuid);
            for (Success success : successList) {
                //值超范围！！！
                if (success.getId() == id) {
                    successList.remove(success);
                    break;
                }
            }
            successList.add(new Success(id, price));
            succeaaMap.put(uuid, successList);
        }
    }

    @Override
    public List<SuccessGrain> getJoinList(Long uuid) {
        List<SuccessGrain> successGrainList = new ArrayList<>();


        if(succeaaMap.containsKey(uuid)) {
            List<Success> successList = succeaaMap.get(uuid);
            for (Success success : successList) {
                Grain grain = grainMapper.selectByPrimaryKey(success.getId());
                SuccessGrain successGrain = new SuccessGrain();
                successGrain.setId(grain.getId());
                successGrain.setVarieties(grain.getVarieties());
                successGrain.setGrade(grain.getGrade());
                successGrain.setProduceTime(grain.getProduceTime());
                successGrain.setAddress(grain.getAddress());
                successGrain.setFirstPrice(grain.getFirstPrice());
                successGrain.setNewPrice(grain.getNewPrice());
                successGrain.setNum(grain.getNum());
                if (grain.getNewPrice() == success.getPrice()) {
                    successGrain.setMessage(1);
                }
                successGrain.setMessage(0);
                successGrainList.add(successGrain);
            }
            return successGrainList;
        }
        return null;
    }


    @Override
    public List<Success> checkMessage(Long uuid) {

        return succeaaMap.get(uuid);
    }


}
