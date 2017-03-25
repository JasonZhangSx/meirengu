import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by root on 2017/3/23.
 */
public class IdWorker {


    //开始该类生成ID的时间截，1288834974657 (Thu, 04 Nov 2010 01:42:54 GMT) 这一时刻到当前时间所经过的毫秒数，占 41 位（还有一位是符号位，永远为 0）。
    private final long startTime = 1490251041367L;
//    private final long startTime = 1490251041367L;
//    private static final long startTime = System.currentTimeMillis();

    //优惠券类别id所占的位数
    private long workerIdBits = 4L;


    //支持的最大优惠券类别id，结果是16,这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数（不信的话可以自己算一下，记住，计算机中存储一个数都是存储的补码，结果是负数要从补码得到原码）
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);


    //序列在id中占的位数
    private long sequenceBits = 12L;

    //机器id向左移12位
    private long workerIdLeftShift = sequenceBits;


    //时间截向左移4+12=16位
    private long timestampLeftShift = workerIdBits + workerIdLeftShift;

    //生成序列的掩码，这里为1111 1111 1111
    private long sequenceMask = -1 ^ (-1 << sequenceBits);

    private long workerId;


    //同一个时间截内生成的序列数，初始值是0，从0开始
    private long sequence = 0L;

    //上次生成id的时间截
    private long lastTimestamp = -1L;

    public IdWorker(long workerId){
        if(workerId < 0 || workerId > maxWorkerId){
            throw new IllegalArgumentException(
                    String.format("workerId[%d] is less than 0 or greater than maxWorkerId[%d].", workerId, maxWorkerId));
        }
        this.workerId = workerId;
    }

    //生成id
    public synchronized long nextId(){
        long timestamp = timeGen();
        if(timestamp < lastTimestamp){
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果是同一时间生成的，则自增
        if(timestamp == lastTimestamp){
            sequence = (sequence + 1) & sequenceMask;
            if(sequence == 0){
                //生成下一个毫秒级的序列
                timestamp = tilNextMillis();
                //序列从0开始
                sequence = 0L;
            }
        }else{
            //如果发现是下一个时间单位，则自增序列回0，重新自增
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        //看本文第二部分的结构图，移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - startTime) << timestampLeftShift)
                | (workerId << workerIdLeftShift)
                | sequence;
    }

    protected long tilNextMillis(){
        long timestamp = timeGen();
        if(timestamp <= lastTimestamp){
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen(){
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {

//        class IdServiceThread implements Runnable {
//            private Set<Long> set;
//            private IdWorker idWorker;
//
//            public IdServiceThread(Set<Long> set, IdWorker idWorker) {
//                this.set = set;
//                this.idWorker = idWorker;
//            }
//
//            @Override
//            public void run() {
//                while (true) {
//                    long id = idWorker.nextId();
//                    System.out.println("duplicate:" + id);
//                    if (!set.add(id)) {
//                        System.out.println("duplicate:" + id);
//                    }
//                }
//            }
//        }
//
//        Set<Long> set = new HashSet<Long>();
//        for(int i=0;i<100;i++){
//            Thread t1 = new Thread(new IdServiceThread(set, new IdWorker(1,1)));
//            t1.setDaemon(true);
//            t1.start();
//            try {
//                Thread.sleep(30000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        /** 自定义进制(0,1没有加入,容易与o,l混淆) */
                String[] r=new String[]{"q", "w", "e", "a", "s","d", "z", "x", "c",  "p",  "i", "k",  "m", "j", "u", "f", "r",  "v", "y",  "t", "n",  "b", "g", "h"};
        Date startTime = new Date();
        IdWorker idWorker = new IdWorker(1);
        Random random = new Random(47);
        StringBuffer bf = new StringBuffer();
        Set<String> strSet = new HashSet<String>();

        for (int i = 0;i<100000;i++) {
            long a = idWorker.nextId();
            bf.append(a);
            int k = random.nextInt(10);
            bf.replace(k,k+1,r[random.nextInt(24)]);
            strSet.add(bf.toString());
            bf.setLength(0);
        }
        Date endTime = new Date();
        long useTime = endTime.getTime() - startTime.getTime();
        for(String str : strSet) {
            System.out.println(str);
        }
        System.out.println(strSet.size());
        System.out.println(useTime);



    }

}
