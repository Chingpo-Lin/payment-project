package com.example.paymentdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * wechat config
 */

@Configuration
@PropertySource(value = "classpath:application.properties")
public class WeChatConfig {

    @Value("${wxpay.appid}")
    private String appId;

    @Value("${wxpay.appsecret}")
    private String appsecret;

    /**
     * open platform appid
     */
    @Value("${wxopen.appid}")
    private String openAppid;

    /**
     * open platform app secret
     */
    @Value("${wxopen.appsecret}")
    private String openAppsecret;

    /**
     * open platform redirect url
     */
    @Value("${wxopen.redirect_url}")
    private String openRedirectUrl;

    /**
     * er wei wa lian jie
     */
    private final static String OPEN_QRCODE_URL="https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";

    /**
     * get access token
     */
    private final static String OPEN_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    /**
     * get user info
     */
    private final static String OPEN_USER_INFO_URL ="https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * unified order url
     */
    private static final String UNIFIED_ORDER_URL = "https://api.xdclass.net/pay/unifiedorder";

    public static String getUnifiedOrderUrl() {
        return UNIFIED_ORDER_URL;
    }

    /**
     * merchant account id
     */
    @Value("${wxpay.mer_id}")
    private String mchId;

    /**
     * payment key
     */
    @Value("${wxpay.key}")
    private String key;

    /**
     * wechat payment redirect url
     */
    @Value("${wxpay.callback}")
    private String payCallbackUrl;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPayCallbackUrl() {
        return payCallbackUrl;
    }

    public void setPayCallbackUrl(String payCallbackUrl) {
        this.payCallbackUrl = payCallbackUrl;
    }

    public static String getOpenAccessTokenUrl() {
        return OPEN_ACCESS_TOKEN_URL;
    }

    public static String getOpenUserInfoUrl() {
        return OPEN_USER_INFO_URL;
    }

    public static String getOpenQrcodeUrl() {
        return OPEN_QRCODE_URL;
    }

    public String getOpenAppid() {
        return openAppid;
    }

    public void setOpenAppid(String openAppid) {
        this.openAppid = openAppid;
    }

    public String getOpenAppsecret() {
        return openAppsecret;
    }

    public void setOpenAppsecret(String openAppsecret) {
        this.openAppsecret = openAppsecret;
    }

    public String getOpenRedirectUrl() {
        return openRedirectUrl;
    }

    public void setOpenRedirectUrl(String openRedirectUrl) {
        this.openRedirectUrl = openRedirectUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
}
