package com.example.majji;



public class ProductInfo {
    int key;
    private String src;
    private String href;
    private int price;
    private Type type;
    private Sex sex;

    /*enum Type{
        TSHIRT,
        HOODIE,
        SWEATSHIRT
    }

    enum Site{
        SPAO,
        BAON
    }

    enum Sex{
        MAN,
        WOMAN
    }*/
    public ProductInfo(int key,Type type, Sex sex, String href, String src, int price){
        this.key = key;
        this.href = href;
        this.src = src;
        this.price = price;
        this.type = type;
        this.sex= sex;
    }

    public ProductInfo() {
        // all nulll ?
    }


    public int getKey() {
        return this.key;
    }

    public int getPrice() {
        return this.price;
    }

    public Type getType() {
        return this.type;
    }

    public String getHref() {
        return this.href;
    }

    public String getSrc() {
        return this.src;
    }

    public Sex getSex(){ return this.sex;}

    public void setHref(String href) {
        this.href = href;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setSex(Sex sex) {this.sex=sex;}

    public void setAll(String key, String type, String sex, String href, String src, String price) {

        this.key = Integer.parseInt(key);
        this.href = href;
        this.src = src;
        this.price = Integer.parseInt(price);
        switch (type){
            case "HOODIE":
                this.type = Type.HOODIE;
            case "SWEATSHIRT":
                this.type = Type.SWEATSHIRT;
            case "TSHIRT":
                this.type = Type.TSHIRT;
        }
        switch (sex){
            case "MAN":
                this.sex = Sex.MAN;
            case "WOMAN":
                this.sex = Sex.WOMAN;
        }
    }
}
