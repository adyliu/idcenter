/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sohu.idcenter;


/**
 * from https://github.com/twitter/snowflake/blob/master/src/main/scala/com/twitter/service/snowflake/IdWorker.scala
 * @author adyliu (imxylz@gmail.com)
 * @since 1.0
 */
public class IdWorker {

    private final long workerId;
    private final long datacenterId;
    private final long idepoch = 1344322705519L;
    
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;
    private long sequence;

    //
    public IdWorker(long workerId, long datacenterId, long sequence) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException("workerId is illegal: " + workerId);
        }
        if (datacenterId < 0 || datacenterId > maxDatacenterId) {
            throw new IllegalArgumentException("datacenterId is illegal: " + workerId);
        }
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }
    
    public long getId() {
        long id = nextId();
        return id;
    }

    private synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        long id = ((timestamp - idepoch) << timestampLeftShift)//
                | (datacenterId << datacenterIdShift)//
                | (workerId << workerIdShift)//
                | sequence;
        return id;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

}
