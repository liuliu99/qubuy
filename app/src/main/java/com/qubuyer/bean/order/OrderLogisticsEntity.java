package com.qubuyer.bean.order;

import com.qubuyer.bean.Entity;

import java.util.List;

/**
 * @date 创建时间:2019/4/15
 * @author Susong
 * @description 订单物流实体
 & @version
 */
public class OrderLogisticsEntity extends Entity {

    /**
     * EBusinessID : test1319989
     * OrderCode :
     * ShipperCode : STO
     * LogisticCode : 201904121620398379775050
     * Success : true
     * State : 3
     * Reason : null
     * Traces : [{"AcceptTime":"2019-04-13 17:27:54","AcceptStation":"快件已经签收，签收人：张启明[武汉市]","Remark":"已经签收"},{"AcceptTime":"2019-04-10 17:27:54","AcceptStation":"快件到达武汉市武昌区徐东大街1号网点[武汉市]","Remark":"到达目的城市"},{"AcceptTime":"2019-04-09 17:27:54","AcceptStation":"快件在离开深圳集散中心，发往武汉市[深圳市]","Remark":"离开发件城市"},{"AcceptTime":"2019-04-08 17:27:54","AcceptStation":"快件已经到达深圳集散中心[深圳市]","Remark":null},{"AcceptTime":"2019-04-07 17:27:54","AcceptStation":"深圳福田保税区网点已揽件[深圳市]","Remark":"已揽件"}]
     */

    private String EBusinessID;
    private String OrderCode;
    private String ShipperCode;
    private String LogisticCode;
    private boolean Success;
    private String State;
    private Object Reason;
    private List<TracesBean> Traces;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public Object getReason() {
        return Reason;
    }

    public void setReason(Object Reason) {
        this.Reason = Reason;
    }

    public List<TracesBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<TracesBean> Traces) {
        this.Traces = Traces;
    }

    public static class TracesBean {
        /**
         * AcceptTime : 2019-04-13 17:27:54
         * AcceptStation : 快件已经签收，签收人：张启明[武汉市]
         * Remark : 已经签收
         */

        private String AcceptTime;
        private String AcceptStation;
        private String Remark;

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }
}
