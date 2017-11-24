package andy.tinker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * 类描述：
 * 创建人：yekh
 * 创建时间：2017/10/27 17:06
 */

@DefaultLifeCycle(application = ".App"
        ,flags = ShareConstants.TINKER_ENABLE_ALL
        ,loadVerifyFlag = true)
public class MyApp extends ApplicationLike{

    private static MyApp app;

    public MyApp(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        TinkerInstaller.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        app = this;
    }

    public static MyApp getMyApp(){
        return app;
    }
}
