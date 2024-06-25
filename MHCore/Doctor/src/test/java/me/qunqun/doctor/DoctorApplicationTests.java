package me.qunqun.doctor;

import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.entity.reps.AiResponse;
import me.qunqun.doctor.entity.vo.CheckItemReportVO;
import me.qunqun.doctor.entity.vo.CheckReportVO;
import me.qunqun.doctor.entity.vo.OrderVO;
import me.qunqun.doctor.repo.CheckItemDetailedReportRepository;
import me.qunqun.doctor.service.*;
import me.qunqun.doctor.entity.reps.Result;
import me.qunqun.doctor.utils.ScheduledTasks;
import me.qunqun.shared.entity.po.OverallResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
class DoctorApplicationTests
{
	@Autowired
	private EmailService emailService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ScheduledTasks scheduledTasks;
	@Autowired
	private WeatherService weatherService;
	@Autowired
	private CheckItemReportService checkItemReportService;
	@Autowired
	private OverallResultService overallResultService;
	@Autowired
	private CheckItemDetailedReportRepository checkItemDetailedReportRepository;
	@Autowired
	private ModelApiService modelApiService;
	@Autowired
	private AiService aiService;

	@Test
	void contextLoads()
	{
		log.info("Test start");
		Integer orderNum = orderService.getOrderCountByHospitalId(1);
		log.info("orderNum: {}", orderNum);
		scheduledTasks.sendScheduledEmail();
	}

	@Test
	void testWeather()
	{
		log.info("Test start");
		log.info("weather: {}", weatherService.getWeather("广东", "广州"));
	}

	@Test
	@Transactional
	@Rollback(false)
	void testCheck()
	{
//		Integer orderId = 100569001;
//		OrderVO orderVO = orderService.getOrderVO(orderId);
//
//		Result<List<CheckItemReportVO>> resCheckItemReportVOs = checkItemReportService.getCheckItemReports(orderVO);
//
//		Result<List<OverallResult>> resOverallResults = overallResultService.getOverallResult(orderId);
//		CheckReportVO checkReportVO = new CheckReportVO();
//		checkReportVO.setOrder(orderVO);
//		checkReportVO.setCheckItemReports(resCheckItemReportVOs.getData());
//		checkReportVO.setOverallResults(resOverallResults.getData());
//		log.error("checkReportVO: {}", checkReportVO);
	}

	@Test
	void testModelApiClient() throws Exception {
		// 计算生成文本的时间
		long startTime = System.currentTimeMillis();
		String prompt = "{ \"体检报告\": [{ \"血常规\": {\"简介\": \"临床三大常规检测之一，是最基本的血液检验。通过观察血细胞的数量变化及形态分布，从而判断血液状况及相关疾病。\",\"各项指标\": [{ \"单核细胞计数百分比\": {\"正常值\": \"4.2-15.2\",\"化验值\": \"4.99\",\"单位\": \"%\",\"是否异常\": \"无异常\"}},{ \"血小板计数\": {\"正常值\": \"100-300\",\"化验值\": \"330\",\"单位\": \"10^9/L\",\"是否异常\": \"异常\"}},{ \"血红细胞计数\": {\"正常值\": \"3.5-5.5\",\"化验值\": \"4.5\",\"单位\": \"10^12/L\",\"是否异常\": \"无异常\"}},{ \"嗜酸性粒细胞计数百分比\": {\"正常值\": \"0.2-7.6\",\"化验值\": \"2.3\",\"单位\": \"%\",\"是否异常\": \"无异常\"}},{ \"白细胞计数\": {\"正常值\": \"4-10\",\"化验值\": \"6\",\"单位\": \"10^9/L\",\"是否异常\": \"无异常\"}},{ \"红细胞压值\": {\"正常值\": \"36-50\",\"化验值\": \"57\",\"单位\": \"%\",\"是否异常\": \"异常\"}},{ \"中性粒细胞计数绝对值\": {\"正常值\": \"1.8-8.89\",\"化验值\": \"6.33\",\"单位\": \"10^9/L\",\"是否异常\": \"无异常\"}},{ \"淋巴细胞计数百分比\": {\"正常值\": \"18.3-47.9\",\"化验值\": \"25.7\",\"单位\": \"%\",\"是否异常\": \"无异常\"}}]}},{ \"一般检查\": {\"简介\": \"通过仪器测定人体基本健康指标。\",\"各项指标\": [{ \"舒张压\": {\"正常值\": \"<90\",\"化验值\": \"145\",\"单位\": \"null\",\"是否异常\": \"异常\"}},{ \"收缩压\": {\"正常值\": \"<140\",\"化验值\": \"453\",\"单位\": \"null\",\"是否异常\": \"异常\"}},{ \"体重\": {\"正常值\": \"null\",\"化验值\": \"77\",\"单位\": \"kg\",\"是否异常\": \"无异常\"}},{ \"身高\": {\"正常值\": \"null\",\"化验值\": \"533\",\"单位\": \"cm\",\"是否异常\": \"无异常\"}},{ \"腰围\": {\"正常值\": \"null\",\"化验值\": \"42\",\"单位\": \"cm\",\"是否异常\": \"无异常\"}},{ \"臀围\": {\"正常值\": \"null\",\"化验值\": \"68\",\"单位\": \"cm\",\"是否异常\": \"无异常\"}}]}},{ \"尿常规\": {\"简介\": \"临床三大常规检测之一，可反映机体的代谢状况，是很多疾病诊断的重要指标。\",\"各项指标\": [{ \"尿蛋白定性\": {\"正常值\": \"-\",\"化验值\": \"100\",\"单位\": \"g/L\",\"是否异常\": \"无异常\"}},{ \"尿比重\": {\"正常值\": \"1.015-1.025\",\"化验值\": \"1.019\",\"单位\": \"null\",\"是否异常\": \"无异常\"}},{ \"尿亚硝酸盐\": {\"正常值\": \"-（阴性）\",\"化验值\": \"阴性\",\"单位\": \"null\",\"是否异常\": \"无异常\"}},{ \"尿维生素\": {\"正常值\": \"0\",\"化验值\": \"0\",\"单位\": \"mmol/L\",\"是否异常\": \"无异常\"}},{ \"尿白细胞\": {\"正常值\": \"-\",\"化验值\": \"2.0\",\"单位\": \"/ul\",\"是否异常\": \"无异常\"}},{ \"尿液酸碱度\": {\"正常值\": \"5.5-6.5\",\"化验值\": \"5.7\",\"单位\": \"null\",\"是否异常\": \"无异常\"}}]}}] }";

//		ModelResponse api = modelApiService.generateReportAdvise(prompt, false);
		AiResponse api = aiService.sendChatRequest(prompt);
		long endTime = System.currentTimeMillis();
		log.info("Time: {}", (endTime - startTime) / 1000.0 + "s");
		log.info("api: {}", api.getData().getTextResponse());
	}

	
}
