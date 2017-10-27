package com.lbz.android.myappplay.data;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.apkparset.AndroidApk;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.util.ACache;
import com.lbz.android.myappplay.commom.util.AppUtils;
import com.lbz.android.myappplay.commom.util.HttpPostRequestMap;
import com.lbz.android.myappplay.data.http.Repository;
import com.lbz.android.myappplay.presenter.contract.AppManagerContract;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by lbz on 2017/9/21.
 */

public class AppManagerModel implements AppManagerContract.IAppManagerModel {

    private RxDownload mRxDownload;

    private Context mContext;

    private Repository mRepository;

    public AppManagerModel(Context context, RxDownload rxDownload ,Repository repository) {

        this.mContext = context;

        mRxDownload = rxDownload;

        mRepository = repository;

    }

    @Override
    public Observable<List<DownloadRecord>> getDownloadRecord() {

        return mRxDownload.getTotalDownloadRecords();

    }

    @Override
    public Observable<List<AndroidApk>> getLocalApks() {

        final String dir = ACache.get(mContext).getAsString(Constant.APK_DOWNLOAD_DIR);

        return Observable.create(new ObservableOnSubscribe<List<AndroidApk>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AndroidApk>> e) throws Exception {

                e.onNext(scanApks(dir));
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<AndroidApk>> getInstallApps() {
        return Observable.create(new ObservableOnSubscribe<List<AndroidApk>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AndroidApk>> e) throws Exception {

                e.onNext(getInstallAppsList());
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<PageBean> getCanUpdateApps(final boolean update) {

       return getInstallApps()
               .compose(RxHttpResponseCompose.composeSchedulers())
               .flatMap(new Function<List<AndroidApk>, ObservableSource<HashMap<String, String>>>() {
            @Override
            public ObservableSource<HashMap<String, String>> apply(@NonNull List<AndroidApk> androidApks) throws Exception {

                String packageName="";
                String versionCode="";
                for (AndroidApk androidApk:androidApks) {
                    Log.e("11111","androidApks getAppVersionName="+androidApk.getAppName()+androidApk.getAppVersionName());

                    packageName=packageName+androidApk.getPackageName()+",";
                    versionCode=versionCode+androidApk.getAppVersionCode()+",";
                }
                Log.e("11111","packageName="+packageName);
                Log.e("11111","versionCode="+versionCode);
                HashMap<String, String> fields = HttpPostRequestMap.getSingleton().getHttpPostRequestMap(mContext);
                fields.put("packageName",packageName);
                fields.put("versionCode", versionCode);
                return Observable.just(fields);
            }
        })
                .flatMap(new Function<HashMap<String, String>, ObservableSource<PageBean>>() {
                    @Override
                    public ObservableSource<PageBean> apply(@NonNull HashMap<String, String> requestMap) throws Exception {
                        return mRepository.getCanUpdateAppList(requestMap,update).compose(RxHttpResponseCompose.composeSchedulers());
                    }
                }).doOnNext(new Consumer<PageBean>() {
                   @Override
                   public void accept(PageBean pageBean) throws Exception {
                       for (AppInfo app:pageBean.getListApp()) {
                           AppUtils.getNewAppInfoWithLoaclVersionName(mContext,app);
                       }
                   }
               });



//        CanUpdateAppRequestBean canUpdateAppRequestBean =new CanUpdateAppRequestBean();
//        String packageName = "com.android.inputmethod.pinyin,com.android.pacprocessor,com.android.vpndialogs,com.lenovo.romfeatureguide,com.android.location.fused,com.lenovo.lsf.device,com.lenovo.email,com.android.cellbroadcastreceiver,com.UCMobile,com.lenovo.videoplayer,com.lenovo.systemuiplus,com.lenovo.optimize,com.qualcomm.msapm,com.ting.mp3.oemc.android,android,com.lenovo.calculator,com.android.musicfx,com.lenovo.leos.simsettings,com.sand.airdroid,com.lenovo.ue.service,com.android.keychain,com.qualcomm.location,com.qualcomm.services.location,com.lenovo.weather.theme.plus,com.lenovo.ota,com.svox.pico,com.android.incallui,cn.eliteu.android,com.qualcomm.interfacepermissions,com.highandes.TrakAx,com.android.systemui,com.android.externalstorage,com.android.settings,com.android.providers.settings,com.android.bluetooth,com.android.profilesettings,com.android.smspush,com.android.keyguard.external_res,com.android.defcontainer,com.lenovo.smartrotation,com.lenovo.wifiswitch,com.android.musicvis,com.lenovo.calendar,com.validation,com.android.onetimeinitializer,com.android.stk,com.lenovo.powercenter,com.android.providers.downloads,com.tmall.wireless,com.android.soundrecorder,com.android.providers.contacts,com.android.keyguard,com.sohu.sohuvideo,com.dsi.ant.server,com.xiaomi.market,com.lenovo.leos.lenovoservicesetting,com.lenovo.lasf,com.baidu.searchbox_lenovo,com.lenovo.launcher.launcherextend,com.lenovo.lenovoweathertheme,com.android.providers.media,com.android.dreams.basic,com.android.packageinstaller,com.tencent.mm,com.tencent.mobileqq,com.lenovo.videotalk.phone,com.lenovo.lsf,com.sina.weibo,com.sohu.newsclient,com.lenovo.launcher,com.lenovo.weather.theme.dreamland,com.lenovo.ideawallpaper,cn.darkal.networkdiagnosis,com.lenovo.loggerpannel,com.lai.android,com.lenovo.deskclock,com.lenovo.supercontacts,com.android.wallpaper,org.codeaurora.ims,com.qualcomm.qcrilmsgtunnel,cn.eliteu.android.enterprise,com.lenovo.magicplus,com.lenovo.service,com.lenovo.GPS,com.qualcomm.gsmtuneaway,com.android.sharedstoragebackup,com.lenovo.levoice.car,com.android.documentsui,lenovo,com.sohu.inputmethod.sogouoem,com.lenovo.lenovosearch,com.lenovo.exchange,com.android.providers.userdictionary,com.iyd.reader.ReadingJoy,com.android.wallpapercropper,com.lenovo.supernote,com.lenovo.miracast,com.android.shell,com.lenovo.FileBrowser,com.lenovo.leos.cloud.sync,com.android.certinstaller,com.android.noisefield,com.eg.android.AlipayGphone,com.lenovo.carapplication,com.lenovo.game.lockscreen.arkanoid,com.tongbu.tui.usbproxy,com.android.proxyhandler,com.lenovo.android.settings.tether,com.qualcomm.shutdownlistner,com.IdeaFriendSecreteCode,com.lenovo.menu_assistant,com.lenovo.ideafriend,com.lenovo.setupwizard,com.lenovo.SleeplogViewer,com.lenovo.music,com.lenovo.fm,com.android.wallpaper.livepicker,com.lenovo.anyshare,com.android.phasebeam,com.lenovo.themecenter,com.lenovo.lewea,com.lenovo.smartstandby,com.vhall.live,com.lenovo.gameworldphone,com.amap.android.location,com.android.printspooler,com.lenovo.game.lockscreen.arkanoid_bubble2,com.lenovo.weatherserver,com.lenovo.lsf.pay.phone,com.lenovo.wifidirect.share,org.codeaurora.bluetooth,com.lenovo.SdcardOperate,com.tencent.android.qqdownloader,com.wandoujia.phoenix2.usbproxy,com.android.backupconfirm,com.lenovo.lasf_lite,com.android.wallpaper.holospiral,com.lenovo.PlayersTutorial,com.baidu.BaiduMap,com.lenovo.lasf.tts,com.qualcomm.fastdormancy,com.lenovo.compass,com.lenovo.SecretCode,com.android.galaxy4,com.android.dreams.phototable,com.android.providers.telephony,com.android.providers.calendar,com.lenovo.scg,com.qualcomm.timeservice,com.lenovo.widetouch,com.lenovo.browser,com.android.magicsmoke,com.lenovo.updateassist,com.alipay.mobile.scanx,com.lenovo.safecenter,com.qualcomm.qcom_qmi,com.lenovo.onekeylock,com.lenovo.leos.appstore,com.android.phone,com.android.providers.downloads.ui,com.android.inputmethod.latin,com.lenovo.lenovosmartscene,com.qualcomm.wfd.service,app.greyshirts.sslcapture,com.android.inputdevices";
//        String versionCode = "19,19,19,1000010,19,403010961,20040254,19,685,3000301,1010064,1,1,35,19,2001032,10400,19,30205,44,19,1,1,1000019,3030124,1,2000000,123,1,4,2030487,19,19,19,19,19,19,1000005,19,20140423,1,19,2090557,19,19,19,20060244,19,1584,20030159,2000067,2010616,3310,30100,147,4060293,2149,25318016,1,1,700,19,19,1000,482,2014030539,4220850,651,58,6106920,1000058,0,30,1,1,20010142,1035,19,1,19,103,4080498,2014080800,1,19,19,12,19,2,240,1040045,20040035,19,565,19,1300724,1,19,30000071,3941074,19,1,107,2010061,1000082,109,19,3200112,19,1,6035,3000908,1080022,1,30010327,20030194,19,4030698,1,2500231,2008182,2,290,4851690,315,1,1000074,1000011,3920180,1,19,1,7102130,6215,19,2069,19,30640032,461,2211,19,3000074,1,1,19,19,19,7140707,19,15,40050091,19,2060184,2,4253368,1,10000,90400,19,19,19,2,2,13,19";
//        canUpdateAppRequestBean.setPackageName(packageName);
//        canUpdateAppRequestBean.setVersionCode(versionCode);
//        HashMap<String, String> fields = HttpPostRequestMap.getSingleton().getHttpPostRequestMap(mContext);
//        fields.put("packageName",packageName);
//        fields.put("versionCode", versionCode);



//        return mApiService.getCanUpdateAppList(fields).compose(RxHttpResponseCompose.composeSchedulers());
    }

    private List<AndroidApk> getInstallAppsList() {
        List<AndroidApk> appList = new ArrayList<>();

        PackageManager pm = mContext.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);

        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {
                AndroidApk info = new AndroidApk();
                info.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
                info.setPackageName(packageInfo.packageName);
                info.setDrawable(packageInfo.applicationInfo.loadIcon(pm));
                info.setAppVersionCode(packageInfo.versionCode+"");
                info.setAppVersionName(packageInfo.versionName);
                appList.add(info);
            } else {
                // 系统应用　　　　　　　　
            }

        }

        return appList;
    }

    private List<AndroidApk> scanApks(String dir) {

        File file = new File(dir);

        if (!file.isDirectory()) {

            throw new RuntimeException("is not Dir");
        }

        File[] apks = file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File f) {

                if (f.isDirectory()) {
                    return false;
                }

                return f.getName().endsWith(".apk");
            }
        });

        List<AndroidApk> androidApks = new ArrayList<>();

        for (File apk : apks) {

            AndroidApk androidApk = AndroidApk.read(mContext, apk.getPath());

            if (androidApk != null) {

                androidApks.add(androidApk);

            }

        }

        return androidApks;

    }

}
