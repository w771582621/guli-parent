package com.wl.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wl.msm.config.PropertyGet;
import com.wl.msm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private PropertyGet propertyGet;
    @Override
    public boolean send(String phone,Map<String,Object> param) {
        if (StringUtils.isEmpty(phone))
            return false;
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID。
                .setAccessKeyId(propertyGet.KEY_ID)
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                .setAccessKeySecret(propertyGet.KET_SECRET);
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        try {
            com.aliyun.dysmsapi20170525.Client client = new com.aliyun.dysmsapi20170525.Client(config);
            com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                    .setSignName("阿里云短信测试")
                    .setTemplateCode("SMS_154950909")
                    .setPhoneNumbers(phone)
                    .setTemplateParam(JSONObject.toJSONString(param));
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
            // 复制代码运行请自行打印 API 的返回值
            client.sendSmsWithOptions(sendSmsRequest, runtime);
            return true;
        } catch (Exception error) {
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            error.printStackTrace();
            return false;
        }
    }
}
