package cc.wudoumi.framework.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;

import cc.wudoumi.framework.R;
import cc.wudoumi.framework.utils.ToastUtil;


/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/24
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String FIRST_FRAGMENT_TAG = "FIRST_FRAGMENT_TAG";
    protected FragmentManager mFragmentManager;
    private Fragment currentFragment;

    private int resultCode = RESULT_CANCELED;
    private Bundle data;


    public void setResultData(int resultCode,Bundle data){
        this.resultCode = resultCode;
        this.data = data;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            startFragment(getFirstFragment(),FIRST_FRAGMENT_TAG,false,false);
        }

        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                currentFragment = getCurrentFragment();
                if(currentFragment!=null&&currentFragment instanceof BaseFragment&&resultCode!=RESULT_CANCELED){
                    ((BaseFragment)currentFragment).onFragmentBackForResult(resultCode,data);
                }
                resultCode = RESULT_CANCELED;
                data = null;
            }
        });


    }
    protected int getLayoutId(){return R.layout.framework_activity_base;}
    protected int getContainerId() {
        return R.id.framework_activity_container;
    }

    protected abstract Fragment getFirstFragment();

    @Override
    public final void onBackPressed() {
        currentFragment = getCurrentFragment();
        if(currentFragment!=null&&currentFragment instanceof BaseFragment){
            BaseFragment fragment = (BaseFragment) currentFragment;
            if(fragment.onBackPressed()){
                return;
            }
        }
        superOnBackPressed();
    }

    public void superOnBackPressed(){
        if(mFragmentManager.getBackStackEntryCount()==0){
            if(!isExitImmediate()){
                long currentTime = System.currentTimeMillis();
                if(currentTime-lastTime>2000){
                    lastTime = currentTime;
                    showToast("再按一次退出");
                    return;
                }
            }
            if(resultCode!=RESULT_CANCELED){
                if(data==null){
                    setResult(resultCode);
                }else{
                    Intent intent = new Intent();
                    intent.putExtras(data);
                    setResult(resultCode,intent);
                }
            }
        }
        super.onBackPressed();
        if(mFragmentManager.getBackStackEntryCount()==0){
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }


    private long lastTime;
    protected boolean isExitImmediate(){
        return true;
    }

    public void showToast(String msg){
        ToastUtil.showToast(this,msg);
    }


    public Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(getContainerId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    public Fragment getFragmentFromClass(Class<? extends Fragment> clazz, Bundle bundle) {
        return Fragment.instantiate(this, clazz.getName(), bundle);
    }

    public Fragment getFragmentFromClass(Class<? extends Fragment> clazz) {
        return Fragment.instantiate(this, clazz.getName());
    }
    public void startFragment(Fragment fragment, String tag, boolean canBack,boolean animation) {
        if (TextUtils.isEmpty(tag)) {
            tag = fragment.getClass().getName();
        }

        FragmentTransaction f = mFragmentManager.beginTransaction();
        if(animation){
            f.setCustomAnimations(R.anim.right_in,
                    R.anim.left_out,
                    R.anim.left_in,
                    R.anim.right_out
            );
        }
        f.replace(getContainerId(), fragment, tag);
        if (canBack) {
            f.addToBackStack(tag);
        }
        f.commitAllowingStateLoss();
    }
    public void startFragment(Fragment fragment, String tag, boolean canBack) {
        startFragment(fragment,tag,canBack,true);
    }

    public void startFragment(Fragment fragment) {
        startFragment(fragment, null, false);
    }

    public void startFragment(Class<? extends Fragment> clazz) {
        startFragment(getFragmentFromClass(clazz, null), clazz.getName(), false);
    }

    public void startFragment(Class<? extends Fragment> clazz, Bundle bundle) {
        startFragment(getFragmentFromClass(clazz, bundle), clazz.getName(), false);
    }

    public void startFragmentCanBack(Fragment fragment) {
        startFragment(fragment, null, true);
    }

    public void startFragmentCanBack(Class<? extends Fragment> clazz) {
        startFragment(getFragmentFromClass(clazz, null), clazz.getName(), true);
    }

    public void startFragmentCanBack(Class<? extends Fragment> clazz, Bundle bundle) {
        startFragment(getFragmentFromClass(clazz, bundle), clazz.getName(), true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentFragment = getCurrentFragment();
        if(currentFragment!=null){
            currentFragment.onActivityResult(requestCode,resultCode,data);
        }
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}
