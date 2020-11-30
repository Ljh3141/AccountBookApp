package kr.ac.yc.smartsw.report_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et_id;
    EditText et_pw;
    String[] permission_list = {
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CONTACTS
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        et_id=findViewById(R.id.text_id);
        et_pw=findViewById(R.id.text_pw);
    }

    public void clickBtnId(View view){
        String str_id = et_id.getText().toString();
        String str_pw = et_pw.getText().toString();

        if(str_id.equals("admin")&&str_pw.equals("1234")) {

            Intent intent = new Intent(this, MainActivity_page.class);

            intent.putExtra("Id", str_id);
            intent.putExtra("Pw", str_pw);

            startActivity(intent);
        }else{
            Toast toast_IDPWNotCorrect = Toast.makeText(this.getApplicationContext(),"id또는 pw가 정확하지 않습니다.", Toast.LENGTH_SHORT);

            toast_IDPWNotCorrect.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermission(){
        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list,0);
            }
        }
    }
    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            for(int i=0; i<grantResults.length; i++)
            {
                //허용됬다면
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                }
                else {
                    Toast.makeText(getApplicationContext(),"앱권한설정하세요",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }*/
}