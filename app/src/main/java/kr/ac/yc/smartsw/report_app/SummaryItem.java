package kr.ac.yc.smartsw.report_app;

public class SummaryItem {
    private String user;
    private int money;
    private int debt;
    private int useDate;//추후 날짜로 변경
    public SummaryItem(){

    }
    public SummaryItem(String name, int money, int debt, int useDate){
        this.user = name;
        this.money=money;
        this.debt=debt;
        this.useDate=useDate;
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
    public int getDebt(){
        return debt;
    }
    public void setDebt(int debt){
        this.debt=debt;
    }
    public int getUseDate(){
        return useDate;
    }
    public void setUseDate(int date){
        this.useDate = date;
    }
}
