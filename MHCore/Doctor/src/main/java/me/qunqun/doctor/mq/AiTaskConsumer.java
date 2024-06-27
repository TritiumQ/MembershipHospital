package me.qunqun.doctor.mq;

import me.qunqun.doctor.entity.dto.EditReportDTO;
import me.qunqun.doctor.entity.dto.QueryReportDTO;
import me.qunqun.doctor.entity.reps.AiResponse;
import me.qunqun.doctor.service.AiService;
import me.qunqun.doctor.service.ModelApiService;
import me.qunqun.doctor.service.TaskStatusService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Service
public class AiTaskConsumer {

    @Autowired
    private AiService aiService;
    @Autowired
    private ModelApiService modelApiService;

    @Autowired
    private TaskStatusService taskStatusService; // 自定义服务，用于记录任务状态

    @RabbitListener(queues = AiMqConfig.AIMQ_DIRECT_QUEUE)
    public void processAiTask(QueryReportDTO editReportDTO) {
        try {
            String taskId = editReportDTO.getOrderId();
            String prompt = modelApiService.transCheckItemReportVO(editReportDTO.getCheckItemReports());
            AiResponse aiResponse = aiService.sendChatRequest(prompt);
            String ans = aiResponse.getData().getTextResponse();

            // 更新任务状态和结果
            taskStatusService.updateTaskStatus(taskId, "completed", ans);
        } catch (Exception e) {
            taskStatusService.updateTaskStatus(editReportDTO.getOrderId(), "failed", e.getMessage());
        }
    }

    public String generateTaskId(Integer orderId) {
        try {
            String input = orderId + "-" + new Date().getTime();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating task ID", e);
        }
    }
}
