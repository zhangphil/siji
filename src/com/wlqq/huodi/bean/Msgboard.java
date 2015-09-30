package com.wlqq.huodi.bean;

import java.io.Serializable;
import java.util.List;

public class Msgboard implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6054795486918999034L;

    private long pid;
    private long cid;
    private Message.Type type;
    private long totalNumber;
    private long overallTotalNumber;
    private long autoReloadInterval;
    private long max;
    private long min;
    private List<Message> msgs;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public Message.Type getType() {
        return type;
    }

    public void setType(Message.Type type) {
        this.type = type;
    }

    public long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public long getOverallTotalNumber() {
        return overallTotalNumber;
    }

    public void setOverallTotalNumber(long overallTotalNumber) {
        this.overallTotalNumber = overallTotalNumber;
    }

    public long getAutoReloadInterval() {
        return autoReloadInterval;
    }

    public void setAutoReloadInterval(long autoReloadInterval) {
        this.autoReloadInterval = autoReloadInterval;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public List<Message> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Msgboard");
        sb.append("{pid=").append(pid);
        sb.append(", cid=").append(cid);
        sb.append(", type=").append(type);
        sb.append(", totalNumber=").append(totalNumber);
        sb.append(", overallTotalNumber=").append(overallTotalNumber);
        sb.append(", autoReloadInterval=").append(autoReloadInterval);
        sb.append(", max=").append(max);
        sb.append(", min=").append(min);
        sb.append(", msgs=").append(msgs);
        sb.append('}');
        return sb.toString();
    }
}
