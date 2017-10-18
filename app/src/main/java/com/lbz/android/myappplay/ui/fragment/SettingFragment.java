package com.lbz.android.myappplay.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.lbz.android.myappplay.BuildConfig;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.util.ACache;
import com.lbz.android.myappplay.commom.util.DataCleanManager;

import cn.qqtheme.framework.picker.FilePicker;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by elitemc on 2017/10/18.
 */

public class SettingFragment extends PreferenceFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        initData();
    }

    private void initData() {
        initClearCachePref();
        initDownloadDirPref();
        initVersion();
    }

    private void initClearCachePref() {
        final Preference pref = getPreferenceManager().findPreference(getString(R.string.key_clear_cache));
        try {
            pref.setSummary(DataCleanManager.getCacheSize(getActivity().getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                SettingFragment.this.clearCache().subscribe(new Consumer() {
                    public void accept(@NonNull Object o) throws Exception {
                        Toast.makeText(SettingFragment.this.getActivity(), "清理成功", Toast.LENGTH_SHORT).show();
                        pref.setSummary(DataCleanManager.getCacheSize(SettingFragment.this.getActivity().getCacheDir()));
                    }
                });
                return true;
            }
        });

    }


    private void initDownloadDirPref() {

        final Preference pref = getPreferenceManager().findPreference(getString(R.string.key_apk_download_dir));
        final String dir = ACache.get(getActivity()).getAsString(Constant.APK_DOWNLOAD_DIR);
        pref.setSummary(dir);
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                SettingFragment.this.dirChoose(pref, dir);
                return false;
            }
        });


    }

    private void initVersion() {
        final Preference pref = getPreferenceManager().findPreference(getString(R.string.key_version));
        pref.setSummary("版本："+ BuildConfig.VERSION_NAME);

    }

    private Observable clearCache() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                DataCleanManager.cleanFiles(SettingFragment.this.getActivity());
                DataCleanManager.cleanInternalCache(SettingFragment.this.getActivity());
                DataCleanManager.deleteFolderFile(ACache.get(SettingFragment.this.getActivity()).getAsString(Constant.APK_DOWNLOAD_DIR), false);
                e.onNext(Integer.valueOf(1));
                e.onComplete();
            }
        }).compose(RxHttpResponseCompose.composeSchedulers());
    }

    private void dirChoose(final Preference pref, String defualtDir) {
        FilePicker fp = new FilePicker(getActivity(), 0);
        fp.setRootPath(defualtDir);
        fp.setItemHeight(50);
        fp.setOnFilePickListener(new FilePicker.OnFilePickListener() {
            public void onFilePicked(String currentPath) {
                pref.setSummary(currentPath);
                ACache.get(SettingFragment.this.getActivity()).put(Constant.APK_DOWNLOAD_DIR, currentPath);
            }
        });
        fp.show();
    }


}
