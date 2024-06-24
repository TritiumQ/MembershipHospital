package me.qunqun.doctor.service;

import me.qunqun.doctor.repo.OrderRepository;
import me.qunqun.doctor.utils.SmsUtil;
import me.qunqun.shared.entity.po.Order;
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

}
