package com.example.paymentdemo.controller;

import com.example.paymentdemo.config.WeChatConfig;
import com.example.paymentdemo.domain.User;
import com.example.paymentdemo.domain.VideoOrder;
import com.example.paymentdemo.service.UserService;
import com.example.paymentdemo.service.VideoOrderService;
import com.example.paymentdemo.utils.JsonData;
import com.example.paymentdemo.utils.JwtUtils;
import com.example.paymentdemo.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

@Controller
@RequestMapping("/api/v1/wechat")
public class WechatController {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoOrderService videoOrderService;

    @GetMapping("login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page") String accessPage) throws UnsupportedEncodingException {

        String redirectUrl = weChatConfig.getOpenRedirectUrl();

        String callbackUrl = URLEncoder.encode(redirectUrl, "GBK");

        String qrcodeUrl = String.format(weChatConfig.getOpenQrcodeUrl(), weChatConfig.getOpenAppid(), callbackUrl, accessPage);

        return JsonData.buildSuccess(qrcodeUrl);
    }

    /**
     * callback to here when scan successfully
     * @param code
     * @param state
     * @param response
     */
    @GetMapping("/user/callback")
    public void wechatUserCallback(@RequestParam(value = "code") String code,
                                   String state, HttpServletResponse response) throws IOException {

        User user = userService.saveWeChatUser(code);
        if (user != null) {
            // generate jwt
            String token = JwtUtils.geneJsonWebToken(user);
            // need to add http:// to current user link in loginUrl api
            response.sendRedirect(state + "?token=" + token + "&head_img=" +
                    user.getHeadImg() + "&name=" + URLEncoder.encode(user.getName(), "UTF-8"));
        }
    }

    /**
     * wechat payment redirect
     * use post so cannot use get mapping
     */
    @RequestMapping("/order/callback")
    public void orderCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream inputStream = request.getInputStream();

        // wrap design model, better than input stream reader
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();
        inputStream.close();

        Map<String, String> callbackMap = WXPayUtil.xmlToMap(sb.toString());
        System.out.println(callbackMap);

        SortedMap<String, String> sortedMap = WXPayUtil.getSortedMap(callbackMap);

        // if sign is correct
        if (WXPayUtil.isCorrectSign(sortedMap, weChatConfig.getKey())) {
            if ("SUCCESS".equals(sortedMap.get("result_code"))) {
                String outTradeNo = sortedMap.get("out_trade_no");
                VideoOrder dbVideoOrder = videoOrderService.findByOutTradeNo(outTradeNo);

                if (dbVideoOrder.getState() == 0) { // judge logic
                    VideoOrder videoOrder = new VideoOrder();
                    videoOrder.setOpenid(sortedMap.get("openid"));
                    videoOrder.setOutTradeNo(outTradeNo);
                    videoOrder.setNotifyTime(new Date());
                    videoOrder.setState(1);
                    int rows = videoOrderService.updateVideoOderByOutTradeNo(videoOrder);

                    if (rows == 1) { // add success, notify wechat
                        response.setContentType("text/xml");
                        response.getWriter().write("success");
                        return;
                    }
                }
            }
        }

        // fail
        response.setContentType("text/xml");
        response.getWriter().write("fail");

    }
}
