package ro.fasttrackit.curs19.homework.budgetapp;

import java.util.ArrayList;
import java.util.List;

public class Data {
    TransactionService transactions = new TransactionService(getList());

    public List<Transaction> getList() {
        List<Transaction> list = new ArrayList<>();
        list.add(new Transaction(1, "Gold", Type.BUY, 144.5));
        list.add(new Transaction(2, "EUR", Type.BUY, 90.1));
        list.add(new Transaction(3, "Bitcoin", Type.BUY, 148.9));
        list.add(new Transaction(4, "Silver", Type.BUY, 78.15));
        list.add(new Transaction(5, "Gold", Type.SELL, 184.5));
        list.add(new Transaction(6, "USD", Type.BUY, 200.00));
        list.add(new Transaction(7, "EUR", Type.SELL, 44.55));
        return list;
    }
}
