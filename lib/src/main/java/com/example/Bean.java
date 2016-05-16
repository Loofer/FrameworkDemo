package com.example;

import com.google.gson.Gson;

/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/24
 */
public class Bean {
    private String result_key = "";
    private String id = "";
    private String title_image_path = "";
    private String goods_type = "";
    private String price= "";
    private String kill_time= "";
    private String left_time= "";
    private String goods_id= "";
    private String sr_fee= "";
    private String goods_amount= "";
    private String total_amount= "";
    private String buy_num= "";
    private String title= "";

    public static void main(String[] args){
        System.out.println(new Gson().toJson(new Bean()));
    }

}
