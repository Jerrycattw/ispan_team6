package com.coupon.dto;

import org.springframework.stereotype.Component;

@Component
public class TagDTO {
	private String tagName;
    private String tagType;
    
    // Constructors
    public TagDTO() {}
    
    public TagDTO(String tagName, String tagType) {
        this.tagName = tagName;
        this.tagType = tagType;
    }

    // Getters and Setters
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    @Override
    public String toString() {
        return "TagDTO [tagName=" + tagName + ", tagType=" + tagType + "]";
    }
}
