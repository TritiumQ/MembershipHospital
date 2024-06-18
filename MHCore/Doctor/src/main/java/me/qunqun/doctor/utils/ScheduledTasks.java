package me.qunqun.doctor.utils;

import me.qunqun.doctor.entity.reps.WeatherResponse;
import me.qunqun.doctor.service.WeatherService;
import me.qunqun.shared.entity.po.Doctor;
import me.qunqun.doctor.entity.dto.OrderQueryDTO;
import me.qunqun.doctor.service.DoctorService;
import me.qunqun.doctor.service.EmailService;
import me.qunqun.doctor.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    private static final int THREAD_POOL_SIZE = 10; // Define the number of threads in the pool

    @Scheduled(cron = "0 0 8 * * MON-FRI")
    public void sendScheduledEmail() {
        WeatherResponse weather = weatherService.getWeather("广东", "广州");
        List<Doctor> doctors = doctorService.getAllDoctors();
        LocalDate currentDate = getCurrentLocalDate();
        String weatherText = "Weather: " + weather.getWeather1() + " to " + weather.getWeather2() + ", temperature: " + weather.getTemperature() + "°C, wind: " + weather.getWindDirection() + " " + weather.getWindSpeed() + "m/s\n";

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for (Doctor doctor : doctors) {
            executorService.submit(() -> {
                try {
                    sendEmailToDoctor(doctor, currentDate, weatherText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    private void sendEmailToDoctor(Doctor doctor, LocalDate currentDate, String weatherText) {
        String subject = "Daily Report";
        String to = doctor.getEmail();
        StringBuilder text = new StringBuilder();
        Integer orderNum = orderService.getOrderCountByHospitalIdAndDate(doctor.getDeptNo(), currentDate);
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
}