package com.example.coronavirusherdimmunitydoctor.enums;


public enum PatientStatus {

    NEGATIVE,               // 0: negative (normal)
    INFECTED,               // 1: infected (positive)
    SUSPECTED,              // 2: suspect (pending)
    HEALED,                 // 3: healed
    QUARANTINE_LIGHT,       // 4: quarantine_light
    QUARANTINE_WARNING,     // 5: quarantine_warning
    QUARANTINE_ALERT        // 6: quarantine_alert
}