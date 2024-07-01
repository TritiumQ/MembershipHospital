package me.qunqun.doctor.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.qunqun.doctor.entity.vo.ChatRequestVO;
import me.qunqun.doctor.entity.reps.AiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class AiService {
    @Value("${anything-llm.url:http://localhost:8180}")
    private String baseUrl;

    @Value("${anything-llm.workspace:hospital}")
    private String workSpace;

    @Value("${anything-llm.mode:chat}")
    private String mode;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private static final String reportAdvisePrompt = "请你从一位专业医生的角度并结合搜索到的医学知识对这份体检报告进行分析，帮助医生指出其中存在的问题，并帮助医生为体检客户提供相应的建议，所有输出内容限制在150字中文以内。按以下风格中文输出为\"分析:xxx。\n建议:xxx\",分析和建议需要换行，注意不要输出除此之外的任何内容。此外，“检验值“为“null”的检查项表示尚未填写可忽略";

    public AiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public AiResponse sendChatRequest(String message) throws Exception {
        String url = baseUrl + "/workspace/chat";
        String query = message + reportAdvisePrompt;
        ChatRequestVO chatRequest = new ChatRequestVO();
        chatRequest.setMessage(query);
        chatRequest.setWorkspace(workSpace);
        chatRequest.setMode(mode);

        String requestBody = objectMapper.writeValueAsString(chatRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), AiResponse.class);
    }

}
