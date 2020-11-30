package kr.ac.yc.smartsw.report_app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag3LogList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag3LogList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final int ITEM_PRESSED = 1;
    private static final int UPDATE_OK = 2;
    private static final int UPDATE_CN = 3;
    private static final int DELETE = 4;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView log_list;
    private View view;
    private LogListAdapter logListAdapter;
    private AccountBookDBHelper acBookDBHelper;
    ArrayList<Integer> numList;

    public Frag3LogList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag3LogList newInstance(String param1, String param2) {
        Frag3LogList fragment = new Frag3LogList();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_3, container, false);
        try{
            log_list = view.findViewById(R.id.log_list2);
        }catch (Exception n){
            Toast listFail_toast = Toast.makeText(getActivity(),"리스트를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT);
            listFail_toast.show();
        }
        log_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//길게눌렀을때 구현
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {//데이터 오래 터치시 수정창 호출
                LogItem item = (LogItem)log_list.getAdapter().getItem(position);//선택된 아이템 불러오기

                Intent intent = new Intent(getActivity().getApplicationContext(), UpdatePopup.class);
                Bundle bundle = new Bundle();
                bundle.putInt("itemNum", item.getNum());
                bundle.putString("user", item.getUser());
                bundle.putString("description", item.getDescription());
                bundle.putInt("money", item.getMoney());
                bundle.putInt("kind", item.getKind());
                bundle.putString("date", item.getUseDate());
                intent.putExtra("data", bundle);

                startActivityForResult(intent, ITEM_PRESSED);

                return false;//intent로 popup불러올것.
            }
        });
        dataSet();
        // Inflate the layout for this fragment
        return view;
    }



    void dataSet() {
        logListAdapter = new LogListAdapter();//이곳에 파일 데이터 작업
        /*for(int i=0; i<30; i++){
            logListAdapter.addItem("user_"+i+"log", 500*i, i, i+1000);
        }*///임시 데이터 추가
        /*File file = new File(getContext().getFilesDir(), "moneyData.txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str = null;
            while((str=br.readLine())!=null){
                String[] array = str.split(":");
                String user = array[0];
                int money = Integer.parseInt(array[1]);
                int kind = Integer.parseInt(array[2]);
                int date = Integer.parseInt(array[3]);
                logListAdapter.addItem(user, money, kind, date);
            }
            br.close();
        }catch (IOException e){
                e.printStackTrace();
        }*/ //db로 재작업

        acBookDBHelper=new AccountBookDBHelper(getActivity().getApplicationContext());
        Cursor cursor = acBookDBHelper.getMainData();
        numList = new ArrayList<>();
        while(cursor.moveToNext()){
            int num = cursor.getInt(0);
            numList.add(num);

            String user=cursor.getString(1);
            int money = cursor.getInt(3);
            int kind = cursor.getInt(4);
            String description = cursor.getString(2);
            String date = cursor.getString(5);

            logListAdapter.addItem(user,kind,money, description,date, num);
        }

        log_list.setAdapter(logListAdapter);
        acBookDBHelper.close();
        cursor.close();
    }

    //public void addData(Logitem item){//txt->db로 제거됨
        /*if(item!=null) {
            boolean isSave = false;
            String strData = item.getSaveText();
            File file = new File(getContext().getFilesDir(), "moneyData.txt");
            try{//text파일에 데이터 저장
                BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
                buf.write(strData);
                buf.newLine();
                buf.flush();
                buf.close();
                isSave=true;
            }catch(FileNotFoundException e){
                e.printStackTrace();
                Toast toast = Toast.makeText(getActivity(), "파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT);
                toast.show();
            }catch(IOException e){
                e.printStackTrace();
                Toast toast = Toast.makeText(getActivity(), "값이 저장되지 않았습니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
            if(isSave) {
                try {
                    logListAdapter.addItem(item);
                    logListAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getActivity(), "정상적이지 않은 값이 있습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }else{
            Toast toast = Toast.makeText(getActivity(), "item이 null입니다.", Toast.LENGTH_SHORT);
            toast.show();
        }*/
    //}//db로 재작업

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if(requestCode == ITEM_PRESSED){
            switch (resultCode){
                case UPDATE_OK:
                    Bundle bundle = intent.getBundleExtra("data");
                    LogItem logitem = new LogItem(bundle.getString("user"), bundle.getInt("money"), bundle.getInt("kind"), bundle.getString("description"), bundle.getInt("itemNum"), bundle.getString("date"));
                    UpdateItem updateItem = new UpdateItem(0, "변경 : "+String.format("%d", logitem.getNum()), bundle.getString("change_log"));


                    acBookDBHelper=new AccountBookDBHelper(getActivity().getApplicationContext());
                    acBookDBHelper.updateMainData(logitem);
                    acBookDBHelper.insertOnUpdateData(updateItem);

                    MainActivity_page.mainActivity_page.callAllList(1);
                    log_list.setSelection(numList.indexOf(logitem.getNum()));
                    acBookDBHelper.close();

                    Toast toast=Toast.makeText(getActivity().getApplicationContext(),"변경되었습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                case UPDATE_CN:
                    toast=Toast.makeText(getActivity().getApplicationContext(),"변경되지 않았습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                case DELETE :
                    bundle = intent.getBundleExtra("data");
                    int itemNum = bundle.getInt("itemNum");
                    if(itemNum == -1){

                        toast=Toast.makeText(getActivity().getApplicationContext(),"삭제 오류 발생", Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    }
                    acBookDBHelper=new AccountBookDBHelper(getActivity().getApplicationContext());
                    acBookDBHelper.deleteMainData(itemNum);
                    updateItem = new UpdateItem(0, "삭제 :"+String.format("%d", itemNum),bundle.getString("description"));
                    acBookDBHelper.insertOnUpdateData(updateItem);
                    //업데이트 데이터에 삭제 기록추가

                    dataSet();
                    MainActivity_page.mainActivity_page.callAllList(1);
                    acBookDBHelper.close();
                    break;
            }
        }
    }

    public void setSelection(int i){
        log_list.setSelection(numList.indexOf(i));
    }
}