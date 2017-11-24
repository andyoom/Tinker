package andy.tinker;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.io.File;

import hotfix.andy.com.library.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        tv.setText(Utils.getTextContent());
        //热修复测试代码  不添加调用onClick
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv.setText("456");
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        //patch_signed_7zip.apk为生成的补丁包
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/patch_signed_7zip.apk";
        File file = new File(path);
        if (file.exists()){
            Toast.makeText(this, "补丁已经存在", Toast.LENGTH_SHORT).show();
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }else {
            Toast.makeText(this, "补丁已经不存在", Toast.LENGTH_SHORT).show();
        }
    }
}
