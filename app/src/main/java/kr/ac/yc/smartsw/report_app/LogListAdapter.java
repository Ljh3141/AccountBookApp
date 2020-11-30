package kr.ac.yc.smartsw.report_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

public class LogListAdapter extends BaseAdapter {
    private ArrayList<LogItem> mItems= new ArrayList<>();

    @Override
    public int getCount(){
        return mItems.size();
    }

    @Override
    public LogItem getItem(int position){
        return mItems.get(position);
    }
    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.logitem_custom, parent,false);
        }
        TextView TV_id = (TextView) convertView.findViewById(R.id.list_id_text);
        TextView TV_componentKind = (TextView) convertView.findViewById(R.id.list_text_kind);
        TextView TV_componentMoney = (TextView) convertView.findViewById(R.id.list_text_money);
        TextView TV_componentDes = (TextView) convertView.findViewById(R.id.list_text_describtion);
        TextView TV_componentDate = (TextView) convertView.findViewById(R.id.list_text_date);
        TextView TV_componentNum = (TextView) convertView.findViewById(R.id.list_id_num);

        LogItem logitem_item = getItem(position);
        int i = logitem_item.getKind();
        String kind_str = getStrKind(i);

        TV_id.setText(logitem_item.getUser());
        TV_componentMoney.setText(String.format("금액 : %d",logitem_item.getMoney())+"원");
        TV_componentKind.setText("분류 : "+kind_str);
        TV_componentDes.setText("설명 : "+logitem_item.getDescription());
        TV_componentDate.setText("등록일 : "+logitem_item.getUseDate());
        TV_componentNum.setText(String.format("%d",logitem_item.getNum()));
        return convertView;
    }

    public void addItem(String id,  int kind,int money,String description, String date, int num){
        LogItem mlogitem = new LogItem();

        mlogitem.setUser(id);
        mlogitem.setMoney(money);
        mlogitem.setKind(kind);
        mlogitem.setDescription(description);
        mlogitem.setUseDate(date);
        mlogitem.setNum(num);

        mItems.add(mlogitem);
    }
    /*public void addItem(Logitem item){
        mItems.add(item);
    }*/
    private String getStrKind(int i){
        String str = "";
        switch(i){
            case 1 :
                str = str + "식비";
                break;
            case 2 :
                str = str + "쇼핑";
                break;
            case 3 :
                str = str + "관리비";
                break;
            case 4 :
                str = str + "교통비";
                break;
            case 5 :
                str = str + "기타";
                break;
        }
        return str;
    }
}
