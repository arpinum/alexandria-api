package arpinum.ddd.event;

import arpinum.infrastructure.date.Dates;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.reflect.TypeToken;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "_class")
public abstract class Event<T> {

    protected Event() {
    }

    public Event(Object targetId) {
        this.targetId = targetId;
    }

    public Class<T> targetType() {
        return (Class<T>) new TypeToken<T>(getClass()) {
        }.getRawType();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Object getTargetId() {
        return targetId;
    }

    public String getTargetType() {
        return targetType;
    }

    private String targetType = targetType().getSimpleName();
    private long timestamp = Dates.instantNow().toEpochMilli();
    private Object targetId;
}
