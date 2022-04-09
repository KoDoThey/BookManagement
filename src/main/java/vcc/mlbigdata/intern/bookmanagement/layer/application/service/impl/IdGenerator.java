package vcc.mlbigdata.intern.bookmanagement.layer.application.service.impl;

public class IdGenerator {
    private final static long START_STAMP = 1480166465631L;

    /**
     * The number of bits occupied by each part
     */
    private final static long MACHINE_BIT = 10;
    private final static long SEQUENCE_BIT = 12;

    /**
     * Maximum value for each part
     */
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE_NUM = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * displacement of each part to the left
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long TIMESTAMP_LEFT = MACHINE_LEFT + MACHINE_BIT;

    private long machineId;
    private long sequence = 0L;
    private long lastStamp = -1L;

    public IdGenerator(long machineId) {
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.machineId = machineId;
    }

    /**
     * Generate the next ID
     */
    public synchronized long nextId() {
        long currentStamp = getNewTimestamp();
        if (currentStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }
        if (currentStamp == lastStamp) {
            //Within the same millisecond, the serial number is automatically incremented
            sequence = (sequence + 1) & MAX_SEQUENCE_NUM;
            //The maximum number of sequences in the same millisecond has been reached
            if (sequence == 0L) {
                currentStamp = getNextMill();
            }
        } else {
            //In different milliseconds, the sequence number is set to 0
            sequence = 0L;
        }

        lastStamp = currentStamp;

        return (currentStamp - START_STAMP) << TIMESTAMP_LEFT   // Timestamp part
                | machineId << MACHINE_LEFT                     // MachineId part
                | sequence;                                     // Sequence part
    }

    private long getNextMill() {
        long mill = getNewTimestamp();
        while (mill <= lastStamp) {
            mill = getNewTimestamp();
        }
        return mill;
    }

    private long getNewTimestamp() {
        return System.currentTimeMillis();
    }

}
