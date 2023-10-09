package lektion09;

import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;

import java.awt.image.SampleModel;

public class SimpleMqttCallBack implements MqttCallback {
    int status = 0;

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String topic = "cmnd/grp7719/Power1";
        String res = new String(mqttMessage.getPayload());
        // res indeholder en m ling som et JSON-object
        // put real stuff here     < --------    !!!!!!!!!!

        JSONObject jsonObject = new JSONObject(res);

        double temp = jsonObject.getJSONObject("AM2301").getDouble("Temperature");
        double hum = jsonObject.getJSONObject("AM2301").getDouble("Humidity");
        System.out.println("Temperature: " + temp + " Humidity: " + hum);

        if (hum > 80) {
            publishMessage(MQTTprogram.sampleClient, "cmnd/grp7719/Power1", "1 ");
        } else {
            publishMessage(MQTTprogram.sampleClient, "cmnd/grp7719/Power1", "0");
        }

        System.out.println(res);






    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
    }




    public static void publishMessage(MqttClient sampleClient, String topicsend, String content) throws MqttPersistenceException, MqttException, InterruptedException {
        // Laver en publish p  sampleClient med topic topicsend og indhold content.
        MqttMessage message = new MqttMessage();
        message.setPayload(content.getBytes());
        System.out.println(content.getBytes());
        sampleClient.publish(topicsend, message);
        System.out.println("Message published");

    }
}