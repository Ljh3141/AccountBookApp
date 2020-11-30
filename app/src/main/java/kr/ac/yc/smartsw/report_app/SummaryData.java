package kr.ac.yc.smartsw.report_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SummaryData {
    private Map<String, Integer> moneyMap;
    private Map<String, Integer> countMap;
    private int sum;
    private int count;
    public SummaryData(){
        moneyMap = new HashMap<String, Integer>();
        countMap = new HashMap<String, Integer>();

        sum = 0;
        count = 0;
    }

    public void setMoneyMap(String name, int money){
        if(moneyMap.containsKey(name)){
            int newMoney = moneyMap.get(name)+money;
            int newCount = countMap.get(name)+1;
            moneyMap.put(name,newMoney);
            countMap.put(name, newCount);
            sum +=money;
        }else{
            moneyMap.put(name, money);
            countMap.put(name, 1);
            sum +=money;
            count++;
        }
    }
    public Map<String,Integer> getMoneyMap(){
        return moneyMap;
    }
    public Map<String,Integer> getCountMap(){return countMap;}
    public int getAverage(){
        if(count==0){
            return 0;
        }else{
            return sum/count;
        }
    }
    public int getSum(){
        return sum;
    }
    public int getCount(){
        return count;
    }
}
