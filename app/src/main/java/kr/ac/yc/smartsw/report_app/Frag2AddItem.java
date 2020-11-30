package kr.ac.yc.smartsw.report_app;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag2AddItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag2AddItem extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner sp_kind;
    ArrayList<String> arrayList;
    ArrayAdapter<CharSequence> arrayAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AccountBookDBHelper acBookDBHelper;
    Button button_submit;
    private View view1;

    public Frag2AddItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag2AddItem newInstance(String param1, String param2) {
        Frag2AddItem fragment = new Frag2AddItem();
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
        view1 = inflater.inflate(R.layout.fragment_2, container, false);
        // Inflate the layout for this fragment

        arrayAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.spinnerItem,R.layout.sipnnerfont);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                //new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList);

        sp_kind = view1.findViewById(R.id.SP_Kind);
        sp_kind.setAdapter(arrayAdapter);
        /*sp_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        button_submit= view1.findViewById(R.id.bt_logListItemSubmit);
        button_submit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                TextView tVid = view1.findViewById(R.id.Et_add_user);
                TextView tVmoney = view1.findViewById(R.id.Et_add_money);
                TextView tDescription = view1.findViewById(R.id.Et_add_description);
                String kind_str = sp_kind.getSelectedItem().toString();
                // TextView tVKind = view1.findViewById(R.id.Et_add_kind);

                String strId = tVid.getText().toString();
                if(strId==null||strId.equals("")){
                    Toast toast = Toast.makeText(view1.getContext(),"이름을 입력해주세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                String strMoney = tVmoney.getText().toString();
                if(strMoney==null||strMoney.equals("")){
                    Toast toast = Toast.makeText(view1.getContext(),"금액을 입력해주세요", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                int money = Integer.parseInt(strMoney);

                //int kind = Integer.parseInt(tVKind.getText().toString());//수정필요
                int kind = getKindNum(kind_str);
                String description = tDescription.getText().toString();

                LogItem item = new LogItem(strId,money,kind,description,0);

                acBookDBHelper=new AccountBookDBHelper(getActivity().getApplicationContext());
                acBookDBHelper.insertOnMainData(item);

                MainActivity_page.mainActivity_page.callAllList(0);
                acBookDBHelper.close();

                Toast.makeText(getContext(), "새 항목이 추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        return view1;
    }
    public int getKindNum(String str){
        int kind = 0;
        if(str.equals("식비")){
            kind = 1;
        }else if(str.equals("쇼핑")){
            kind = 2;
        }else if(str.equals("관리비")){
            kind = 3;
        }else if(str.equals("교통비")){
            kind = 4;
        }else if(str.equals("기타")){
            kind = 5;
        }
        return kind;
    }

}