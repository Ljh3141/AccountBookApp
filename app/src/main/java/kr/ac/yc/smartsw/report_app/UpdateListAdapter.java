package kr.ac.yc.smartsw.report_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UpdateListAdapter extends BaseAdapter {
    private ArrayList<UpdateItem> mItems = new ArrayList<>();
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public UpdateItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context =  parent.getContext();
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.updateitem_custom, parent, false);
        }
        TextView tv_user = (TextView) convertView.findViewById(R.id.TV_Update_user);
        TextView tv_num = (TextView) convertView.findViewById(R.id.TV_Update_num);
        TextView tv_description = (TextView) convertView.findViewById(R.id.TV_Update_Description);
        TextView tv_date = (TextView) convertView.findViewById(R.id.TV_Update_Date);

        UpdateItem updateItem = getItem(position);

        tv_user.setText(updateItem.getUser());
        tv_num.setText(String.format("%d",updateItem.getNum()));
        tv_description.setText(updateItem.getDescription());
        tv_date.setText(updateItem.getDate());
        return convertView;
    }
    public void addItem(int num, String user, String description,String date){
        UpdateItem updateItem = new UpdateItem(num, user, description);
        updateItem.setDate(date);

        mItems.add(updateItem);
    }

}
