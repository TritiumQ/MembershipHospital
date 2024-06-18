package me.qunqun.doctor.service;

import me.qunqun.doctor.entity.reps.ModelResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ModelApiService {

    @Value("${model.api.url}")
    private static final String API_URL = "http://localhost:11434/api/generate";
    private final HttpClient httpClient;
    @Value("${model.api.model}")
    private static final String model = "qwen2:7b";
    private static final String reportAdvisePrompt = "请你从一位专业医生的角度对这份体检报告进行分析，指出其中存在的问题，并用中文给出相应的建议，所有输出内容限制在150字中文以内。按json格式输出为{'analysis':xxx,\n'advise':xxx},注意{}也要输出，并且不要输出除此之外的任何内容。";

    public ModelApiService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public ModelResponse generateCompletion(String prompt, boolean stream) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("stream", stream);

        return sendPostRequest(API_URL, requestBody.toString());
    }

    public ModelResponse generateReportAdvise(String prompt, boolean stream) throws Exception {
        String query = prompt + reportAdvisePrompt;
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("stream", stream);
        return sendPostRequest(API_URL, requestBody.toString());
    }

    public ModelResponse generateCompletionWithOptions(String prompt, boolean stream, JSONObject options) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("stream", stream);
        requestBody.put("options", options);

        return sendPostRequest(API_URL, requestBody.toString());
    }

    public ModelResponse generateCompletionWithImages(String prompt, boolean stream, List<String> base64Images) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("stream", stream);
        requestBody.put("images", base64Images);

        return sendPostRequest(API_URL, requestBody.toString());
    }

    public ModelResponse generateCompletionRaw(String prompt, boolean raw) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("raw", raw);
        requestBody.put("stream", false);

        return sendPostRequest(API_URL, requestBody.toString());
    }

    public ModelResponse loadModel(String model) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);

        return sendPostRequest(API_URL, requestBody.toString());
    }

    private ModelResponse sendPostRequest(String url, String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return parseResponse(response.body());
        } else {
            throw new Exception("Error: " + response.statusCode() + " - " + response.body());
        }
    }

    private ModelResponse parseResponse(String responseBody) throws JSONException {
        JSONObject jsonObject = new JSONObject(responseBody);
        ModelResponse apiResponse = new ModelResponse();

        apiResponse.setModel(jsonObject.getString("model"));
        apiResponse.setCreatedAt(ZonedDateTime.parse(jsonObject.getString("created_at")));
        apiResponse.setResponse(jsonObject.getString("response"));
        apiResponse.setDone(jsonObject.getBoolean("done"));
        apiResponse.setDoneReason(jsonObject.getString("done_reason"));

        JSONArray contextArray = jsonObject.getJSONArray("context");
        List<Integer> contextList = new ArrayList<>();
        for (int i = 0; i < contextArray.length(); i++) {
            contextList.add(contextArray.getInt(i));
        }
        apiResponse.setContext(contextList);

        apiResponse.setTotalDuration(jsonObject.getLong("total_duration"));
        apiResponse.setLoadDuration(jsonObject.getLong("load_duration"));

        // 添加字段存在性检查
        if (jsonObject.has("prompt_eval_count")) {
            apiResponse.setPromptEvalCount(jsonObject.getInt("prompt_eval_count"));
        }
        if (jsonObject.has("prompt_eval_duration")) {
            apiResponse.setPromptEvalDuration(jsonObject.getLong("prompt_eval_duration"));
        }
        if (jsonObject.has("eval_count")) {
            apiResponse.setEvalCount(jsonObject.getInt("eval_count"));
        }
        if (jsonObject.has("eval_duration")) {
            apiResponse.setEvalDuration(jsonObject.getLong("eval_duration"));
        }

        return apiResponse;
    }
}

//    public static void main(String[] args) {
//        try {
//            ModelApiService client = new ModelApiService();
//            String response = client.generateCompletion("Why is the sky blue?", false);
//            System.out.println(response);
//
//            JSONObject options = new JSONObject();
//            options.put("seed", 42);
//            response = client.generateCompletionWithOptions("Why is the sky blue?", false, options);
//            System.out.println(response);
//
//            String image = Base64.getEncoder().encodeToString("sample image data".getBytes());
//            response = client.generateCompletionWithImages("What is in this picture?", false, List.of(image));
//            System.out.println(response);
//
//            response = client.generateCompletionRaw("[INST] why is the sky blue? [/INST]", true);
//            System.out.println(response);
//
////            response = client.loadModel("llama3");
////            System.out.println(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
