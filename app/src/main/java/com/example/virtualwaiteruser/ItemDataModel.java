package com.example.virtualwaiteruser;

public class ItemDataModel
{
    public String ItemName,tableno;
    public Long cost;
    public String quant;

    public ItemDataModel(String ItemName, Long cost, String quant,String tableno)
    {
        this.ItemName=ItemName;
        this.cost=cost;
        this.quant=quant;
        this.tableno=tableno;

    }
}
