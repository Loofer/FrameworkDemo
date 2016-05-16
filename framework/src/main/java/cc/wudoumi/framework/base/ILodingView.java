package cc.wudoumi.framework.base;

import cc.wudoumi.framework.net.ResultMessage;

/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/22
 */
public interface ILodingView {
    void showSuccessView();

    void showNoDataView();

    void showNoNetView();

    void showErrorView(ResultMessage resultMessage);

    void showLodingView();
}
