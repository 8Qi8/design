package com.yyq;

import com.yyq.common.utils.SmsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GraduationDesignApplicationTests {

	@Test
	void contextLoads() {
        try {
            SmsUtil.sendMessage("阿里云短信测试", "SMS_154950909",
					"18715989890", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
