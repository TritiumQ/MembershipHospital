package me.qunqun.doctor.utils;

import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.entity.reps.WeatherResponse;
import me.qunqun.doctor.service.WeatherService;
import me.qunqun.shared.entity.po.Doctor;
import me.qunqun.doctor.service.DoctorService;
import me.qunqun.doctor.service.EmailService;
import me.qunqun.doctor.service.OrderService;
import me.qunqun.shared.entity.po.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ScheduledTasks {

    @Autowired
    private EmailService emailService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Resource
    private SmsUtil SmsUtil;

    private static final int THREAD_POOL_SIZE = 10;
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    @Transactional
    @Scheduled(cron = "0 0 9 * * MON-FRI")
    public void sendScheduledEmail() {
        WeatherResponse weather = weatherService.getWeather("广东", "广州");
        List<Doctor> doctors = doctorService.getAllDoctors();
        LocalDate currentDate = getCurrentLocalDate().plusDays(1); // 加1查询时包括当天
        String weatherText = String.format("Weather: %s to %s, temperature: %s°C, wind: %s %sm/s\n",
                weather.getWeather1(), weather.getWeather2(), weather.getTemperature(), weather.getWindDirection(), weather.getWindSpeed());
        try {
            for (Doctor doctor : doctors) {
                executorService.submit(() -> {
                    try {
                        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
                        transactionTemplate.execute(status -> {
                            sendEmailToDoctor(doctor, currentDate, weatherText);
                            return null;
                        });
                    } catch (Exception e) {
                        log.error("Error sending email to doctor: {}", doctor.getId(), e);
                    }
                });
            }
        } catch (Exception e) {
            log.error("Error scheduling email tasks", e);
        }
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void sendCheckSms() {
        log.info("sendCheckSms start");
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Order> orders = orderService.getOrdersByDateAndDeprecated(tomorrow);
        for (Order order : orders) {
            String date = tomorrow.toString();
            String hospitalName = order.getHospital().getName();
            String mobile = order.getUser().getId();
            String userName = order.getUser().getRealName();
            String orderId = order.getId().toString();
            executorService.submit(() -> {
                try {
                    SmsUtil.sendSMSTip(mobile, userName, orderId, hospitalName, date);
                } catch (Exception e) {
                    log.error("Error sending SMS for order: {}", order.getId(), e);
                }
            });
        }
        log.info("sendCheckSms end");
    }


    private void sendEmailToDoctor(Doctor doctor, LocalDate currentDate, String weatherText) {
        String subject = "Daily Report";
        String to = doctor.getEmail();
        StringBuilder text = new StringBuilder();
//        Integer orderNum = orderService.getOrderCountByHospitalIdAndDate(doctor.getDeptNo(), currentDate);
        Integer orderNum = orderService.getOrderCountByHospitalIdAndDateBeforeAndState(doctor.getDeptNo(), currentDate, 1);
        text.append("Dear Dr.")
                .append(doctor.getRealName())
                .append(", you have ")
                .append(orderNum)
                .append(" medical reports pending today.\n")
                .append("Please check the system for more details.\n")
                .append("Today's weather: \n")
                .append(weatherText)
                .append(currentDate);
        emailService.sendEmail(to, subject, text.toString());
    }



    private Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // 内部类用于传递 SMS 数据
    private static class SmsData {
        String mobile;
        String userName;
        String orderId;
        String hospitalName;
        String date;

        SmsData(String mobile, String userName, String orderId, String hospitalName, String date) {
            this.mobile = mobile;
            this.userName = userName;
            this.orderId = orderId;
            this.hospitalName = hospitalName;
            this.date = date;
        }
    }


    @PreDestroy
    public void onDestroy() {
        log.info("Shutting down ExecutorService");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    log.error("ExecutorService did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}