package com.example.majji;


public class MemberInfo {
    public String number="";
    public String name="";
    public String major="";

    public MemberInfo(){

    }

    public MemberInfo(String number, String name, String major){
        this.major = major;
        this.name = name;
        this.number = number;
    }

    public String getMajor(){
        return this.major;
    }
    public void setMajor(String major){
        this.major = major;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getNumber(){
        return this.number;
    }
    public void setNumber(String number){
        this.number = number;
    }


}
