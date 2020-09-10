package com.qubuyer.bean.address;

import com.qubuyer.bean.Entity;

/**
 * @author jiangtianming
 * @date 2019/3/19
 * @description
 */
public class DistrictData extends Entity {
    private int id;

    private int parent_id;

    private String spell;

    private String name;

    private int level;

    private String lng;

    private String lat;

    private int status;

    private String level_text;

    private String status_text;


    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setSpell(String spell){
        this.spell = spell;
    }
    public String getSpell(){
        return this.spell;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public int getLevel(){
        return this.level;
    }
    public void setLng(String lng){
        this.lng = lng;
    }
    public String getLng(){
        return this.lng;
    }
    public void setLat(String lat){
        this.lat = lat;
    }
    public String getLat(){
        return this.lat;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setLevel_text(String level_text){
        this.level_text = level_text;
    }
    public String getLevel_text(){
        return this.level_text;
    }
    public void setStatus_text(String status_text){
        this.status_text = status_text;
    }
    public String getStatus_text(){
        return this.status_text;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
