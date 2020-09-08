
package com.aws.sns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.aws.sns.config.AwsSNSClient;

@SpringBootApplication(exclude = { ContextStackAutoConfiguration.class, ContextRegionProviderAutoConfiguration.class })
@RestController
public class SpringbootAwsSnsApplication {

	@Autowired
	private AwsSNSClient awsSnsClient;

	@Value("${topic.arn}")
	private String TOPIC_ARN;

	@GetMapping("/addSubscriptio/{emailID}")
	public String addSubscription(@PathVariable String emailID) {
		System.out.println("Emailid " + emailID);
		System.out.println("TOPIC_ARN = " + TOPIC_ARN);
		SubscribeRequest snsRequest = new SubscribeRequest(TOPIC_ARN, "email", emailID);
		awsSnsClient.getSnsClient().subscribe(snsRequest);
		return "Subscription request pending. To confirm the subscription check the email!! " + emailID;
	}

	@GetMapping("/sendNotification")
	public String publishMessageToTopic() {
		PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, buildEmailBody(),
				"Notification:Network connectivity issue !");
		awsSnsClient.getSnsClient().publish(publishRequest);
		return "Notification sent successfully.. !";

	}

	private String buildEmailBody() {
		return "Dear Employees" + "\n" + "\n" + "Connection down in Banglure office !!"
				+ "\n We will send Notification once resolved !!";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAwsSnsApplication.class, args);
	}

}
