package com.lbz.android.myappplay.presenter;

import com.hwangjr.rxbus.RxBus;
import com.lbz.android.myappplay.bean.LoginBean;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.rx.subscriber.ErrorHandleSubscriber;
import com.lbz.android.myappplay.commom.util.ACache;
import com.lbz.android.myappplay.commom.util.VerificationUtils;
import com.lbz.android.myappplay.presenter.contract.LoginContract;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Created by elitemc on 2017/9/5.
 */

public class LoginPresenter extends BasePresenter<LoginContract.ILoginModel, LoginContract.LoginView> {

    @Inject
    public LoginPresenter(LoginContract.ILoginModel mModel, LoginContract.LoginView mView) {
        super(mModel, mView);
    }

    public void login(String phone, String password) {
        if (!VerificationUtils.matcherPhoneNum(phone)) {
            mView.checkPhoneError();
            return;
        } else {
            mView.checkPhoneSuccess();
        }
        mModel.login(phone, password)
                .compose(RxHttpResponseCompose.<LoginBean>composeResult())
                .subscribe(new ErrorHandleSubscriber<LoginBean>(mContext) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.hideLoading();
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        mView.loginSuccess(loginBean);
                        saveUser(loginBean);
                        RxBus.get().post(loginBean.getUser());
                    }
                });
    }

    private void saveUser(LoginBean loginBean) {
        ACache aCache = ACache.get(mContext);

        aCache.put(Constant.TOKEN, loginBean.getToken());
        aCache.put(Constant.USER, loginBean.getUser());
    }
}
