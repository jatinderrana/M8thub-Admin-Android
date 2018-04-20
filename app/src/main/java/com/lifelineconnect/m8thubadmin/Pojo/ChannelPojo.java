package com.lifelineconnect.m8thubadmin.Pojo;

/**
 * Created by rana on 11/21/2017.
 */
public class ChannelPojo
{
    private String membership_fee;

    private String membership;

    private String description;

    private String name;

    private String privacy;

    private String rating;

    private String currency;
    private int likeCount, shareCount, subsCount;

    private String partnership, cover_image, avatar, id;

    public String getMembership_fee ()
    {
        return membership_fee;
    }

    public void setMembership_fee (String membership_fee)
    {
        this.membership_fee = membership_fee;
    }

    public String getMembership ()
    {
        return membership;
    }

    public void setMembership (String membership)
    {
        this.membership = membership;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getPrivacy ()
    {
        return privacy;
    }

    public void setPrivacy (String privacy)
    {
        this.privacy = privacy;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    public String getCurrency ()
    {
        return currency;
    }

    public void setCurrency (String currency)
    {
        this.currency = currency;
    }

    public String getPartnership ()
    {
        return partnership;
    }

    public void setPartnership (String partnership)
    {
        this.partnership = partnership;
    }
    private String IS_Liked, IS_Subscribed;

    public void setIS_Liked(String IS_Liked) {
        this.IS_Liked = IS_Liked;
    }

    public String getIS_Liked() {
        return IS_Liked;
    }


    public void setIS_Subscribed(String IS_Subscribed) {
        this.IS_Subscribed = IS_Subscribed;
    }

    public String getIS_Subscribed() {
        return IS_Subscribed;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [membership_fee = "+membership_fee+", membership = "+membership+", description = "+description+", name = "+name+", privacy = "+privacy+", rating = "+rating+", currency = "+currency+", partnership = "+partnership+"]";
    }

    public void setCoverImage(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getAvatar(){
        return avatar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setlikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setSubsCount(Integer subsCount) {
        this.subsCount = subsCount;
    }

    public int getSubsCount() {
        return subsCount;
    }
}