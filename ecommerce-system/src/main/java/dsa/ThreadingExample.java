package dsa;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


class Order {
    private String orderId;
    private String productId;
    private int quantity;
}

class OrderQueue {
    private final Queue<Order> orders = new LinkedList<>();
    private final int MAX_CAPACITY = 10;
}

class Inventory {
    private final Map<String, Integer> inventory = new HashMap<>();
}

class OrderProcessor implements Runnable {
    private final OrderQueue orderQueue;
    private final Inventory inventory;

    OrderProcessor(OrderQueue orderQueue, Inventory inventory) {
        this.orderQueue = orderQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

    }
}

public class ThreadingExample {

}
