package com.lbz.android.myappplay.ui.activity;


import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.LoginBean;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerLoginComponent;
import com.lbz.android.myappplay.di.module.LoginModule;
import com.lbz.android.myappplay.presenter.LoginPresenter;
import com.lbz.android.myappplay.presenter.contract.LoginContract;
import com.lbz.android.myappplay.ui.widget.LoadingButton;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.Bind;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {


    @Bind(R.id.tool_bar)
    Toolbar toolBar;
    @Bind(R.id.txt_mobi)
    EditText txtMobi;
    @Bind(R.id.view_mobi_wrapper)
    TextInputLayout viewMobiWrapper;
    @Bind(R.id.txt_password)
    EditText txtPassword;
    @Bind(R.id.view_password_wrapper)
    TextInputLayout viewPasswordWrapper;
    @Bind(R.id.btn_login)
    LoadingButton btnLogin;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder().appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build().inject(this);
    }

    @Override
    protected void init() {
        setTitle("登录");
        setSupportActionBar(toolBar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Observable<CharSequence> mobileObservable = RxTextView.textChanges(txtMobi);
        Observable<CharSequence> passwordObservable = RxTextView.textChanges(txtPassword);

        Observable.combineLatest(mobileObservable, passwordObservable, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence mobi, CharSequence pwd) {
                return isPhoneValid(mobi.toString()) && isPasswordValid(pwd.toString());
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                RxView.enabled(btnLogin).call(aBoolean);
            }
        });

        RxView.clicks(btnLogin).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.login(txtMobi.getText().toString().trim(), txtPassword.getText().toString().trim());
            }
        });
    }


    private boolean isPhoneValid(String phone) {
        return phone.length() == 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }


    @Override
    public void checkPhoneError() {
        viewMobiWrapper.setError("手机号格式错误");
    }

    @Override
    public void checkPhoneSuccess() {
        viewMobiWrapper.setError("");
        viewMobiWrapper.setErrorEnabled(false);
    }

    @Override
    public void loginSuccess(LoginBean bean) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void showLoading() {
        btnLogin.showLoading();

    }

    @Override
    public void showError(String msg) {
        btnLogin.showButtonText();
    }

    @Override
    public void hideLoading() {
        btnLogin.showButtonText();
    }
}
