package com.weiteng.weitengapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class SpendingInfo implements Serializable{
    private String count;
    private List<SpIn> exchange;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<SpIn> getExchange() {
        return exchange;
    }

    public void setExchange(List<SpIn> exchange) {
        this.exchange = exchange;
    }

    public class SpIn{
        private String Sshopname;
        private String EexchangeTime;
        private String EexchangeAmount;

        public String getSshopname() {
            return Sshopname;
        }

        public void setSshopname(String sshopname) {
            Sshopname = sshopname;
        }

        public String getEexchangeTime() {
            return EexchangeTime;
        }

        public void setEexchangeTime(String eexchangeTime) {
            EexchangeTime = eexchangeTime;
        }

        public String getEexchangeAmount() {
            return EexchangeAmount;
        }

        public void setEexchangeAmount(String eexchangeAmount) {
            EexchangeAmount = eexchangeAmount;
        }
    }
}
