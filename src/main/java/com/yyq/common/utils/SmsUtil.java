package com.yyq.common.utils;

import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.google.gson.Gson;

import java.util.Random;

public class SmsUtil {

    public static final String ALIBABA_CLOUD_ACCESS_KEY_ID = "LTAI5tAuprHN8sMD78sPggMn";
    public static final String ALIBABA_CLOUD_ACCESS_KEY_SECRET = "0GXLrfCHDne9Matykjvj3WlsDzdxSh";
    private static final String CHARACTERS_NUMBER = "0123456789";

    /**
     * 使用AK&SK初始化Client
     * 使用客户端去发送短信
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws Exception
     */
    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名 固定写法
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    /**
     * 向指定手机号发送短信
     *
     * @param signName     签名
     * @param templateCode 模板
     * @param phoneNumbers 手机号
     * @param param        参数 ---验证码
     */
    public static void sendMessage(String signName, String templateCode, String phoneNumbers, String param) throws Exception {
        // 获取客户端对象
        Client client = createClient(ALIBABA_CLOUD_ACCESS_KEY_ID, ALIBABA_CLOUD_ACCESS_KEY_SECRET);
        // 获取发送短信对象 设置发送短信的 签名，模板，手机号，验证码
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setPhoneNumbers(phoneNumbers);
        request.setTemplateParam("{\"code\":\"" + param + "\"}");
        // 运行时行为  例如最大尝试次数，超时时间等等
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 根据信息发送短信
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(request, runtime);
            System.out.println(new Gson().toJson(sendSmsResponse.body));
        } catch (TeaException error) {
            // 如有需要，请打印 error
            System.out.println(error);
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }


    //生成6位手机验证码
    public static String generatePhoneVerificationCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CHARACTERS_NUMBER.length());
            sb.append(CHARACTERS_NUMBER.charAt(index));
        }
        return sb.toString();
    }
}

