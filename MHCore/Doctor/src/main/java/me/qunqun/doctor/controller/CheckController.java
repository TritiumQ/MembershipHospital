package me.qunqun.doctor.controller;

import me.qunqun.shared.entity.po.*;
import me.qunqun.doctor.entity.dto.OrderQueryDTO;
import me.qunqun.doctor.entity.vo.CheckItemReportVO;
import me.qunqun.doctor.entity.vo.CheckReportVO;
import me.qunqun.doctor.entity.vo.OrderVO;
import me.qunqun.doctor.repo.OrderRepository;
import me.qunqun.doctor.service.*;
import me.qunqun.doctor.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/check")
public class CheckController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CheckItemReportService checkItemReportService;
    @Autowired
    private OverallResultService overallResultService;

    @RequestMapping("/Orders")
    public Result<List<OrderVO> > OrderList(@RequestBody OrderQueryDTO orderQueryDTO,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        if (orderQueryDTO == null) {
            return Result.error(400, "参数错误");
        }
        return orderService.getOrders(orderQueryDTO, page, size);
    }

    @RequestMapping("/OrdersAll")
    public Result<List<OrderVO> > OrderListAll(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return orderService.getOrders(null, page, size);
    }

    @RequestMapping("/CheckReport")
    public Result<CheckReportVO> CheckReport(@RequestParam int orderId) {
        OrderVO orderVO = orderService.getOrderVO(orderId);
        if (orderVO == null) {
            return Result.error(400, "检查单不存在");
        }
        Result<List<CheckItemReportVO>> resCheckItemReportVOs = checkItemReportService.getCheckItemReports(orderVO);
        if (resCheckItemReportVOs.getCode() != 200) {
            return Result.error(resCheckItemReportVOs.getCode(), resCheckItemReportVOs.getMessage());
        }
        Result<List<OverallResult>> resOverallResults = overallResultService.getOverallResult(orderId);
        if (resOverallResults.getCode() != 200) {
            return Result.error(resOverallResults.getCode(), resOverallResults.getMessage());
        }
        CheckReportVO checkReportVO = new CheckReportVO();
        checkReportVO.setOrder(orderVO);
        checkReportVO.setCheckItemReports(resCheckItemReportVOs.getData());
        checkReportVO.setOverallResults(resOverallResults.getData());
        return Result.success(checkReportVO);
    }


}
