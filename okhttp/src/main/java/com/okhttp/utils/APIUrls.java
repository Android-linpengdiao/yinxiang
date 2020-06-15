package com.okhttp.utils;

public class APIUrls {


    public final static String url_domain = "http://enjoy.fengyunguoyuan.com/";
//    public final static String url_domain = "http://www.udiandou.com/";

    public final static String url_fileUpload = url_domain + "api/common/fileUpload";
    public final static String url_createSecurityToken = url_domain + "api/common/stsToken";


    //用户信息
    public final static String url_phoneCode = url_domain + "api/common/phoneCode";
    public final static String url_phoneLogin = url_domain + "api/common/phoneLogin";
    public final static String url_login = url_domain + "api/common/login";
    public final static String url_thirdLogin = url_domain + "api/common/thirdLogin";
    public final static String url_updatePasswordAndLogin = url_domain + "api/common/phoneCode";
    public final static String url_register = url_domain + "api/common/register";
    public final static String url_forgetPassword = url_domain + "api/common/forgetPassword";
    public final static String url_cancel = url_domain + "api/center/cancel";
    public final static String url_baseInfo = url_domain + "api/center/baseInfo";
    public final static String url_isFollow = url_domain + "api/center/IsFollow";
    public final static String url_resetPassword = url_domain + "api/common/resetPassword";
    public final static String url_editPersonal = url_domain + "api/center/editPersonal";
    public final static String url_report = url_domain + "api/center/report";
    public final static String url_profile = url_domain + "api/center/profile";

    //首页-活动
    public final static String url_homePageActives = url_domain + "api/homepageActives/info";

    /**
     * 首页-视频-活动环宇
     */
    //首页-视频-活动环宇
    public final static String url_homePageVideosActive = url_domain + "api/homepageVideos/active";
    //首页-视频-pk
    public final static String url_homePageVideosCreatePk = url_domain + "api/homepageVideos/createPk";
    //首页-视频-我的pk
    public final static String url_homePageVideosSelfPk = url_domain + "api/homepageVideos/selfPk";
    //首页-视频-接力
    public final static String url_homePageVideosRelay = url_domain + "api/homepageVideos/relay";
    //首页-视频-我的接力
    public final static String url_homepageVideosSelfRelay = url_domain + "api/homepageVideos/selfRelay";
    //首页-视频-举报
    public final static String url_homePageVideosReport = url_domain + "api/homepageVideos/report";
    //首页-视频-是否点赞
    public final static String url_homePageVideosIsAssist = url_domain + "api/homepageVideos/isAssist";
    //首页-视频-点赞
    public final static String url_homePageVideosAssist = url_domain + "api/homepageVideos/assist";
    //首页-视频-点赞取消
    public final static String url_homePageVideosCancelAssist = url_domain + "api/homepageVideos/cancelAssist";
    //首页-视频-搜索
    public final static String url_homePageVideosSearch = url_domain + "api/homepageVideos/search";
    //首页-视频-创建评论
    public final static String url_homePageVideosCreateComment = url_domain + "api/homepageVideos/createComment";
    //首页-视频-获取评论
    public final static String url_homePageVideosComment = url_domain + "api/homepageVideos/comment";
    //首页-视频-获取收费投票设置
    public final static String url_homePageVideosVoteSet = url_domain + "api/homepageVideos/voteSet";
    //首页-视频-投票
    public final static String url_homePageVideosVote = url_domain + "api/homepageVideos/vote";
    //首页-视频-PK投票
    public final static String url_homePageVideosPKVote = url_domain + "api/homepageVideos/pkVote";

    //首页-视频-竞技PK
    public final static String url_homePageVideosPK = url_domain + "api/homepageVideos/pk";


    //首页-视频-荣耀在线
    public final static String url_homePageVideosHonour = url_domain + "api/homepageVideos/honour";

    //首页-用户关注用户
    public final static String url_homePagePersonFollow = url_domain + "api/homepage/personFollow";
    //首页-用户取消关注用户
    public final static String url_homePagePersonUnFollow = url_domain + "api/homepage/personUnFollow";
    //首页-用户是否关注用户
    public final static String url_homePagePersonIsFollow = url_domain + "api/homepage/personIsFollow";


    /**
     * 好友
     */

    //好友-消息-系统消息
    public final static String url_friendSystem = url_domain + "api/friend/system";
    //好友-消息-点赞消息
    public final static String url_friendAssist = url_domain + "api/friend/assist";
    //好友-消息-评论消息
    public final static String url_friendComment = url_domain + "api/friend/comment";
    //好友-消息-系统消息
    public final static String url_friendClub = url_domain + "api/friend/club";
    //好友-消息-同意加群/拒绝加群
    public final static String url_friendClubAction = url_domain + "api/friend/clubAction";


    /**
     * 频道
     */

