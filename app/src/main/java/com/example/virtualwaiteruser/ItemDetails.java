package com.example.virtualwaiteruser;

public class ItemDetails
{
    public String name;
    public Long cost;

    public ItemDetails() {
        // This is default constructor.
    }
    public ItemDetails(Long cost, String name)
    {
        this.cost=cost;
        this.name=name;
    }

    public  String  getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Long getCost() {

        return cost;
    }

    public void setCost(Long cost) {

        this.cost = cost;
    }

}
