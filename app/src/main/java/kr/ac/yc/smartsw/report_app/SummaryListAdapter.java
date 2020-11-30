package kr.ac.yc.smartsw.report_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SummaryListAdapter extends BaseAdapter {
    private ArrayList<SummaryItem> mItems = new ArrayList<>();
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public SummaryItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sammeryitem_custom, parent,false);
        }
        TextView TV_id = (TextView) convertView.findViewById(R.id.sammuery_list_id_text);
        TextView TV_component1 = (TextView) convertView.findViewById(R.id.sammuery_list_text1);
        TextView TV_component2 = (TextView) convertView.findViewById(R.id.sammuery_list_text2);
        TextView TV_component3 = (TextView) convertView.findViewById(R.id.sammuery_list_text3);

        SummaryItem summeryitem = getItem(position);
        if(summeryitem.getUser().equals("총계")){
            TV_id.setText(summeryitem.getUser());
            TV_component1.setText(String.format("총 인원 : %d명",summeryitem.getMoney()));
            TV_component2.setText(String.format("총 금액 : %d원",summeryitem.getDebt()));
            TV_component3.setText(String.format("평균 금액 : %d원",summeryitem.getUseDate()));

        }else{
            TV_id.setText(summeryitem.getUser());
            TV_component1.setText(String.format("총 소비 : %d원",summeryitem.getMoney()));
            TV_component2.setText(String.format("내야할 돈 : %d원",summeryitem.getDebt()));
            TV_component3.setText(String.format("소비 횟수 : %d번",summeryitem.getUseDate()));

        }
        return convertView;
    }

    public void addItem(String id, int money, int debt, int date){
        SummaryItem summeryitem=new SummaryItem(id, money, debt, date);


        mItems.add(summeryitem);
    }
    public void addItem(SummaryItem summeryitem){


        mItems.add(summeryitem);
    }
}
