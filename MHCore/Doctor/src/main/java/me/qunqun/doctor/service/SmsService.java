package me.qunqun.doctor.service;

import me.qunqun.doctor.mq.SmsMqConfig;
import me.qunqun.doctor.mq.SmsMqMessage;
import me.qunqun.doctor.repo.OrderRepository;
import me.qunqun.doctor.utils.SmsUtil;
import me.qunqun.shared.entity.po.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SmsUtil smsUtil;

    @Async
    public void sendSmsReportComplete(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
          return;
        }
        smsUtil.sendSMSReportComplete(order.getUser().getId(), order.getUser().getRealName(), orderId.toString());
    }

    @RabbitListener(queues = SmsMqConfig.SMSMQ_DIRECT_QUEUE)
    public void sendSmsReportCompleteMq(SmsMqMessage<Integer> message) {
        Integer orderId = message.getMessageData();
        Order order = null;
        if (orderId != null) {
            order = orderRepository.findById(orderId).orElse(null);
        }
        if (order == null) {
          return;
        }
        smsUtil.sendSMSReportComplete(order.getUser().getId(), order.getUser().getRealName(), orderId.toString());
    }

}
