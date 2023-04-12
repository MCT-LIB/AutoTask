package com.mct.autotask.domain.model.scheduler;

import androidx.annotation.IntDef;

import java.util.Calendar;

@IntDef({
        Calendar.SUNDAY,
        Calendar.MONDAY,
        Calendar.TUESDAY,
        Calendar.WEDNESDAY,
        Calendar.THURSDAY,
        Calendar.FRIDAY,
        Calendar.SATURDAY,
})
public @interface SchedulerDate {
}
