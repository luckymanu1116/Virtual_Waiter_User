package com.example.virtualwaiteruser;

public class FinalCartDetails
{
    public String name,quant;
    public Long cost;


    public FinalCartDetails(String name,String quant, Long cost)
    {
        this.cost=cost;
        this.name=name;
        this.quant=quant;
    }

    public  String  getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
    public  String  getQuant() {

        return quant;
    }
    public void setQuant(String quant) {

        this.quant = quant;
    }

    public Long getCost() {

        return cost;
    }

    public void setCost(Long cost) {

        this.cost = cost;
    }

}
