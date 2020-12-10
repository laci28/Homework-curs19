package ro.fasttrackit.curs19.homework.budgetapp;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    Data data = new Data();

    @GetMapping("/")
    TransactionService listAll() {
        return data.transactions;
    }

    @GetMapping
    List<Transaction> getTransactionByMinMaxAmount(@RequestParam(required = false) Type type,
                                                   @RequestParam("minAmount") double minAmount,
                                                   @RequestParam("maxAmount") double maxAmount) {
        return data.transactions.getTransactionByMinMaxAmount(type, minAmount, maxAmount);
    }

    @GetMapping("{transactionId}")
    Transaction getTransaction(@PathVariable int transactionId) {
        return data.transactions.getTransaction(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction id " + transactionId + " does not exists"));
    }

    @PostMapping
    Transaction addTransaction(@RequestBody Transaction transaction) {
        return data.transactions.addTransaction(transaction);
    }

    @PutMapping("{transactionId}")
    Transaction replaceTransaction(@PathVariable int transactionId, @RequestBody Transaction transaction) {
        return data.transactions.replaceTransaction(transactionId, transaction);
    }

    @PatchMapping("{transactionId}")
    Transaction updateCountry(@PathVariable int transactionId, @RequestBody Transaction transaction) {
        return data.transactions.updateTransaction(transactionId, transaction);
    }

    @DeleteMapping("{transactionId}")
    Transaction deleteTransaction(@PathVariable int transactionId) {
        return data.transactions.deleteTransaction(transactionId);
    }

    @GetMapping("/reports/type")
    Map<Type, List<Transaction>> mapFromTypeToAmount() {
        return data.transactions.mapFromTypeToAmount();
    }

    @GetMapping("/reports/products")
    Map<String, List<Transaction>> mapFromProductToAmount() {
        return data.transactions.mapFromProductToAmount();
    }
}
