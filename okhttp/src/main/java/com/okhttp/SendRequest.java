package com.okhttp;

import com.baselibrary.utils.CommonUtil;
import com.okhttp.callbacks.Callback;
import com.okhttp.utils.APIUrls;
import com.okhttp.utils.OkHttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class SendRequest {
    private static String TAG = "SendRequest";

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param password_confirmation
     * @param authCode
     * @param reg                   极光推送的regId
     * @param openid
     * @param qq_id
     * @param weibo_id
     * @param call
     */
    public static void register(String phone, String password, String password_confirmation, String authCode, String reg, String openid, String qq_id, String weibo_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        map.put("password_confirmation", password_confirmation);
        map.put("authCode", authCode);
        map.put("reg", reg);
        map.put("openid", openid);
        map.put("qq_id", qq_id);
        map.put("weibo_id", weibo_id);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_register).build().execute(call);

    }

    /**
     * 手机注册
     *
     * @param phone
     * @param password
     * @param password_confirmation
     * @param authCode
     * @param call
     */
    public static void register(String phone, String password, String password_confirmation, String authCode, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        map.put("password_confirmation", password_confirmation);
        map.put("authCode", authCode);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_register).build().execute(call);

    }

    /**
     * 密码登录
     *
     * @param phone
     * @param password
     * @param call
     */
    public static void login(String phone, String password, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_login).build().execute(call);

    }

    /**
     * 用户第三方登录（微信）
     *
     * @param openid
     * @param nickname
     * @param headimgurl
     * @param sex
     * @param city
     * @param province
     * @param call
     */
    public static void WXLogin(String openid, String nickname, String headimgurl, String sex, String city, String province, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("openid", openid);
        map.put("nickname", nickname);
        map.put("headimgurl", headimgurl);
        map.put("sex", sex);
        map.put("city", city);
        map.put("province", province);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_thirdLogin).build().execute(call);

    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @param key   register.code=>注册/forget.password=>忘记密码/phone.login=>手机号验证码登录
     * @param call
     */
    public static void phoneCode(String phone, String key, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("key", key);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_phoneCode).build().execute(call);

    }

    /**
     * 验证码登录
     *
     * @param phone
     * @param authCode
     * @param call
     */
    public static void phoneLogin(String phone, String authCode, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("authCode", authCode);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_phoneLogin).build().execute(call);

    }

    /**
     * 修改密码
     *
     * @param phone
     * @param code
     * @param password
     * @param againPassword
     * @param call
     */
    public static void updatePasswordAndLogin(String phone, String code, String password, String againPassword, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        map.put("password", password);
        map.put("againPassword", againPassword);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_updatePasswordAndLogin).build().execute(call);

    }

    /**
     * 用户忘记密码
     *
     * @param phone
     * @param authCode
     * @param password
     * @param password_confirmation
     * @param call
     */
    public static void forgetPassword(String phone, String authCode, String password, String password_confirmation, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("authCode", authCode);
        map.put("password", password);
        map.put("password_confirmation", password_confirmation);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_forgetPassword).build().execute(call);

    }

    /**
     * 注销用户
     *
     * @param tourist_id 类型下的唯一标识
     * @param call
     */
    public static void cancel(String tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", tourist_id);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_cancel).build().execute(call);

    }

    /**
     * 读取本地身份证信息
     *
     * @param tourist_id 类型下的唯一标识
     * @param call
     */
    public static void baseInfo(int tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_baseInfo).build().execute(call);

    }

    public static void isFollow(int tourist_id, int follow_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("follow_id", String.valueOf(follow_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_isFollow).build().execute(call);

    }

    /**
     * 首页-活动
     *
     * @param type 类型 （1->全部 2->未结束的）
     * @param call
     */
    public static void homePageActives(int type, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("type", String.valueOf(type));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_homePageActives).build().execute(call);

    }

    /**
     * =======================================首页================================================
     */
    /**
     * 首页-视频-活动环宇
     *
     * @param active_id 活动id
     * @param perPage
     * @param call
     */
    public static void homePageVideosActive(int active_id, int perPage, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("active_id", String.valueOf(active_id));
        map.put("perPage", String.valueOf(perPage));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosActive).build().execute(call);

    }

    /**
     * 首页-视频-pk
     *
     * @param tourist_id
     * @param video_id
     * @param pkvideo_id
     * @param call
     */
    public static void homePageVideosCreatePk(int tourist_id, int video_id, int pkvideo_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("video_id", String.valueOf(video_id));
        map.put("pkvideo_id", String.valueOf(pkvideo_id));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosCreatePk).build().execute(call);

    }

    /**
     * 首页-视频-接力
     *
     * @param touristVideoId 用户视频id
     * @param relaytVideoId  接力视频id
     * @param call
     */
    public static void homePageVideosCreatePk(int touristVideoId, int relaytVideoId, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("touristVideoId", String.valueOf(touristVideoId));
        map.put("relaytVideoId", String.valueOf(relaytVideoId));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosRelay).build().execute(call);

    }

    /**
     * 首页-视频-举报
     *
     * @param tourist_id
     * @param video_id
     * @param type       类型 1涉黄 2涉爆 3涉及反动言论 4其他
     * @param body
     * @param call
     */
    public static void homePageVideosReport(int tourist_id, int video_id, int type, int body, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("video_id", String.valueOf(video_id));
        map.put("type", String.valueOf(type));
        map.put("body", String.valueOf(body));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosReport).build().execute(call);

    }

    /**
     * 首页-视频-是否点赞
     *
     * @param tourist_id
     * @param video_id
     * @param url
     * @param call
     */
    public static void homePageVideosIsAssist(int tourist_id, int video_id, String url, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("video_id", String.valueOf(video_id));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosIsAssist).build().execute(call);

    }

    /**
     * 首页-视频-点赞
     *
     * @param tourist_id
     * @param video_id
     * @param url
     * @param call
     */
    public static void homePageVideosAssist(int tourist_id, int video_id, String url, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("video_id", String.valueOf(video_id));
        OkHttpUtils.getInstance().post().params(map).url(url).build().execute(call);

    }

    /**
     * 首页-视频-搜索
     *
     * @param active_id
     * @param keyword
     * @param perPage
     * @param call
     */
    public static void homePageVideosSearch(int active_id, String keyword, String perPage, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("active_id", String.valueOf(active_id));
        map.put("keyword", keyword);
        map.put("perPage", String.valueOf(perPage));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosSearch).build().execute(call);

    }

    /**
     * 首页-视频-创建评论
     *
     * @param tourist_id
     * @param video_id
     * @param body
     * @param call
     */
    public static void homePageVideosCreateComment(int tourist_id, String video_id, String body, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("body", body);
        map.put("video_id", String.valueOf(video_id));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosCreateComment).build().execute(call);

    }

    /**
     * 首页-视频-获取评论
     *
     * @param perPage
     * @param video_id
     * @param call
     */
    public static void homePageVideosComment(int perPage, String video_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("perPage", String.valueOf(perPage));
        map.put("video_id", String.valueOf(video_id));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosComment).build().execute(call);

    }

    /**
     * 首页-视频-获取收费投票设置
     *
     * @param call
     */
    public static void homePageVideosVoteSet(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosVoteSet).build().execute(call);

    }

    /**
     * 首页-视频-投票
     *
     * @param tourist_id
     * @param video_id
     * @param free       是否免费 1是 2收费
     * @param call
     */
    public static void homePageVideosVote(int tourist_id, int video_id, int free, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("video_id", String.valueOf(video_id));
        map.put("free", String.valueOf(free));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosVote).build().execute(call);

    }


    /**
     * 首页-视频-竞技PK
     *
     * @param tourist_id
     * @param active_id
     * @param perPage
     * @param call
     */
    public static void homePageVideosPK(int tourist_id, int active_id, int perPage, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("active_id", String.valueOf(active_id));
        map.put("perPage", String.valueOf(perPage));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosPK).build().execute(call);

    }


    /**
     * 首页-视频-荣耀在线
     *
     * @param tourist_id
     * @param active_id
     * @param perPage
     * @param call
     */
    public static void homePageVideosHonour(int tourist_id, int active_id, int perPage, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("active_id", String.valueOf(active_id));
        map.put("perPage", String.valueOf(perPage));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePageVideosHonour).build().execute(call);

    }

    /**
     * 首页-用户关注用户
     *
     * @param tourist_id
     * @param follow_id  要关注的用户id
     * @param call
     */
    public static void homePagePersonFollow(int tourist_id, int follow_id, String url, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("follow_id", String.valueOf(follow_id));
        OkHttpUtils.getInstance().post().params(map).url(url).build().execute(call);

    }

    /**
     * 首页-用户是否关注用户
     *
     * @param tourist_id
     * @param follow_id
     * @param call
     */
    public static void homePagePersonIsFollow(int tourist_id, int follow_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("follow_id", String.valueOf(follow_id));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_homePagePersonIsFollow).build().execute(call);

    }


    /**
     * ======================================我的===================================
     */

    /**
     * 好友-消息-系统消息
     *
     * @param perPage
     * @param call
     */
    public static void friendSystem(int perPage, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("perPage", String.valueOf(perPage));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_friendSystem).build().execute(call);

    }

    /**
     * 好友-消息-点赞消息
     *
     * @param tourist_id
     * @param perPage
     * @param call
     */
    public static void friendAssist(int tourist_id, int perPage, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("perPage", String.valueOf(perPage));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_friendAssist).build().execute(call);

    }

    /**
     * 好友-消息-评论消息
     *
     * @param tourist_id
     * @param perPage
     * @param call
     */
    public static void friendComment(int tourist_id, int perPage, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("perPage", String.valueOf(perPage));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_friendComment).build().execute(call);

    }

    /**
     * 好友-消息-群消息
     *
     * @param tourist_id
     * @param perPage
     * @param call
     */
    public static void friendClub(int tourist_id, int perPage, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("perPage", String.valueOf(perPage));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_friendClub).build().execute(call);

    }

    /**
     * 好友-消息-同意加群/拒绝加群
     *
     * @param tourist_id
     * @param status     状态 1同意/2拒绝
     * @param call
     */
    public static void friendClubAction(int tourist_id, int status, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("status", String.valueOf(status));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_friendClubAction).build().execute(call);

    }

    /**
     * ======================================频道===================================
     */

    /**
     * 频道-社团-获取社团
     *
     * @param tourist_id
     * @param status     状态 1审核通过 2审核未通过 3正在审核中
     * @param call
     */
    public static void channelClub(int tourist_id, int status, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("status", String.valueOf(status));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_channelClub).build().execute(call);

    }

    public static void channelClubTouristId(int tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_channelClub).build().execute(call);

    }

    public static void channelClubStatus(int status, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("status", String.valueOf(status));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_channelClub).build().execute(call);

    }


    /**
     * 频道-社团-创建社团
     *
     * @param tourist_id
     * @param logo         社团logo
     * @param license      营业执照
     * @param idcard_front 身份证正面
     * @param idcard_back  身份证反面
     * @param phone        手机号码
     * @param authCode     验证码
     * @param desc         社团简介
     * @param call
     */
    public static void channelCreateClub(int tourist_id, String name,String logo, String license, String idcard_front, String idcard_back, String phone, String authCode, String desc, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("name", name);
        map.put("logo", logo);
        map.put("license", license);
        map.put("idcard_front", idcard_front);
        map.put("idcard_back", idcard_back);
        map.put("phone", phone);
        map.put("authCode", authCode);
        map.put("desc", desc);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_channelCreateClub).build().execute(call);

    }

    /**
     * 频道-社团-编辑社团
     *
     * @param tourist_id
     * @param logo         社团logo
     * @param license      营业执照
     * @param idcard_front 身份证正面
     * @param idcard_back  身份证反面
     * @param phone        手机号码
     * @param authCode     验证码
     * @param desc         社团简介
     * @param call
     */
    public static void channelEditClub(int tourist_id, String logo, String license, String idcard_front, String idcard_back, String phone, String authCode, String desc, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("logo", logo);
        map.put("license", license);
        map.put("idcard_front", idcard_front);
        map.put("idcard_back", idcard_back);
        map.put("phone", phone);
        map.put("authCode", authCode);
        map.put("desc", desc);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_channelEditClub).build().execute(call);

    }

    /**
     * 频道-社团-解散
     *
     * @param tourist_id
     * @param club_id
     * @param dissolve      解散理由
     * @param dissolve_time
     * @param call
     */
    public static void channelEissolveClub(int tourist_id, int club_id, String dissolve, String dissolve_time, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("club_id", String.valueOf(club_id));
        map.put("dissolve", dissolve);
        map.put("dissolve_time", dissolve_time);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_channelEissolveClub).build().execute(call);

    }

    /**
     * 频道-社团-入团申请设置
     *
     * @param tourist_id
     * @param club_id
     * @param join       1收费 2免费
     * @param join_token 金币数 免费传0
     * @param call
     */
    public static void channelJoinClubSet(int tourist_id, int club_id, String join, String join_token, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("club_id", String.valueOf(club_id));
        map.put("join", join);
        map.put("join_token", join_token);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_channelJoinClubSet).build().execute(call);

    }


    /**
     * ======================================我的===================================
     */


    /**
     * 我的-我的关注
     *
     * @param tourist_id
     * @param perPage
     * @param call
     */
    public static void personInformFollows(int tourist_id, int perPage, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("perPage", String.valueOf(perPage));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_personInformFollows).build().execute(call);

    }

    /**
     * 我的-我的粉丝
     *
     * @param tourist_id
     * @param perPage
     * @param call
     */
    public static void personInformFans(int tourist_id, int perPage, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("perPage", String.valueOf(perPage));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_personInformFans).build().execute(call);

    }

    /**
     * 我的-用户资料
     *
     * @param tourist_id
     * @param key        avatar=>头像 name=>用户昵称 sex=>用户性别（1男 2女） desc=>简介 addr=>地区
     * @param value
     * @param call
     */
    public static void personInformEditBase(int tourist_id, String key, String value, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put(key, value);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_personInformEditBase).build().execute(call);

    }

    /**
     * 我的-获取用户资料
     *
     * @param tourist_id
     * @param call
     */
    public static void personInformInfo(int tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_personInformInfo).build().execute(call);

    }

    /***
     * 我的-设置-关于我们
     * @param call
     */
    public static void personSettingsAbout(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_personSettingsAbout).build().execute(call);

    }

    /**
     * 我的-设置-常见问题
     *
     * @param call
     */
    public static void personSettingsQuestion(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_personSettingsQuestion).build().execute(call);

    }

    /**
     * 我的-设置-我要反馈
     *
     * @param call
     */
    public static void personSettingsFeedback(int tourist_id, String content, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("content", content);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_personSettingsFeedback).build().execute(call);

    }

    /**
     * 我的-设置-通知设置
     *
     * @param tourist_id
     * @param key        like_notice=>点赞通知 comment_notice=>评论通知 （1是 2否）
     * @param value
     * @param call
     */
    public static void personSettingsNotice(int tourist_id, String key, int value, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put(key, String.valueOf(value));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_personSettingsNotice).build().execute(call);

    }

    /**
     * 我的-获取vip相关金额信息
     *
     * @param call
     */
    public static void personVipSet(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_personVipSet).build().execute(call);

    }

    /**
     * 我的-我的钱包-充值设置
     *
     * @param call
     */
    public static void personWalletSet(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_personWalletSet).build().execute(call);

    }

    /**
     * 我的-我的钱包-消费记录
     *
     * @param tourist_id
     * @param perPage
     * @param call
     */
    public static void personWalletRecord(int tourist_id, int perPage, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("perPage", String.valueOf(perPage));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_personWalletRecord).build().execute(call);

    }


    /**
     * ===================================发布===========================================================
     */

    /**
     * 发布视频
     *
     * @param tourist_id
     * @param coverUrl   视频封面
     * @param videoUrl   视频连接
     * @param active_id  活动id 默认值0
     * @param club_id    社团id 默认值0
     */

    public static void publishWork(int tourist_id, String coverUrl, String videoUrl, int active_id, int club_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("img", coverUrl);
        map.put("video", videoUrl);
        map.put("active_id", String.valueOf(active_id));
        map.put("club_id", String.valueOf(club_id));
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_publishWork).build().execute(call);
    }

    /**
     * 重置密码
     *
     * @param tourist_id
     * @param old_password
     * @param password
     * @param call
     */
    public static void resetPassword(String tourist_id, String old_password, String password, String confirm_password, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", tourist_id);
        map.put("old_password", old_password);
        map.put("password", password);
        map.put("confirm_password", password);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_resetPassword).build().execute(call);

    }

    public static void commonStartUp(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_commonStartUp).build().execute(call);

    }

    /**
     * 获取轮播图
     *
     * @param call
     */
    public static void commonBanner(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_commonBanner).build().execute(call);

    }

    /**
     * 获取首页导航分类
     *
     * @param call
     */
    public static void commonNav(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_commonNav).build().execute(call);

    }

    /**
     * 用户搜索作品
     *
     * @param tourist_id 搜索人id(非必填)
     * @param type       1 最热 ；2 推荐
     * @param nav_id     分类id(非必填)
     * @param word       关键词搜索(非必填)
     * @param per_page   每页条数(非必填 默认10)
     * @param page       页数(非必填 默认1)
     * @param call
     */
    public static void searchWork(int tourist_id, int type, int nav_id, String word, int per_page, int page, Callback call) {
        Map<String, String> map = new HashMap<>();
        if (!CommonUtil.isBlank(String.valueOf(type))) {
            map.put("tourist_id", String.valueOf(tourist_id));
        }
        if (!CommonUtil.isBlank(String.valueOf(type))) {
            map.put("type", String.valueOf(type));
        }
        if (!CommonUtil.isBlank(String.valueOf(type))) {
            map.put("nav_id", String.valueOf(nav_id));
        }
        if (!CommonUtil.isBlank(word)) {
            map.put("word", word);
        }
        map.put("per_page", String.valueOf(per_page));
        map.put("page", String.valueOf(page));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_searchWork).build().execute(call);

    }

    public static void searchWorkType(int nav_id, int per_page, int page, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("nav_id", String.valueOf(nav_id));
        map.put("per_page", String.valueOf(per_page));
        map.put("page", String.valueOf(page));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_searchWork).build().execute(call);

    }

    public static void searchWorkWord(String word, int per_page, int page, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("word", word);
        map.put("per_page", String.valueOf(per_page));
        map.put("page", String.valueOf(page));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_searchWork).build().execute(call);
    }

    public static void contentIsAssist(int tourist_id, int content_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("content_id", String.valueOf(content_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_contentIsAssist).build().execute(call);
    }

    public static void publishContentAssist(int tourist_id, int content_id, String assistUrl, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("content_id", String.valueOf(content_id));
        OkHttpUtils.getInstance().post().params(map).url(assistUrl).build().execute(call);
    }

    public static void showContentComment(int content_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("content_id", String.valueOf(content_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_showContentComment).build().execute(call);
    }

    public static void publishComment(int tourist_id, int content_id, String body, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("content_id", String.valueOf(content_id));
        map.put("body", body);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_publishComment).build().execute(call);
    }

    /**
     * 获取作品详情
     *
     * @param content_id 作品ID
     * @param call
     */
    public static void workDetail(int content_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("content_id", String.valueOf(content_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_workDetail).build().execute(call);

    }

    /**
     * 获取我关注的人
     *
     * @param tourist_id 用户id（必填）.
     * @param per_page   每页条数（非必填 默认10）.
     * @param page       页数（非必填 默认1）.
     * @param call
     */
    public static void centerConcern(int tourist_id, int per_page, int page, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("per_page", String.valueOf(per_page));
        map.put("page", String.valueOf(page));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_centerConcern).build().execute(call);

    }

    /**
     * 获取关注我的人
     *
     * @param tourist_id 用户id（必填）.
     * @param per_page   每页条数（非必填 默认10）.
     * @param page       页数（非必填 默认1）.
     * @param call
     */
    public static void centerAttention(int tourist_id, int per_page, int page, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("per_page", String.valueOf(per_page));
        map.put("page", String.valueOf(page));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_centerAttention).build().execute(call);

    }

    public static void centerDiscuss(int tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_centerDiscuss).build().execute(call);

    }

    public static void centerFabulous(int tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_centerFabulous).build().execute(call);

    }

    public static void commonMessage(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_commonMessage).build().execute(call);

    }

    public static void centerSelfWork(int tourist_id, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_centerSelfWork).build().execute(call);
    }

    public static void url_favouriteContent(int tourist_id, int per_page, int page, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("per_page", String.valueOf(per_page));
        map.put("page", String.valueOf(page));
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_favouriteContent).build().execute(call);
    }

    public static void commonAbout(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_commonAbout).build().execute(call);
    }

    public static void centerComment(int tourist_id, String comment, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("comment", comment);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_centerComment).build().execute(call);
    }

    public static void centerFollow(int tourist_id, int follow_id, String followUrl, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("follow_id", String.valueOf(follow_id));
        OkHttpUtils.getInstance().post().params(map).url(followUrl).build().execute(call);
    }


    /**
     * 修改个人信息
     *
     * @param avatar
     * @param name
     * @param birth
     * @param sex
     * @param autograph
     * @param weibo
     * @param call
     */
    public static void editPersonal(String avatar, String name, String birth, String sex, String autograph, String weibo, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("avatar", avatar);
        map.put("name", name);
        map.put("birth", birth);
        map.put("sex", sex);
        map.put("autograph", autograph);
        map.put("weibo", weibo);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_editPersonal).build().execute(call);

    }

    public static void editPersonal(int tourist_id, int base_id, String avatar, String name, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", String.valueOf(tourist_id));
        map.put("base_id", String.valueOf(base_id));
        map.put("avatar", avatar);
        map.put("name", name);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_editPersonal).build().execute(call);

    }

    /**
     * 举报
     *
     * @param tourist_id
     * @param report_id
     * @param report
     * @param call
     */
    public static void report(String tourist_id, String report_id, String report, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", tourist_id);
        map.put("report_id", report_id);
        map.put("report", report);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_report).build().execute(call);

    }


    /**
     * 举报
     *
     * @param tourist_id
     * @param face_photo //身份证正面照片
     * @param back_photo //身份证反面照片
     * @param call
     */
    public static void profile(String tourist_id, String face_photo, String back_photo, Callback call) {
        Map<String, String> map = new HashMap<>();
        map.put("tourist_id", tourist_id);
        map.put("face_photo", face_photo);
        map.put("back_photo", back_photo);
        OkHttpUtils.getInstance().post().params(map).url(APIUrls.url_profile).build().execute(call);

    }

    /**
     * 上传文件
     *
     * @param file
     * @param call
     */
    public static void fileUpload(String file, String name, Callback call) {
        OkHttpUtils.getInstance().post().addFile("file", name, new File(file)).url(APIUrls.url_fileUpload).build().execute(call);

    }

    public static void createSecurityToken(Callback call) {
        Map<String, String> map = new HashMap<>();
        OkHttpUtils.getInstance().get().params(map).url(APIUrls.url_createSecurityToken).build().execute(call);

    }
}
