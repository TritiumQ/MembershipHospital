package me.qunqun.doctor.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.qunqun.shared.manager.mq.entity.ObjectMessage;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsMqMessage<T> {
    @NonNull
    private String id;
    @NonNull
    private String message;
    @Nullable
    private T messageData;

    public static <T> SmsMqMessage<T> create(String message, T messageData)
    {
        return new SmsMqMessage<>(UUID.randomUUID().toString(), message, messageData);
    }
}
