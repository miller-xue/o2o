package com.miller.o2o.common;

import java.util.HashMap;

/**
 * Created by miller on 2019/3/9
 *
 * @author Miller
 */
public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = -4161247644188412719L;

    public AjaxResult() {
    }

    /**
     * 添加一个状态
     * @param state 状态
     * @return
     */
    public AjaxResult state(BaseState state) {
        return state(state.getState(), state.getStateInfo());
    }

    /**
     * 添加一个状态信息，
     * @param state 状态码
     * @param stateInfo 状态信息
     * @return
     */
    public AjaxResult state(int state, String stateInfo) {
        this.put("state", state);
        this.put("stateInfo", stateInfo);
        return this;
    }

    /**
     * 返回重定向信息
     * @param redirect 是否重定向
     * @return
     */
    public static AjaxResult redirect(boolean redirect) {
        AjaxResult json = new AjaxResult();
        json.put("redirect", redirect);
        return json;
    }

    public static AjaxResult redirect(boolean redirect,String url) {
        AjaxResult json = new AjaxResult();
        json.put("redirect", redirect);
        json.put("url", url);
        return json;
    }

    /**
     * 返回失败消息
     *
     * @param msg 内容
     * @return 失败消息
     */
    public static AjaxResult error(String msg) {
        AjaxResult json = new AjaxResult();
        json.put("success", false);
        json.put("msg", msg);
        return json;
    }

    /**
     * 返回失败
     * @return
     */
    public static AjaxResult error() {
        AjaxResult json = new AjaxResult();
        json.put("success", false);
        return json;
    }

    public static AjaxResult error(Exception e) {
        AjaxResult json = new AjaxResult();
        json.put("success", false);
        json.put("msg", e.getMessage());
        return json;
    }



    /**
     * 返回成功消息
     *
     * @param msg 内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg)
    {
        AjaxResult json = new AjaxResult();
        json.put("msg", msg);
        json.put("success", true);
        return json;
    }
    /**
     *
     * 返回成功
     */
    public static AjaxResult success()
    {
        AjaxResult json = new AjaxResult();
        json.put("success", true);
        return json;
    }



    /**
     * 添加参数
     *
     * @param key 键值
     * @param value 内容
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }



}