    //频道-社团-获取社团
    public final static String url_channelClub = url_domain + "api/channel/club";
    //频道-社团-创建社团
    public final static String url_channelCreateClub = url_domain + "api/channel/createClub";
    //频道-社团-加入社团
    public final static String url_channelJoinClub = url_domain + "api/channel/joinClub";
    //频道-社团-社团详情
    public final static String url_channelClubDetail = url_domain + "api/channel/clubDetail";
    //频道-社团-编辑社团
    public final static String url_channelEditClub = url_domain + "api/channel/editClub";
    //频道-社团-编辑社团简介
    public final static String url_channelEditClubDesc = url_domain + "api/channel/editClubDesc";
    //频道-社团-解散
    public final static String url_channelEissolveClub = url_domain + "api/channel/dissolveClub";
    //频道-社团-入团申请设置
    public final static String url_channelJoinClubSet = url_domain + "api/channel/joinClubSet";
    //频道-社团-主页-社团作品
    public final static String url_channelClubContent = url_domain + "api/channel/clubContent";
    //频道-社团-主页-社团成员
    public final static String url_channelClubMember = url_domain + "api/channel/clubMember";

    /**
     * 我的
     */
    //我的-我的关注
    public final static String url_personInformFollows = url_domain + "api/personInform/follows";
    //我的-我的粉丝
    public final static String url_personInformFans = url_domain + "api/personInform/fans";
    //我的-我的作品
    public final static String url_personInformWorks = url_domain + "api/personInform/works";
    //我的-我的作品详情
    public final static String worksDetail = url_domain + "api/personInform/worksDetail";
    // 我的-用户资料
    public final static String url_personInformEditBase = url_domain + "api/personInform/editBase";
    //我的-获取用户资料
    public final static String url_personInformInfo = url_domain + "api/personInform/info";

    //我的-设置-关于我们
    public final static String url_personSettingsAbout = url_domain + "api/personSettings/about";
    //我的-设置-常见问题
    public final static String url_personSettingsQuestion = url_domain + "api/personSettings/question";
    //我的-设置-我要反馈
    public final static String url_personSettingsFeedback = url_domain + "api/personSettings/feedback";
    //我的-设置-通知设置
    public final static String url_personSettingsNotice = url_domain + "api/personSettings/notice";

    //我的-获取vip相关金额信息
    public final static String url_personVipSet = url_domain + "api/personVip/set";
    //我的-我的钱包-充值设置
    public final static String url_personWalletSet = url_domain + "api/personWallet/set";
    //我的-我的钱包-消费记录
    public final static String url_personWalletRecord = url_domain + "api/personWallet/record";

    /**
     * 充值
     */
    //充值
    public final static String url_cashPay = url_domain + "api/cash/pay";
    //充值-加入付费社团
    public final static String url_cashJoinClub = url_domain + "api/cash/joinClub";
    //充值-用户作品推广
    public final static String url_cashSpread = url_domain + "api/cash/spread";


    public final static String url_commonStartUp = url_domain + "api/common/startUp";
    public final static String url_commonNav = url_domain + "api/common/nav";
    public final static String url_commonBanner = url_domain + "api/common/banner";

    //用户搜索作品
    public final static String url_searchWork = url_domain + "api/center/searchWork";
    //获取作品详情
    public final static String url_workDetail = url_domain + "api/center/workDetail";
    //获取我关注的人
    public final static String url_centerConcern = url_domain + "api/center/concern";
    //查看谁评论了我
    public final static String url_centerDiscuss = url_domain + "api/center/discuss";
    // 查看谁赞了我我的作品
    public final static String url_centerFabulous = url_domain + "api/center/fabulous";
    // 获取关注我的人
    public final static String url_centerAttention = url_domain + "api/center/attention";
    //取消关注用户接口
    public final static String url_centerUnFollow = url_domain + "api/center/unFollow";
    //关注用户接口
    public final static String url_centerFollow = url_domain + "api/center/follow";

    //查看作品是否被点赞
    public final static String url_contentIsAssist = url_domain + "api/center/contentIsAssist";
    //对作品取消点赞
    public final static String url_publishCommentDeleteAssist = url_domain + "api/center/publishCommentDeleteAssist";
    //对作品点赞
    public final static String url_publishCommentAssist = url_domain + "api/center/publishCommentAssist";
    //展示作品的评论
    public final static String url_showContentComment = url_domain + "api/center/showContentComment";
    //发布对作品的评论
    public final static String url_publishComment = url_domain + "api/center/publishComment";
    //获取用户作品
    public final static String url_centerSelfWork = url_domain + "api/center/selfWork";
    //获取用户最爱的作品
    public final static String url_favouriteContent = url_domain + "api/center/favouriteContent";
    //用户发表作品
    public final static String url_publishWork = url_domain + "api/publish/video";

    //获取系统消息
    public final static String url_commonMessage = url_domain + "api/common/message";

    //关于我们
    public final static String url_commonAbout = url_domain + "api/common/about";
    //意见反馈
    public final static String url_centerComment = url_domain + "api/center/comment";


}
