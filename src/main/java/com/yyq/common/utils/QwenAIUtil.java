package com.yyq.common.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class QwenAIUtil {
    private static final String API_KEY = "sk-6965b2a32889477391244a6910afcac4";  // 你的通义千问 API Key
    private static final String API_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";
    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * 调用通义千问 API 进行文章解读
     *
     * @param
     * @return AI 解读结果
     */
    /*public static String analyzeArticle(String text) {
        try {
            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "qwen-turbo");
            requestBody.put("input", Map.of("prompt", "请你回答：" + text));

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + API_KEY);
            headers.set("Content-Type", "application/json");

            // 发送 HTTP 请求
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            // 解析 JSON 响应
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            // 打印完整的 API 响应（调试用）
            System.out.println("完整 JSON 响应: " + jsonNode.toPrettyString());
            // 获取 AI 生成的回答
            JsonNode outputNode = jsonNode.path("output").path("text");
            return outputNode.isMissingNode() ? "API 响应格式错误" : outputNode.asText();

        } catch (Exception e) {
            e.printStackTrace();
            return "调用 AI 解读失败：" + e.getMessage();
        }
    }*/
    public static String askAI(String content, String question) {
        try {
            if (content == null || content.trim().isEmpty()) {
                return "文章内容不能为空";
            }
            if (question == null || question.trim().isEmpty()) {
                question = "请总结这篇文章的主要内容"; // 默认提供解读
            }

            // 组织请求数据
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "qwen-turbo");
            requestBody.put("input", Map.of("prompt", "请你根据文章内容：" + content + "回答问题：" + question));

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + API_KEY);


            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);


            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.path("output").path("text").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "AI 解析失败，请稍后重试";
        }
    }
}
