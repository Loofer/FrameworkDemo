package cc.wudoumi.framework.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import cc.wudoumi.framework.R;
import cc.wudoumi.framework.net.ResultMessage;
import cc.wudoumi.framework.utils.L;
import cc.wudoumi.framework.view.SlideRootView;

import static cc.wudoumi.framework.R.layout.framework_fragment_base_with_title;

/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/17
 */
public abstract class BaseFragment extends Fragment{
    protected L logger = L.getL("BaseFragment");
    protected FragmentManager mFragmentManager;
    protected LayoutInflater mInflater;
    protected BaseActivity mActivity;
    private View rootView;
    private boolean isBindData;//是否已经绑定了数据

    protected void handlerArgment(Bundle bundle){

    }

    protected boolean needCacheRootView(){
        return true;
    }

    protected boolean bindDataEveryOnce(){
        return false;
    }


    protected boolean onBackPressed(){
        return false;
    }

    public void onCreateView(Bundle savedInstanceState) {
        logger.print();
    }

    protected View initView(LayoutInflater inflater, ViewGroup container) {
        SlideRootView slideRootView = (SlideRootView) inflater.inflate(R.layout.framework_fragment_base_with_title,container,false);
        if(getTitleLayoutId()!=0){
            ViewStub vsTitle = (ViewStub) slideRootView.findViewById(R.id.titleViewStub);
            vsTitle.setLayoutResource(getTitleLayoutId());
            vsTitle.inflate();
        }
        if(getLayoutId()!=0){
            ViewStub vsContent = (ViewStub) slideRootView.findViewById(R.id.contentViewStub);
            vsContent.setLayoutResource(getLayoutId());
            vsContent.inflate();
        }
        return slideRootView;
    }

    protected abstract int getLayoutId();

    protected int getTitleLayoutId(){
        return 0;
    }

    /**
     * 直接findViewById()初始化组件
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void bindDataAndLisntner();

    public void onFragmentBackForResult(int resultCode,Bundle data){

    }

    public void setResultData(int resultCode,Bundle data){
        mActivity.setResultData(resultCode,data);
    }


    public <T extends View> T findViewById(int resId){
        if(rootView!=null){
            return (T) rootView.findViewById(resId);
        }
        return null;
    }







    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        logger.print();
        if(!(context instanceof BaseActivity)){
            throw new RuntimeException("请与BaseActivity配合使用");
        }
        mActivity = (BaseActivity) context;
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        logger.print();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getChildFragmentManager();
        if(getArguments()!=null){
            handlerArgment(getArguments());
        }
        logger.print();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.print();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null||!needCacheRootView()) {//如果view没有被初始化或者不需要缓存的情况下，重新初始化控件
            rootView = initView(inflater, container);
            initView(savedInstanceState);
        } else {
            ViewGroup v = (ViewGroup) rootView.getParent();
            if (v != null) {
                v.removeView(rootView);
            }
        }
        onCreateView(savedInstanceState);
        return rootView;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logger.print();
        if(bindDataEveryOnce()){
            bindDataAndLisntner();
        }else{
            if(!isBindData){
                isBindData = true;
                bindDataAndLisntner();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logger.print();
    }


    @Override
    public void onStart() {
        super.onStart();
        logger.print();
    }

    @Override
    public void onStop() {
        super.onStop();
        logger.print();
    }

    @Override
    public void onResume() {
        super.onResume();
        logger.print();
    }

    @Override
    public void onPause() {
        super.onPause();
        logger.print();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logger.print();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        logger.print();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        logger.print();
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        logger.print();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        logger.print();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logger.print();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        logger.print();
    }

    public void startFragment(Fragment fragment,String tag,boolean canBack){
        mActivity.startFragment(fragment,tag,canBack);
    }
    public void startFragment(Fragment fragment){
        mActivity.startFragment(fragment);
    }
    public void startFragment(Class<? extends Fragment> clazz){
        mActivity.startFragment(clazz);
    }
    public void startFragment(Class<? extends Fragment> clazz,Bundle bundle){
        mActivity.startFragment(clazz,bundle);
    }

    public void startFragmentCanBack(Fragment fragment){
        mActivity.startFragmentCanBack(fragment);
    }
    public void startFragmentCanBack(Class<? extends Fragment> clazz){
        mActivity.startFragmentCanBack(clazz);
    }
    public void startFragmentCanBack(Class<? extends Fragment> clazz,Bundle bundle){
       mActivity.startFragmentCanBack(clazz,bundle);
    }


    public String getFragmentTag(){
        return getClass().getSimpleName();
    }


    @Override
    public void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        mActivity.startActivityForResult(intent, requestCode);
    }


    public Fragment findFragmentByTag(String tag) {
        return mFragmentManager.findFragmentByTag(tag);
    }

}
