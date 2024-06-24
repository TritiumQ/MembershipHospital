package me.qunqun.doctor.service;

import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.entity.reps.ModelResponse;
import me.qunqun.doctor.entity.vo.CheckItemReportVO;
import me.qunqun.doctor.utils.ReportConverter;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class ModelApiService {

    @Value("${model.api.url:http://localhost:11434/api/generate}")
    private String API_URL;
    private final HttpClient httpClient;
    @Value("${model.api.model:qwen2:7b}")
    private String model = "qwen2:7b";
//    private static final String reportAdvisePrompt = "请你从一位专业医生的角度对这份体检报告进行分析，指出其中存在的问题，并用中文给出相应的建议，所有输出内容限制在150字中文以内。按json格式输出为{'分析':xxx,\n'建议':xxx},注意{}也要输出，并且不要输出除此之外的任何内容。";
    private static final String reportAdvisePrompt = "请你从一位专业医生的角度对这份体检报告进行分析，帮助医生指出其中存在的问题，并帮助医生提供相应的建议，所有输出内容限制在150字中文以内。按以下风格中文输出为\"分析:xxx。\n建议:xxx\",分析和建议需要换行，注意不要输出除此之外的任何内容。";;

    private static final int THREAD_POOL_SIZE = 10;
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public ModelApiService() {
        this.httpClient = HttpClient.newHttpClient();
    }


    public ModelResponse generateReportAdvise(String prompt, boolean stream) throws Exception {
        String query = prompt + reportAdvisePrompt;
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("prompt", query);
        requestBody.put("stream", stream);
        return sendPostRequest(requestBody.toString());
    }


    private ModelResponse sendPostRequest(String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API_URL))
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

    public String transCheckItemReportVO(List<CheckItemReportVO> checkItemReportVO) throws Exception {
        String res = ReportConverter.convertToReport(checkItemReportVO);
        log.info(res);
        return res;
    }

    public ModelResponse generateCompletionWithOptions(String prompt, boolean stream, JSONObject options) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("stream", stream);
        requestBody.put("options", options);

        return sendPostRequest(requestBody.toString());
    }

    public ModelResponse generateCompletionWithImages(String prompt, boolean stream, List<String> base64Images) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("stream", stream);
        requestBody.put("images", base64Images);

        return sendPostRequest(requestBody.toString());
    }

    public ModelResponse generateCompletionRaw(String prompt, boolean raw) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("raw", raw);
        requestBody.put("stream", false);

        return sendPostRequest(requestBody.toString());
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
