package ro.fasttrackit.curs19.homework.budgetapp;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final List<Transaction> transactionList = new ArrayList<>();

    public TransactionService(Collection<Transaction> transaction) {
        this.transactionList.addAll(transaction);
    }

    public List<Transaction> getTransactionByMinMaxAmount(Type type, double minAmount, double maxAmount) {
        if (type != null && minAmount != 0 && maxAmount != 0) {
            return transactionList.stream()
                    .filter(transaction -> transaction.getType().equals(type))
                    .filter(transaction -> transaction.getAmount() > minAmount)
                    .filter(transaction -> transaction.getAmount() < maxAmount)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>(transactionList);
        }
    }

    public Optional<Transaction> getTransaction(int tranactionId) {
        return transactionList.stream()
                .filter(transaction -> transaction.getId() == tranactionId)
                .findFirst();
    }

    public Transaction addTransaction(Transaction transaction) {
        transaction.setId(calculateId());
        transactionList.add(transaction);
        return transaction;
    }

    private int calculateId() {
        return transactionList.stream()
                .mapToInt(Transaction::getId)
                .max()
                .orElse(0) + 1;
    }

    public Transaction replaceTransaction(int transactionId, Transaction transaction) {
        transaction.setId(transactionId);
        deleteTransaction(transactionId);
        addTransaction(transaction);
        return transaction;
    }

    public Transaction deleteTransaction(int transactionId) {
        Transaction transaction = getOrThrow(transactionId);
        transactionList.remove(transaction);
        return transaction;
    }

    private Transaction getOrThrow(int transactionId) {
        return getTransaction(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction with id " + transactionId + " does not exist"));
    }

    public Transaction updateTransaction(int transactionId, Transaction newTransaction) {
        Transaction oldTransaction = getOrThrow(transactionId);
        String product = oldTransaction.getProduct();
        Type type = oldTransaction.getType();
        double amount = oldTransaction.getAmount();

        if (newTransaction.getProduct() != null) {
            product = newTransaction.getProduct();
        }
        if (newTransaction.getType() != null) {
            type = newTransaction.getType();
        }
        if (newTransaction.getAmount() != 0) {
            amount = newTransaction.getAmount();
        }

        return replaceTransaction(transactionId,
                new Transaction(oldTransaction.getId(),
                        product,
                        type,
                        amount
                ));
    }

    public Map<Type, List<Transaction>> mapFromTypeToAmount() {
        return transactionList.stream()
                .collect(Collectors.groupingBy(Transaction::getType));
    }

    public Map<String, List<Transaction>> mapFromProductToAmount() {
        return transactionList.stream()
                .collect(Collectors.groupingBy(Transaction::getProduct));
    }
}
