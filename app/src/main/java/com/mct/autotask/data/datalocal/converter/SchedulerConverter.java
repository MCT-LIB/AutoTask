package com.mct.autotask.data.datalocal.converter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import com.mct.autotask.domain.model.scheduler.SchedulerType;
import com.mct.autotask.utils.JsonHelper;

import java.util.Calendar;
import java.util.Set;

public class SchedulerConverter {

    public static class Type {

        @Nullable
        @TypeConverter
        public static SchedulerType fromOrdinal(int ordinal) {
            try {
                return SchedulerType.values()[ordinal];
            } catch (Throwable t) {
                return SchedulerType.ONE_TIME;
            }
        }

        @TypeConverter
        public static int toOrdinal(SchedulerType schedulerType) {
            return schedulerType == null ? SchedulerType.ONE_TIME.ordinal() : schedulerType.ordinal();
        }

    }

    public static class Date {

        @NonNull
        @TypeConverter
        public static Set<Integer> fromJson(String json) {
            return JsonHelper.jsonToSet(json, Integer.class);
        }

        @TypeConverter
        public static String toJson(Set<Integer> set) {
            return JsonHelper.objToJson(set);
        }

    }

    public static class Time {

        private static final String DIVIDER = ":";

        @NonNull
        @TypeConverter
        public static Calendar fromString(String string) {
            Calendar calendar = Calendar.getInstance();
            try {
                String[] arr = string.split(DIVIDER);
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arr[0]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(arr[1]));
            } catch (Throwable t) {
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
            }
            return calendar;
        }

        @NonNull
        @TypeConverter
        public static String toString(@NonNull Calendar calendar) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            return hour + DIVIDER + minute;
        }

    }

}
