package kr.ac.yc.smartsw.report_app;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag4UpdateList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag4UpdateList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView updateList;
    private UpdateListAdapter updateListAdapter;
    private AccountBookDBHelper acBDBHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag4UpdateList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment4.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag4UpdateList newInstance(String param1, String param2) {
        Frag4UpdateList fragment = new Frag4UpdateList();
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
        View view = inflater.inflate(R.layout.fragment_4, container, false);
        try{
            updateList = view.findViewById(R.id.update_list);
        }catch (Exception n){
            Toast listFail_toast = Toast.makeText(getActivity(),"리스트를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT);
            listFail_toast.show();
        }
        updateList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                UpdateItem updateItem = (UpdateItem) updateList.getAdapter().getItem(position);
                String strForCheck = updateItem.getUser().substring(0, 5);
                if(strForCheck.equals("변경 : ")){
                    int target = Integer.parseInt(updateItem.getUser().substring(5));
                    MainActivity_page.mainActivity_page.moveToItem(target);
                }
                return false;
            }
        });


        dataSet();
        return view;
    }

    void dataSet(){
        updateListAdapter = new UpdateListAdapter();//데이터 작업
        acBDBHelper = new AccountBookDBHelper(MainActivity_page.mainActivity_page.getApplicationContext());
        Cursor cursor = acBDBHelper.getReadableUpdateData();

        while(cursor.moveToNext()){
            int num = cursor.getInt(0);
            String user = cursor.getString(1);
            String description = cursor.getString(2);
            String date = cursor.getString(3);

            updateListAdapter.addItem(num, user,description,date);
        }
        updateList.setAdapter(updateListAdapter);

        cursor.close();
        acBDBHelper.close();
    }
}