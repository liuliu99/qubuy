package com.qubuyer.bean.payment;

import com.qubuyer.bean.Entity;

/**
 * @date 创建时间:2019/3/15
 * @author Susong
 * @description 微信支付参数实体
 & @version
 */
public class WechatPayParamEntity extends Entity {
    /**
     * appid : wx2b42e1d03a2f96eb
     * partnerid : 1522877091
     * prepayid : wx08173719841241b2e0aba3e23910621958
     * timestamp : 1552037840
     * noncestr : lApwcnEovWLtjNZ8
     * package : Sign=WXPay
     * sign : 41CD21FF308554DEA1EB2F8FDD25449E
     */
    private String appid;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String noncestr;
    //        @SerializedName("package")
    private String packageX;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
