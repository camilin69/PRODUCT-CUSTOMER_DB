package co.edu.uptc.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.edu.uptc.entities.Consumer;

public class ConsumerHandler {
    List<Consumer> consumers;

    public ConsumerHandler(){
        consumers = new ArrayList<>();
    }

    public Consumer findConsumer(int id){
        Optional<Consumer> consumer = consumers.stream().filter(c -> c.getId() == id).findFirst();
        return consumer.orElse(null);
    }

    public boolean addConsumer(int id, String name, int age, String municipio, short status, String email){
        if(findConsumer(id) == null){
            Consumer consumer = new Consumer(id, name, age, municipio, status, email);
            consumers.add(consumer);
            return true;
        }
        return false;
    }

    public boolean removeConsumer(int id){
        Consumer consumer = findConsumer(id);
        if(consumer != null){
            consumers.remove(consumer);
            return true;
        }
        return false;
    }
}
