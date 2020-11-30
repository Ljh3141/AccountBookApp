package kr.ac.yc.smartsw.report_app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateItem {
    private int num;//데이터 순번 1부터 시작
    private String user;//등록자
    private String description;//변경 내용
    private String date;//변경 날짜

    public UpdateItem(){}
    public UpdateItem(int num, String user, String description){
        this.num= num;
        this.user = user;
        this.description = description;

        String pattern = "yyyy MMMMM dd EEEEE HH:mm:ss";//수정일 지정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("ko", "KR"));
        this.date= simpleDateFormat.format(new Date());
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
