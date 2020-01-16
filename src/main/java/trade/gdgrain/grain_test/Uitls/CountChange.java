package trade.gdgrain.grain_test.Uitls;

import trade.gdgrain.grain_test.pojo.Grain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CountChange {



    Map<Integer,Thread> ThreadMap = new HashMap();

    public void countChange(Grain grain,ConcurrentHashMap<String,Integer> countTimeMap){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (countTimeMap.get("COUNT_TIME" + grain.getId())!=0||countTimeMap.get("COUNT_TIME" + grain.getId())==null) {
                    if(Thread.currentThread().isInterrupted()){
                        //System.out.println(grain.getId()+"---"+Thread.currentThread().getName()+"中断线程！！！！");
                        break;
                    }
                    Integer time = (Integer) countTimeMap.get("COUNT_TIME" + grain.getId());
                    //System.out.println("======"+grain.getId()+"======"+Thread.currentThread().getName()+"========");
                    try {
                        Thread.sleep(1000);
                        time--;
                        countTimeMap.put("COUNT_TIME" + grain.getId(),time);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        ThreadMap.put(grain.getId(),thread);
    }


    public void stopThread(Integer id){
        Thread thread = ThreadMap.get(id);
        thread.interrupt();
}

}
