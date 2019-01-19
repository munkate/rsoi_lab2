package ru.rsoi.gateway.redisq;

import redis.clients.jedis.Jedis;

public  class Producer {
    private Jedis bq = null;

    public void setBlockingQueue(Jedis bq) {
        this.bq = bq;
    }

    public Producer(Jedis queue) {
        this.setBlockingQueue(queue);
    }

    public void send(String massege) throws InterruptedException {
        bq.lpush("queue",massege);
    }

}

