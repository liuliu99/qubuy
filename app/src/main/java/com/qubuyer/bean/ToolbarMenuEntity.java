package com.qubuyer.bean;

import com.qubuyer.customview.AbsToolbar;

public class ToolbarMenuEntity {
    private int dpWidth;
    private int dpHeight;
    private String menuContent;
    private int menuDrawaleId;
    private int menuId;
    private AbsToolbar.OnMenuOnClickListener onMenuOnClickListener;

    public ToolbarMenuEntity() {
    }

    public ToolbarMenuEntity(int dpWidth, int dpHeight, String menuContent, int menuDrawaleId, AbsToolbar.OnMenuOnClickListener onMenuOnClickListener) {
        this.dpWidth = dpWidth;
        this.dpHeight = dpHeight;
        this.menuContent = menuContent;
        this.menuDrawaleId = menuDrawaleId;
        this.onMenuOnClickListener = onMenuOnClickListener;
    }

    public int getDpWidth() {
        return dpWidth;
    }

    public void setDpWidth(int dpWidth) {
        this.dpWidth = dpWidth;
    }

    public int getDpHeight() {
        return dpHeight;
    }

    public void setDpHeight(int dpHeight) {
        this.dpHeight = dpHeight;
    }

    public String getMenuContent() {
        return menuContent;
    }

    public void setMenuContent(String menuContent) {
        this.menuContent = menuContent;
    }

    public int getMenuDrawaleId() {
        return menuDrawaleId;
    }

    public void setMenuDrawaleId(int menuDrawaleId) {
        this.menuDrawaleId = menuDrawaleId;
    }

    public AbsToolbar.OnMenuOnClickListener getOnMenuOnClickListener() {
        return onMenuOnClickListener;
    }

    public void setOnMenuOnClickListener(AbsToolbar.OnMenuOnClickListener onMenuOnClickListener) {
        this.onMenuOnClickListener = onMenuOnClickListener;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
