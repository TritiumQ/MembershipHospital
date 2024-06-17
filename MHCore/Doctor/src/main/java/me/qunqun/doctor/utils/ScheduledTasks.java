package me.qunqun.doctor.utils;

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

@Component
public class ScheduledTasks {

    @Autowired
    private EmailService emailService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 8 * * MON-FRI")
    public void sendScheduledEmail() {
        String subject = "Daily Report";
        StringBuilder text = new StringBuilder();
        List<Doctor> doctors = doctorService.getAllDoctors();
        LocalDate currentDate = getCurrentLocalDate();
        for (Doctor doctor : doctors) {
            String to = doctor.getEmail();
            Integer orderNum = orderService.getOrderCountByHospitalIdAndDate(doctor.getDeptNo(), currentDate);
            text.append("Dear Dr.")
                    .append(doctor.getRealName())
                    .append(", you have ")
                    .append(orderNum)
                    .append(" medical reports pending today.\n")
                    .append("Please check the system for more details.\n")
                    .append(currentDate);
            emailService.sendEmail(to, subject, text.toString());
            text.setLength(0);
        }
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