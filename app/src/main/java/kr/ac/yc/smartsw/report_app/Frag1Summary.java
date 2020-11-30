package kr.ac.yc.smartsw.report_app;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag1Summary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag1Summary extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    private ListView sammaryList;
    private SummaryListAdapter sammeryListAdapter;
    private SummaryData samData;
    private AccountBookDBHelper acBookDBHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag1Summary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag1Summary newInstance(String param1, String param2) {
        Frag1Summary fragment = new Frag1Summary();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_1, container, false);
        // Inflate the layout for this fragment
        try{
            sammaryList = view.findViewById(R.id.sammury_list);
        }catch(Exception e){
            Toast listFail_toast = Toast.makeText(getActivity(),"리스트를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT);
            listFail_toast.show();
        }

        dataSet();
        return view;
    }

    public void dataSet(){
        sammeryListAdapter = new SummaryListAdapter();
        samData=new SummaryData();
        acBookDBHelper=new AccountBookDBHelper(getActivity().getApplicationContext());
        Cursor cursor = acBookDBHelper.getMainData();

        while(cursor.moveToNext()){
            String user=cursor.getString(1);
            int money = cursor.getInt(3);
            samData.setMoneyMap(user, money);
        }
        if(samData.getCount()!=0){
            SummaryItem temp = new SummaryItem("총계",samData.getCount(),samData.getSum(),samData.getAverage());
            int average = samData.getAverage();
            sammeryListAdapter.addItem(temp);
            Map<String, Integer> data = samData.getMoneyMap();
            Map<String, Integer> countData = samData.getCountMap();
            Iterator<String> keys = data.keySet().iterator();
            while(keys.hasNext()){
                String key = keys.next();
                int money = data.get(key);
                int count = countData.get(key);
                int debt = average - money;
                temp = new SummaryItem(key, money, debt, count);
                sammeryListAdapter.addItem(temp);

            }
        }
        /*File file = new File(getContext().getFilesDir(), "moneyData.txt");
        sammary_data samData = new sammary_data();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str = null;
            while((str=br.readLine())!=null){
                String[] array = str.split(":");
                String user = array[0];
                int money = Integer.parseInt(array[1]);
                int kind = Integer.parseInt(array[2]);
                int date = Integer.parseInt(array[3]);
                samData.setMoneyMap(user,money);
            }
            br.close();
            if(samData.getCount()!=0){
                Summeryitem temp = new Summeryitem("총계",samData.getCount(),samData.getSum(),samData.getAverage());
                int average = samData.getAverage();
                sammeryListAdapter.addItem(temp);
                Map<String, Integer> data = samData.getMoneyMap();
                Iterator<String> keys = data.keySet().iterator();
                while(keys.hasNext()){
                    String key = keys.next();
                    int money = data.get(key);
                    int debt = average - money;
                    temp = new Summeryitem(key, money, debt, 0);
                    sammeryListAdapter.addItem(temp);

                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }*/

        sammaryList.setAdapter(sammeryListAdapter);
    }
}