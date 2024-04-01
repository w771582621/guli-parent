package com.wl.msm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyGet {
    @Value("${KEY_ID}")
    public String KEY_ID;
    @Value("${KEY_SECRET}")
    public String KET_SECRET;
}
