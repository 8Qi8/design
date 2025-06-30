package com.yyq;

import com.yyq.common.utils.QwenAIUtil;
import com.yyq.common.utils.SmsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GraduationDesignApplicationTests {

	@Test
	void textMessage() {
        try {
            SmsUtil.sendMessage("阿里云短信测试", "SMS_154950909",
					"18715989890", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void testAI() {
        String result = QwenAIUtil.askAI("对候选解的种群进行操作，主要包含以下几个主要操作：\n" +
                "\n" +
                "\n" +
                "种群初始化\n" +
                "\n" +
                "\n" +
                "变异（突变）：通过把种群中两个成员之间的加权差向量加到第三个成员上来产生新的参数向量\n" +
                "\n" +
                "\n" +
                "交叉：将变异向量的参数与另外预先确定的目标向量参数按照一定的规则混合来产生试验向量。\n" +
                "\n" +
                "\n" +
                "选择 ：如果试验向量的目标函数比目标向量的代价函数低，那么就利用试验向量替换掉目标向量。\n", "总结一下");
        System.out.println("AI 解读结果: " + result);
    }

}
