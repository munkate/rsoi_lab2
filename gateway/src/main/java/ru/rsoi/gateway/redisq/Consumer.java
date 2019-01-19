package ru.rsoi.gateway.redisq;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ru.rsoi.gateway.client.DeliveryFullInformation;
import ru.rsoi.gateway.client.DeliveryFullInformationImpl;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
    protected Jedis queue = null;
    Thread thread;
    DeliveryFullInformationImpl service;

    public Consumer(Jedis queue) {
        this.queue = queue;
        this.thread = new Thread(this);
    }
    public void start()
    {
        this.thread.start();
    }

    public void run() {
        while(queue.exists("queue")) {
                String url = queue.rpop("queue");
                System.out.println("Consumed: "+url);
                try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                    HttpDelete delDelete = new HttpDelete(url);
                    try (CloseableHttpResponse httpResponse = httpClient.execute(delDelete)) {
                    }catch (RuntimeException e){
                        queue.lpush("queue",url);
                    }
                } catch (IOException e) {
                    queue.lpush("queue",url);
                    try {
                        thread.sleep(4000);
                    } catch (InterruptedException e1) {
                    }

                }
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    thread.stop();
                }
            }
        }
    }


