import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;

/**
 * Created by root on 2017/3/27.
 */
public class Producer {

    public static void main(String[] args){
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("192.168.0.135:9876");
        try {
            producer.start();
            Message msg = new Message("deploy",
                    "orderLoseEfficacy",
                    "1",
                    "8152330892164581".getBytes());
            msg.setDelayTimeLevel(2);
            SendResult result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());

//            msg = new Message("deploy",
//                    "orderLoseEfficacy",
//                    "2",
//                    "Just for test.".getBytes());
//            msg.setDelayTimeLevel(3);
//
//            result = producer.send(msg);
//            System.out.println("id:" + result.getMsgId() +
//                    " result:" + result.getSendStatus());
//
//            msg = new Message("deploy",
//                    "orderLoseEfficacy",
//                    "1",
//                    "Just for test.".getBytes());
//            msg.setDelayTimeLevel(4);
//
//            result = producer.send(msg);
//            System.out.println("id:" + result.getMsgId() +
//                    " result:" + result.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            producer.shutdown();
        }
    }
}
