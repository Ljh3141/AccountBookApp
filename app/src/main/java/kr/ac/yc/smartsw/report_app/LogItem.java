package kr.ac.yc.smartsw.report_app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogItem {
    private int num;//번호. db의 key
    private String user;//등록자
    private int money;//금액
    private int use_kind;//소비종류
    private String description;//설명
    private String useDate;//추후 날짜로 변경

    public LogItem(){

    }
    public LogItem(String name, int money, int use_kind, String description, int num){
        this.num=num;
        this.user = name;
        this.money=money;
        this.use_kind=use_kind;
        this.description = description;

        String pattern = "yyyy MMMMM dd EEEEE HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("ko", "KR"));
        this.useDate= simpleDateFormat.format(new Date());
    }
    public LogItem(String name, int money, int use_kind, String description, int num, String date){
        this.num=num;
        this.user = name;
        this.money=money;
        this.use_kind=use_kind;
        this.description = description;
        this.useDate = date;
    }

    public String getUser(){
        return user;
    }
    public void setUser(String user_input){ this.user=user_input; }
    public int getMoney(){
        return money;
    }
    public void setMoney(int money){
        this.money=money;
    }
    public int getKind(){
        return use_kind;
    }
    public void setKind(int use_kind){
        this.use_kind=use_kind;
    }
    public String getUseDate(){
        return useDate;
    }
    public void setUseDate(String date){
        this.useDate = date;
    }
    public String getDescription(){ return this.description; }
    public void setDescription(String description){this.description=description; }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSaveText(){
        //String result = user+":"+Integer.toString(money)+":"+Integer.toString(use_kind)+":"+Integer.toString(useDate);
        return null;
    }

}
