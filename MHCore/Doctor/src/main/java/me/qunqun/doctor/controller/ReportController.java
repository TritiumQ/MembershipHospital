package me.qunqun.doctor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.entity.dto.EditReportDTO;
import me.qunqun.doctor.entity.dto.QueryReportDTO;
import me.qunqun.doctor.entity.reps.AiResponse;
import me.qunqun.doctor.entity.vo.OrderVO;
import me.qunqun.doctor.mq.AiMqConfig;
import me.qunqun.doctor.mq.AiTaskConsumer;
import me.qunqun.doctor.mq.SmsMqMessage;
import me.qunqun.doctor.mq.SmsMqProducer;
import me.qunqun.doctor.service.*;
import me.qunqun.doctor.entity.reps.Result;
import me.qunqun.shared.manager.sms.SmsManager;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@Tag(name = "Report", description = "报告接口")
@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CheckItemReportService checkItemReportService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private ModelApiService modelApiService;
    @Autowired
    private AiService aiService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private AiTaskConsumer aiTaskConsumer;


    @Operation(summary = "保存检查单")
    @RequestMapping("/editReport")
    public Result<String> editReport(@RequestBody EditReportDTO editReportDTO) {
        log.info("editReport: {}", editReportDTO);
        try {
            if (editReportDTO == null) {
                return Result.error(400, "参数错误");
            }
            if (editReportDTO.getOrderId() == null) {
                return Result.error(400, "检查单错误");
            }
            OrderVO orderVO = orderService.getOrderVO(editReportDTO.getOrderId());
            if (orderVO == null) {
                return Result.error(400, "检查单不存在");
            }
            else if (orderVO.getStatus() == 2)
            {
                return Result.error(400, "检查单已归档，无法编辑");
            }
            return checkItemReportService.editReport(editReportDTO);
        } catch (RuntimeException e) {
            return Result.error(500, "检查单编辑失败");
        }
    }

    @Operation(summary = "归档检查单")
    @RequestMapping("/saveReport")
    public Result<String> saveReport(@RequestBody EditReportDTO editReportDTO) {
        log.info("saveReport: {}", editReportDTO);
        try {
            if (editReportDTO == null) {
                return Result.error(400, "参数错误");
            }
            if (editReportDTO.getOrderId() == null) {
                return Result.error(400, "检查单错误");
            }
            OrderVO orderVO = orderService.getOrderVO(editReportDTO.getOrderId());
            if (orderVO == null) {
                return Result.error(400, "检查单不存在");
            }
            else if (orderVO.getStatus() == 2)
            {
                return Result.error(400, "检查单已归档，无法重复归档");
            }
            Result<String> result = checkItemReportService.saveReport(editReportDTO);
            if (result.getCode() == 200) {
//                smsService.sendSmsReportComplete(editReportDTO.getOrderId());
                smsService.sendSmsReportCompleteMq(SmsMqMessage.create("体检报告归档，发送短信通知客户",editReportDTO.getOrderId()));
            }
            return result;
        } catch (RuntimeException e) {
            log.error("saveReport error: {}", e.getMessage());
            return Result.error(500, "检查单编辑失败");
        }
    }

    @Operation(summary = "询问AI")
    @RequestMapping("/askAI")
    public Result<String> askAI(@RequestBody EditReportDTO editReportDTO) throws Exception {
        try {
            if (editReportDTO == null) {
                return Result.error(400, "参数错误");
            }
            if (editReportDTO.getOrderId() == null) {
                return Result.error(400, "检查单错误");
            }
            OrderVO orderVO = orderService.getOrderVO(editReportDTO.getOrderId());
            if (orderVO == null) {
                return Result.error(400, "检查单不存在");
            }
            String prompt = modelApiService.transCheckItemReportVO(editReportDTO.getCheckItemReports());
//            String ans = modelApiService.generateReportAdvise(prompt, false).getResponse();
            AiResponse aiResponse = aiService.sendChatRequest(prompt);
            String ans = aiResponse.getData().getTextResponse();
            log.info("askAI: {}", ans);
            return Result.success(ans);
        } catch (RuntimeException e) {
            return Result.error(500, "询问AI失败");
        }
    }


    @Operation(summary = "询问AI")
    @RequestMapping("/askAIMq")
    public Result<String> askAIMq(@RequestBody EditReportDTO editReportDTO) throws Exception {
        try {
            if (editReportDTO == null) {
                return Result.error(400, "参数错误");
            }
            if (editReportDTO.getOrderId() == null) {
                return Result.error(400, "检查单错误");
            }
            OrderVO orderVO = orderService.getOrderVO(editReportDTO.getOrderId());
            if (orderVO == null) {
                return Result.error(400, "检查单不存在");
            }
            QueryReportDTO queryReportDTO = QueryReportDTO.fromEditReportDTO(editReportDTO);
            String taskId = aiTaskConsumer.generateTaskId(editReportDTO.getOrderId());
            queryReportDTO.setOrderId(taskId);
            // 将任务发送到消息队列
            rabbitTemplate.convertAndSend(AiMqConfig.AIMQ_DIRECT_EXCHANGE, AiMqConfig.AIMQ_DIRECT_ROUTING_KEY, queryReportDTO);
            return Result.success(taskId);
        } catch (RuntimeException e) {
            return Result.error(500, "询问AI失败");
        }
    }

    @Operation(summary = "查询任务状态")
    @RequestMapping("/taskStatus/{orderId}")
    public Result<TaskStatusService.TaskStatus> getTaskStatus(@PathVariable String orderId) {
        TaskStatusService.TaskStatus status = taskStatusService.getTaskStatus(orderId);
        log.info("getTaskStatus: {}", status);
        if (status != null) {
            return Result.success(status);
        } else {
            TaskStatusService.TaskStatus taskStatus = new TaskStatusService.TaskStatus("miss", null);
            return Result.success(taskStatus);
        }
    }
}
