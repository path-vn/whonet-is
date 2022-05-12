package org.path.amr.services.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "whonet", ignoreUnknownFields = false)
public class WhonetConfiguration {

    String priority;
    Integer thread;
    Integer year;
    String clsi;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getThread() {
        return thread;
    }

    public void setThread(Integer thread) {
        this.thread = thread;
    }

    public Integer getYear() {
        if (year == null || year == 0) {
            year = 2022;
        }
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getClsi() {
        if (clsi == null) {
            clsi = "CLSI";
        }
        return clsi;
    }

    public void setClsi(String clsi) {
        this.clsi = clsi;
    }
}
