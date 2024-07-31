package dsa;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.List;
import java.util.ArrayList;

public class LocksExample {
    // Transaction History class
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final List<String> transactions = new ArrayList<>();

    // Method to read the transaction history
    public List<String> getTransactions() {
        rwLock.readLock().lock();
        try {
            // Return a copy of the transactions list to avoid external modifications
            return new ArrayList<>(transactions);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    // Method to add a new transaction
    public void addTransaction(String transaction) {
        rwLock.writeLock().lock();
        try {
            transactions.add(transaction);
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
