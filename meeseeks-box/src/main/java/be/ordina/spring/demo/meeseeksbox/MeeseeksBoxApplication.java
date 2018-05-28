package be.ordina.spring.demo.meeseeksbox;

import be.ordina.spring.demo.GlipGlop;
import be.ordina.spring.demo.RickAndMortyQuote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.cloudfoundry.client.CloudFoundryClient;
import org.cloudfoundry.doppler.DopplerClient;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.cloudfoundry.operations.applications.GetApplicationRequest;
import org.cloudfoundry.operations.applications.ScaleApplicationRequest;
import org.cloudfoundry.operations.applications.StartApplicationRequest;
import org.cloudfoundry.reactor.ConnectionContext;
import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.cloudfoundry.reactor.doppler.ReactorDopplerClient;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;
import org.cloudfoundry.reactor.uaa.ReactorUaaClient;
import org.cloudfoundry.uaa.UaaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableBinding(OutputChannels.class)
public class MeeseeksBoxApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(MeeseeksBoxApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("OPTIONS", "POST", "DELETE");
	}

	@Configuration
	@Profile("cloud")
	@EnableConfigurationProperties(CloudFoundryClientConfigProperties.class)
	static class CloudFoundryClientConfiguration {

		private final CloudFoundryClientConfigProperties properties;

		CloudFoundryClientConfiguration(CloudFoundryClientConfigProperties properties) {
			this.properties = properties;
		}

		@Bean
		public ConnectionContext cloudFoundryConnectionContext() {
			return DefaultConnectionContext.builder()
				.apiHost(properties.getApiHost())
				.skipSslValidation(properties.isSkipSslValidation())
				.build();
		}

		@Bean
		public CloudFoundryClient cloudFoundryClient(ConnectionContext context, TokenProvider tokenProvider) {
			return ReactorCloudFoundryClient.builder()
				.tokenProvider(tokenProvider)
				.connectionContext(context)
				.build();
		}

		@Bean
		public UaaClient uaaClient(ConnectionContext context, TokenProvider tokenProvider) {
			return ReactorUaaClient.builder()
				.tokenProvider(tokenProvider)
				.connectionContext(context)
				.build();
		}

		@Bean
		@Autowired
		public DopplerClient dopplerClient(ConnectionContext context, TokenProvider tokenProvider) {
			return ReactorDopplerClient.builder()
				.tokenProvider(tokenProvider)
				.connectionContext(context)
				.build();
		}

		@Bean
		public CloudFoundryOperations cloudFoundryOperations(CloudFoundryClient cfClient, UaaClient uaaClient,
			DopplerClient dopplerClient) {
			return DefaultCloudFoundryOperations.builder()
				.cloudFoundryClient(cfClient)
				.dopplerClient(dopplerClient)
				.uaaClient(uaaClient)
				.organization(properties.getTargetOrg())
				.space(properties.getTargetSpace())
				.build();
		}

		@Bean
		@ConditionalOnProperty(name = { "username", "password" }, prefix = "cloudfoundry.client")
		public TokenProvider tokenProvider() {
			return PasswordGrantTokenProvider.builder()
				.username(properties.getUsername())
				.password(properties.getPassword())
				.build();
		}
	}

	@Slf4j
	@RestController
	@Profile("cloud")
	@RequestMapping("/")
	@AllArgsConstructor
	static class MeeseeksBoxController {

		private static final String MR_MEESEEKS_APP_NAME = "meeseeks";

		private final CloudFoundryOperations cloudFoundryOperations;
		private final OutputChannels outputChannels;

		@PostMapping
		public void spawnMrMeeseeks() {
			log.info("Checking whether Mr Meeseeks can be scaled...");

			this.outputChannels.microverse().send(MessageBuilder
				.withPayload(new GlipGlop(RickAndMortyQuote.MR_MEESEEKS_SPAWN, "0"))
				.build());

			this.cloudFoundryOperations.applications()
				.get(GetApplicationRequest.builder().name(MR_MEESEEKS_APP_NAME).build())
				.subscribe(response -> {
					int currentInstances = response.getInstances();
					log.info("Mr Meeseeks is running [{}] instances", currentInstances);
					int instanceCount = Math.min(3, currentInstances + 1);
					this.cloudFoundryOperations.applications()
						.scale(ScaleApplicationRequest.builder()
							.name(MR_MEESEEKS_APP_NAME)
							.instances(instanceCount)
							.build()).subscribe((scaleResponse) -> {
						if (!"started".equalsIgnoreCase(response.getRequestedState())) {
							log.info("Starting Mr Meeseeks", currentInstances);
							this.cloudFoundryOperations.applications()
								.start(StartApplicationRequest.builder()
									.name(MR_MEESEEKS_APP_NAME)
									.build()).subscribe();
						}
					});
				}, throwable -> log.error("Could not find Meeseeks application", throwable));
		}

		@DeleteMapping
		public void killMeeseekses() {
			log.info("Checking whether Mr Meeseeks can be terminated...");

			this.cloudFoundryOperations.applications()
				.get(GetApplicationRequest.builder().name(MR_MEESEEKS_APP_NAME).build())
				.subscribe(response -> {
					log.info("Scaling Mr Meeseeks to 0 instances...");
					this.cloudFoundryOperations.applications()
						.scale(ScaleApplicationRequest.builder()
							.instances(0)
							.name(MR_MEESEEKS_APP_NAME)
							.build()).subscribe();
				}, throwable -> log.error("Could not terminate Mr Meeseeks", throwable));
		}
	}

	@Getter
	@Setter
	@ConfigurationProperties(prefix = "cloudfoundry.client")
	static class CloudFoundryClientConfigProperties {

		private String apiHost;

		private String clientId;

		private String clientSecret = "";

		private String username;

		private String password;

		private boolean skipSslValidation = false;

		private String targetOrg;

		private String targetSpace;
	}
}
