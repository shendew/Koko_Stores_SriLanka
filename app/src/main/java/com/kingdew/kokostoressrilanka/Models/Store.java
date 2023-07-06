package com.kingdew.kokostoressrilanka.Models;

public class Store {

    String ID,ShopName,ShopAddress,ShopLogo,ShopLocation,ShopCategory,ShopTags,ShopContact,ShopWebsite,ShopDesc;

    public Store() {
    }

    public Store(String ID, String shopName, String shopAddress, String shopLogo, String shopLocation, String shopCategory, String shopTags, String shopContact, String shopWebsite, String shopDesc) {
        this.ID = ID;
        ShopName = shopName;
        ShopAddress = shopAddress;
        ShopLogo = shopLogo;
        ShopLocation = shopLocation;
        ShopCategory = shopCategory;
        ShopTags = shopTags;
        ShopContact = shopContact;
        ShopWebsite = shopWebsite;
        ShopDesc = shopDesc;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getShopAddress() {
        return ShopAddress;
    }

    public void setShopAddress(String shopAddress) {
        ShopAddress = shopAddress;
    }

    public String getShopLogo() {
        return ShopLogo;
    }

    public void setShopLogo(String shopLogo) {
        ShopLogo = shopLogo;
    }

    public String getShopLocation() {
        return ShopLocation;
    }

    public void setShopLocation(String shopLocation) {
        ShopLocation = shopLocation;
    }

    public String getShopCategory() {
        return ShopCategory;
    }

    public void setShopCategory(String shopCategory) {
        ShopCategory = shopCategory;
    }

    public String getShopTags() {
        return ShopTags;
    }

    public void setShopTags(String shopTags) {
        ShopTags = shopTags;
    }

    public String getShopContact() {
        return ShopContact;
    }

    public void setShopContact(String shopContact) {
        ShopContact = shopContact;
    }

    public String getShopWebsite() {
        return ShopWebsite;
    }

    public void setShopWebsite(String shopWebsite) {
        ShopWebsite = shopWebsite;
    }

    public String getShopDesc() {
        return ShopDesc;
    }

    public void setShopDesc(String shopDesc) {
        ShopDesc = shopDesc;
    }
}
