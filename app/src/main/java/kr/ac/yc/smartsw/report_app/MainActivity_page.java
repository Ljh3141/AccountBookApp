package kr.ac.yc.smartsw.report_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity_page extends AppCompatActivity {
    private Frag3LogList frag3;
    private Frag4UpdateList frag4;
    private Frag1Summary frag1;
    private ViewPager vp;

    private long backKeyPressedTime = 0;
    private Toast toast;
    static MainActivity_page mainActivity_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        vp = findViewById(R.id.viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        VPAdapter adapter = new VPAdapter(fragmentManager);
        vp.setAdapter(adapter);

        mainActivity_page = this;

        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);
        frag1 = (Frag1Summary)adapter.getItem(0);
        frag3 = (Frag3LogList)adapter.getItem(2);
        frag4 = (Frag4UpdateList)adapter.getItem(3);

    }

    public void moveToItem(int itemIndex){
        vp.setCurrentItem(2);
        frag3.setSelection(itemIndex);
    }


    public void callAllList(int type){
        if(type==1) {
            frag1.dataSet();
            frag3.dataSet();
            frag4.dataSet();
        }else{
            frag1.dataSet();
            frag3.dataSet();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }
}