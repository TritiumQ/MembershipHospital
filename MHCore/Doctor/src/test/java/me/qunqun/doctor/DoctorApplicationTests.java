package me.qunqun.doctor;

import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.entity.reps.ModelResponse;
import me.qunqun.doctor.entity.vo.CheckItemReportVO;
import me.qunqun.doctor.entity.vo.CheckReportVO;
import me.qunqun.doctor.entity.vo.OrderVO;
import me.qunqun.doctor.repo.CheckItemDetailedReportRepository;
import me.qunqun.doctor.service.*;
import me.qunqun.doctor.utils.Result;
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
		Integer orderId = 100569001;
		OrderVO orderVO = orderService.getOrderVO(orderId);

		Result<List<CheckItemReportVO>> resCheckItemReportVOs = checkItemReportService.getCheckItemReports(orderVO);

		Result<List<OverallResult>> resOverallResults = overallResultService.getOverallResult(orderId);
		CheckReportVO checkReportVO = new CheckReportVO();
		checkReportVO.setOrder(orderVO);
		checkReportVO.setCheckItemReports(resCheckItemReportVOs.getData());
		checkReportVO.setOverallResults(resOverallResults.getData());
		log.error("checkReportVO: {}", checkReportVO);
	}

	@Test
	void testModelApiClient() throws Exception {
		String prompt = "\"checkItemReports\": [\n" +
				"        {\n" +
				"            \"id\": 19,\n" +
				"            \"checkItemId\": 6,\n" +
				"            \"name\": \"妇科检查\",\n" +
				"            \"remark\": null,\n" +
				"            \"content\": \"全身检查、腹部检查和盆腔检查。检查外阴、阴道、子宫颈和子宫、输卵管、卵巢及宫旁组织和骨盆腔内壁。\",\n" +
				"            \"meaning\": \"主要作用是对一些妇科疾病作出早期诊断、预防以及早期治疗。\",\n" +
				"            \"review\": \"妇科检查体检报告：盆腔检查异常\",\n" +
				"            \"checkItemDetailedReports\": [\n" +
				"                {\n" +
				"                    \"id\": 107,\n" +
				"                    \"detailName\": \"乳腺检查\",\n" +
				"                    \"value\": \"描述....无异常\",\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": null,\n" +
				"                    \"type\": 4,\n" +
				"                    \"minRange\": null,\n" +
				"                    \"maxRange\": null,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": null\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 108,\n" +
				"                    \"detailName\": \"盆腔检查\",\n" +
				"                    \"value\": \"描述...异常\",\n" +
				"                    \"error\": \"异常\",\n" +
				"                    \"errorType\": 1,\n" +
				"                    \"unit\": null,\n" +
				"                    \"type\": 4,\n" +
				"                    \"minRange\": null,\n" +
				"                    \"maxRange\": null,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": null\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 109,\n" +
				"                    \"detailName\": \"子宫检查\",\n" +
				"                    \"value\": \"描述....无异常\",\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": null,\n" +
				"                    \"type\": 4,\n" +
				"                    \"minRange\": null,\n" +
				"                    \"maxRange\": null,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": null\n" +
				"                }\n" +
				"            ]\n" +
				"        },\n" +
				"        {\n" +
				"            \"id\": 17,\n" +
				"            \"checkItemId\": 2,\n" +
				"            \"name\": \"血常规\",\n" +
				"            \"remark\": null,\n" +
				"            \"content\": \"血常规24项:\\r\\n                中性粒细胞计数绝对值、红细胞压值、血小板比容、单核细胞计数百分比、平均血小板体积、\\r\\n                大血小板比例、嗜碱性粒细胞计数百分比、平均血红蛋白含量等。\",\n" +
				"            \"meaning\": \"临床三大常规检测之一，是最基本的血液检验。通过观察血细胞的数量变化及形态分布，从而判断血液状况及相关疾病。\",\n" +
				"            \"review\": null,\n" +
				"            \"checkItemDetailedReports\": [\n" +
				"                {\n" +
				"                    \"id\": 93,\n" +
				"                    \"detailName\": \"白细胞计数\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"10^9/L\",\n" +
				"                    \"type\": 1,\n" +
				"                    \"minRange\": 4.0,\n" +
				"                    \"maxRange\": 10.0,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": \"4-10\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 94,\n" +
				"                    \"detailName\": \"血小板计数\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"10^9/L\",\n" +
				"                    \"type\": 1,\n" +
				"                    \"minRange\": 100.0,\n" +
				"                    \"maxRange\": 300.0,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": \"100-300\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 95,\n" +
				"                    \"detailName\": \"红细胞压值\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"%\",\n" +
				"                    \"type\": 1,\n" +
				"                    \"minRange\": 36.0,\n" +
				"                    \"maxRange\": 50.0,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": \"36-50\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 96,\n" +
				"                    \"detailName\": \"嗜酸性粒细胞计数百分比\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"%\",\n" +
				"                    \"type\": 1,\n" +
				"                    \"minRange\": 0.2,\n" +
				"                    \"maxRange\": 7.6,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": \"0.2-7.6\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 97,\n" +
				"                    \"detailName\": \"血红细胞计数\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"10^12/L\",\n" +
				"                    \"type\": 1,\n" +
				"                    \"minRange\": 3.5,\n" +
				"                    \"maxRange\": 5.5,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": \"3.5-5.5\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 98,\n" +
				"                    \"detailName\": \"淋巴细胞计数百分比\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"%\",\n" +
				"                    \"type\": 1,\n" +
				"                    \"minRange\": 18.3,\n" +
				"                    \"maxRange\": 47.9,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": \"18.3-47.9\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 99,\n" +
				"                    \"detailName\": \"中性粒细胞计数绝对值\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"10^9/L\",\n" +
				"                    \"type\": 1,\n" +
				"                    \"minRange\": 1.8,\n" +
				"                    \"maxRange\": 8.89,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": \"1.8-8.89\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 100,\n" +
				"                    \"detailName\": \"单核细胞计数百分比\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"%\",\n" +
				"                    \"type\": 1,\n" +
				"                    \"minRange\": 4.2,\n" +
				"                    \"maxRange\": 15.2,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": \"4.2-15.2\"\n" +
				"                }\n" +
				"            ]\n" +
				"        },\n" +
				"        {\n" +
				"            \"id\": 18,\n" +
				"            \"checkItemId\": 1,\n" +
				"            \"name\": \"一般检查\",\n" +
				"            \"remark\": null,\n" +
				"            \"content\": \"血压、身高、体重、腰围、臀围。\",\n" +
				"            \"meaning\": \"通过仪器测定人体基本健康指标。\",\n" +
				"            \"review\": null,\n" +
				"            \"checkItemDetailedReports\": [\n" +
				"                {\n" +
				"                    \"id\": 101,\n" +
				"                    \"detailName\": \"收缩压\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": null,\n" +
				"                    \"type\": 1,\n" +
				"                    \"minRange\": 0.0,\n" +
				"                    \"maxRange\": 140.0,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": \"<140\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 102,\n" +
				"                    \"detailName\": \"臀围\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"cm\",\n" +
				"                    \"type\": 3,\n" +
				"                    \"minRange\": null,\n" +
				"                    \"maxRange\": null,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": null\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 103,\n" +
				"                    \"detailName\": \"舒张压\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": null,\n" +
				"                    \"type\": 1,\n" +
				"                    \"minRange\": 0.0,\n" +
				"                    \"maxRange\": 90.0,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": \"<90\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 104,\n" +
				"                    \"detailName\": \"身高\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"cm\",\n" +
				"                    \"type\": 3,\n" +
				"                    \"minRange\": null,\n" +
				"                    \"maxRange\": null,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": null\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 105,\n" +
				"                    \"detailName\": \"腰围\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"cm\",\n" +
				"                    \"type\": 3,\n" +
				"                    \"minRange\": null,\n" +
				"                    \"maxRange\": null,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": null\n" +
				"                },\n" +
				"                {\n" +
				"                    \"id\": 106,\n" +
				"                    \"detailName\": \"体重\",\n" +
				"                    \"value\": null,\n" +
				"                    \"error\": \"无异常\",\n" +
				"                    \"errorType\": 0,\n" +
				"                    \"unit\": \"kg\",\n" +
				"                    \"type\": 3,\n" +
				"                    \"minRange\": null,\n" +
				"                    \"maxRange\": null,\n" +
				"                    \"normalValue\": null,\n" +
				"                    \"normalValueDescription\": null\n" +
				"                }\n" +
				"            ]\n" +
				"        }";

		ModelResponse api = modelApiService.generateReportAdvise(prompt, false);
		log.info("api: {}", api.getResponse());
	}
	
}
