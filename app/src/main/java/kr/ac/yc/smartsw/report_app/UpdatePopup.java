package kr.ac.yc.smartsw.report_app;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UpdatePopup extends AppCompatActivity implements View.OnClickListener {
    private static final int UPDATE_OK = 2;
    private static final int UPDATE_CN = 3;
    private static final int DELETE = 4;
    Intent intent;
    Bundle bundle;
    EditText et_user;
    EditText et_description;
    EditText et_money;
    Spinner sp_kind;
    LogItem logitem;
    String change_log;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_update);
        setTitle("데이터 수정");
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();


        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);

        et_user = findViewById(R.id.ET_Update_user);
        et_description = findViewById(R.id.ET_Update_Description);
        et_money = findViewById(R.id.ET_Update_Money);
        sp_kind = findViewById(R.id.SP_Kind_Update);//스피너 생성

        arrayList = new ArrayList<>();
        arrayList.add("식비");
        arrayList.add("쇼핑");
        arrayList.add("관리비");
        arrayList.add("교통비");
        arrayList.add("기타");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arrayList);
        sp_kind.setAdapter(arrayAdapter);

        change_log = "";

        intent = getIntent();
        bundle = intent.getBundleExtra("data");

        logitem = new LogItem(bundle.getString("user"),bundle.getInt("money"), bundle.getInt("kind"), bundle.getString("description"), bundle.getInt("itemNum"));
        logitem.setUseDate(bundle.getString("date"));

        et_user.setText(logitem.getUser());
        et_description.setText(logitem.getDescription());
        et_money.setText(String.format("%d",logitem.getMoney()));
        //et_kind.setText(String.format("%d",logitem.getKind()));수정필요
        sp_kind.setSelection(logitem.getKind()-1);
        return;
    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_update:
                if(et_user.getText().toString().equals("")){
                    Toast.makeText(this,"이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }else if(et_money.getText().toString().equals("")||et_money.getText().toString().equals("0")){
                    Toast.makeText(this,"금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    break;
                }


                String update_user = et_user.getText().toString();
                String update_description = et_description.getText().toString();
                int update_money = Integer.parseInt(et_money.getText().toString());
                String update_kind_str = sp_kind.getSelectedItem().toString();
                //int update_kind = Integer.parseInt(update_kind_str);//변경
                int update_kind = getKindNum(update_kind_str);
                boolean isChanged = false;
                if(!logitem.getUser().equals(update_user)){
                    change_log = change_log+"등록자 변경:"+logitem.getUser()+"->"+update_user+"\n";
                    logitem.setUser(update_user);
                    isChanged = true;
                }
                if(!logitem.getDescription().equals(update_description)){
                    String str = logitem.getDescription();
                    if(str.equals("")){
                        str = "내용 없음";
                    }
                    change_log = change_log+"설명 변경, 변경전:"+str+"\n";
                    logitem.setDescription(update_description);
                    isChanged = true;
                }
                if(logitem.getMoney()!=update_money){
                    change_log = change_log+"금액 변경:"+logitem.getMoney()+"원->"+update_money+"원"+"\n";
                    logitem.setMoney(update_money);
                    isChanged=true;
                }
                if(logitem.getKind() != update_kind){
                    change_log = change_log +"분류 변경:"+getKindStr(logitem.getKind())+"->"+getKindStr(update_kind);
                    logitem.setKind(update_kind);
                    isChanged=true;
                }
                if(isChanged){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("itemNum", logitem.getNum());
                    bundle.putString("user", logitem.getUser());
                    bundle.putString("description", logitem.getDescription());
                    bundle.putInt("money", logitem.getMoney());
                    bundle.putInt("kind", logitem.getKind());
                    bundle.putString("change_log", change_log);
                    bundle.putString("date", logitem.getUseDate());
                    intent.putExtra("data", bundle);
                    setResult(UPDATE_OK, intent);
                    this.finish();
                }else{
                    setResult(UPDATE_CN);
                    this.finish();
                }

                /*if(isChanged){
                    MainActivity_page mainActivity_page = (MainActivity_page)getParent();//현재 버그발생 지점
                    accountBookDBHelper acBookDBHelper = new accountBookDBHelper(getApplicationContext());

                    acBookDBHelper.updateMainData(logitem);//mainData변경
                    UpdateItem updateItem = new UpdateItem(0, update_user, change_log);

                    mainActivity_page.callList(updateItem);
                    Toast toast=Toast.makeText(this.getApplicationContext(),"변경되었습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    this.finish();
                }else {
                    Toast toast=Toast.makeText(this.getApplicationContext(),"변경되지 않았습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    this.finish();
                }*/
                break;
            case R.id.btn_delete:

                AlertDialog.Builder alertDialogBuiler = new AlertDialog.Builder(this);
                alertDialogBuiler.setTitle("데이터 삭제");
                alertDialogBuiler.setMessage("항목을 삭제하시겠습니까?").setCancelable(false).setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//확인 호출

                        Intent intent = new Intent();
                        String description = "사용자 : "+logitem.getUser()+" / 분류 : "+getKindStr(logitem.getKind())+" / 금액 : "+String.format("%d",logitem.getMoney())+"원\n"+"설명 : "+
                                logitem.getDescription()+"\n작성일시 : "+logitem.getUseDate();
                        Bundle bundle = new Bundle();
                        bundle.putInt("itemNum", logitem.getNum());
                        bundle.putString("description", description);
                        intent.putExtra("data", bundle);
                        setResult(DELETE, intent);
                        finish();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuiler.create();

                alertDialog.show();//확인창 보이기
                break;
        }
    }
    public String getKindStr(int i){
        String result="";
        switch(i){
            case 1:
                result = "식비";
                break;
            case 2:
                result = "쇼핑";
                break;
            case 3:
                result = "관리비";
                break;
            case 4:
                result = "교통비";
                break;
            case 5:
                result = "기타";
                break;
        }
        return result;
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