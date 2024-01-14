package com.dougfsilva.iotnizer.config;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import com.dougfsilva.iotnizer.model.Email;
import com.dougfsilva.iotnizer.model.ProfileType;
import com.dougfsilva.iotnizer.mqtt.MqttParams;
import com.dougfsilva.iotnizer.mqtt.MqttSystemClientSubscriber;
import com.dougfsilva.iotnizer.repository.UserRepository;
import com.dougfsilva.iotnizer.service.user.CreateUser;

@Configuration
public class InitialConfiguration implements ApplicationRunner{
	
	@Value("${user.admin.email}")
	private String adminEmail;
	
	@Value("${user.admin.password}")
	private String adminPassoword;
	
	private final CreateUser createUser;
	
	private final UserRepository userRepository;
	
	private final MqttParams mqtt;
	
	private final MqttSystemClientSubscriber systemClientSubscriber;


	public InitialConfiguration(CreateUser createUser, UserRepository userRepository, MqttParams mqtt,
			MqttSystemClientSubscriber systemClientSubscriber) {
		this.createUser = createUser;
		this.userRepository = userRepository;
		this.mqtt = mqtt;
		this.systemClientSubscriber = systemClientSubscriber;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		createAdmin();
		createMqttSystemSubscriber();
		systemClientSubscriber.connect();
	}
	
	private void createAdmin() {
		if(userRepository.findByEmail(new Email(adminEmail)).isEmpty()){
			createUser.create(adminEmail, "admin", adminEmail, ProfileType.ADMIN);
		}
	}
	
	private void createMqttSystemSubscriber() {
		String rolename = "role_system";
		String username = mqtt.getSystemSubscriberUsername();
		String password = mqtt.getSystemSubscriberPassword();
		String topic = mqtt.getSystemsubscriberTopic();
		try {
			Process createClient = Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("createClient %s", username));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(createClient.getOutputStream()));
			out.write(password + "\n");
			out.flush();
			out.write(password + "\n");
			out.flush();
			out.close();
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("createRole %s", rolename));
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("addClientRole %s %s", username, rolename));
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("addRoleACL %s subscribeLiteral %s allow", rolename, topic));
			Runtime.getRuntime().exec(mqtt.getDynSecUriCommand() + String.format("addRoleACL %s subscribePattern %s allow", rolename, topic));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
