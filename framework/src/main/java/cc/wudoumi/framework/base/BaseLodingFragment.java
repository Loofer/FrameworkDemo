package cc.wudoumi.framework.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import cc.wudoumi.framework.R;
import cc.wudoumi.framework.net.ResultCode;
import cc.wudoumi.framework.net.ResultMessage;

/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/22
 */
public abstract class BaseLodingFragment extends BaseFragment  implements ILodingView,OnReloadListener{
    private static final String LODING_FRAGMENT = "LoadingBaseFragmentLoding";
    private static final String ERROR_FRAGMENT = "LoadingBaseFragmentError";
    protected FrameLayout scuccessRootView;
    protected Handler mHandler = new Handler();
    @Override
    protected int getLayoutId() {
        return R.layout.framework_fragment_base_loding;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        scuccessRootView = findViewById(R.id.success_container);
        LayoutInflater.from(getActivity()).inflate(getSuccessLayoutId(), scuccessRootView, true);
        initSuccessView(savedInstanceState);
        onSuccessViewCreated();
    }

    protected abstract int getSuccessLayoutId();

    protected abstract void initSuccessView(Bundle savedInstanceState);

    @Override
    protected void bindDataAndLisntner() {

    }

    @Override
    public void showSuccessView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mFragmentManager.isDestroyed()){
                    return;
                }

                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                removeFragment(ERROR_FRAGMENT, transaction);
                if (needCacheLodingView()) {
                    detachFragment(LODING_FRAGMENT,transaction);
                } else {
                    removeFragment(LODING_FRAGMENT, transaction);
                }
                transaction.commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void showLodingView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mFragmentManager.isDestroyed()){
                    return;
                }
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                removeFragment(ERROR_FRAGMENT, transaction);
                //如果有加载界面且处于detach状态，直接attch，否则replce
                if (!attachFragment(LODING_FRAGMENT,transaction)) {
                    transaction.replace(R.id.empty_container, getLodingFragment(), LODING_FRAGMENT);
                }
                transaction.commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void showNoDataView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mFragmentManager.isDestroyed()) {
                    return;
                }
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                detachFragment(LODING_FRAGMENT, transaction);
                transaction.add(R.id.empty_container, getNoDataFragment(), ERROR_FRAGMENT);
                transaction.commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void showNoNetView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mFragmentManager.isDestroyed()){//网络回调导致生命周期的不确定性，故作此判断
                    return;
                }
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                detachFragment(LODING_FRAGMENT,transaction);
                transaction.add(R.id.empty_container, getNoNetFragment(), ERROR_FRAGMENT);
                transaction.commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void showErrorView(final ResultMessage resultMessage) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mFragmentManager.isDestroyed()){
                    return;
                }
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                detachFragment(LODING_FRAGMENT,transaction);
                transaction.add(R.id.empty_container, getErrorFragment(resultMessage), ERROR_FRAGMENT);
                transaction.commitAllowingStateLoss();
            }
        });
    }

    void onSuccessViewCreated(){

    }

    protected Fragment getNoDataFragment() {
        return getErrorFragment(ResultMessage.error(ResultCode.ERROR_NO_DATA, "无数据"));
    }

    protected Fragment getNoNetFragment() {

        return getErrorFragment(ResultMessage.error(ResultCode.ERROR_NO_NET, "无网络"));
    }

    protected Fragment getErrorFragment(ResultMessage message) {
        return SimpleErrorFragment.get(message.getMessage()).setOnReloadListener(this);
    }

    protected Fragment getLodingFragment() {
        return new SimpleLodingFragment();
    }


    protected boolean needCacheLodingView() {
        return false;
    }


    private void detachFragment(String tag,FragmentTransaction transaction){
        Fragment fragment = findFragmentByTag(tag);
        if (fragment != null&&fragment.isAdded()) {
            transaction.detach(fragment);
        }
    }
    private boolean attachFragment(String tag,FragmentTransaction transaction) {
        Fragment fragment = findFragmentByTag(tag);
        if (fragment != null) {
            if(fragment.isAdded()){
                return true;
            }
            if(fragment.isDetached()){
                transaction.attach(fragment);
                return true;
            }
        }
        return false;
    }
    private void removeFragment(String tag,FragmentTransaction transaction){
        Fragment fragment = findFragmentByTag(tag);
        if(fragment!=null){
            transaction.remove(fragment);
        }
    }
}
