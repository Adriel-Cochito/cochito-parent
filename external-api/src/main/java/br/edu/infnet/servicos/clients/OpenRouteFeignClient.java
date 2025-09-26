package br.edu.infnet.servicos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openRouteClient", url = "https://api.openrouteservice.org")
public interface OpenRouteFeignClient {

	@GetMapping("/v2/directions/driving-car")
	OpenRouteResponse calcularRota(@RequestParam("api_key") String apiKey, @RequestParam("start") String origem,
			@RequestParam("end") String destino);

	public class OpenRouteResponse {
		private List<Feature> features;

		public List<Feature> getFeatures() {
			return features;
		}

		public void setFeatures(List<Feature> features) {
			this.features = features;
		}

		public static class Feature {
			private Properties properties;

			public Properties getProperties() {
				return properties;
			}

			public void setProperties(Properties properties) {
				this.properties = properties;
			}
		}

		public static class Properties {
			private Summary summary;

			public Summary getSummary() {
				return summary;
			}

			public void setSummary(Summary summary) {
				this.summary = summary;
			}
		}

		public static class Summary {
			private double distance;
			private double duration;

			public double getDistance() {
				return distance;
			}

			public void setDistance(double distance) {
				this.distance = distance;
			}

			public double getDuration() {
				return duration;
			}

			public void setDuration(double duration) {
				this.duration = duration;
			}
		}
	}
}