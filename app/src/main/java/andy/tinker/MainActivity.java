package andy.tinker;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import java.io.File;

import andy.tinker.util.Utils;
import hotfix.andy.com.library.TextUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Tinker.MainActivity";
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, "i am on onCreate classloader:" + MainActivity.class.getClassLoader().toString());
        //test resource change
        Log.e(TAG, "i am on onCreate string:" + getResources().getString(R.string.test_resource));

        tv = (TextView) findViewById(R.id.tv);
        tv.setText(TextUtils.getTextContent());
        //热修复测试代码  不添加调用onClick
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv.setText("456");
//            }
//        });

        findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringBuilder sb = new StringBuilder();
                Tinker tinker = Tinker.with(getApplicationContext());
                if (tinker.isTinkerLoaded()) {
                    sb.append(String.format("[patch is loaded] \n"));
                    sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
                    sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

                    sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
                    sb.append(String.format("[TINKER_ID] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName(ShareConstants.TINKER_ID)));
                    sb.append(String.format("[packageConfig patchMessage] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName("patchMessage")));
                    sb.append(String.format("[TINKER_ID Rom Space] %d k \n", tinker.getTinkerRomSpace()));

                } else {
                    sb.append(String.format("[patch is not loaded] \n"));
                    sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
                    sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

                    sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
                    sb.append(String.format("[TINKER_ID] %s \n", ShareTinkerInternals.getManifestTinkerID(getApplicationContext())));
                }
                sb.append(String.format("[BaseBuildInfo Message] %s \n", BaseBuildInfo.TEST_MESSAGE));

                Log.e(TAG, sb.toString());
            }
        });

        findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tinker.with(getApplicationContext()).cleanPatch();
            }
        });
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

    @Override
    protected void onResume() {
        Log.e(TAG, "i am on onResume");
//        Log.e(TAG, "i am on patch onResume");

        super.onResume();
        Utils.setBackground(false);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.setBackground(true);
    }
}
