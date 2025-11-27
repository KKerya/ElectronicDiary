package com.kirillkabylov.NauJava.enums;

public enum AttendanceStatus {
    PRESENT("Присутствовал"),
    LATE("Опоздание"),
    EXCUSED_ABSENCE("Отсутствовал по уважительной причине"),
    UNEXCUSED_ABSENCE("Отсутствовал по неуважительной причине");

    private final String displayName;

    AttendanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}