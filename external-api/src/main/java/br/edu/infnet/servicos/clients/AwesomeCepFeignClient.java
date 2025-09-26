package br.edu.infnet.servicos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "awesomeCepClient", url = "https://cep.awesomeapi.com.br")
public interface AwesomeCepFeignClient {
    
    @GetMapping("/json/{cep}")
    AwesomeCepResponse consultarCep(@PathVariable("cep") String cep);
    
    public class AwesomeCepResponse {
        private String cep;
        private String address;
        private String state;
        private String district;
        private String lat;
        private String lng;
        private String city;

        // Construtores
        public AwesomeCepResponse() {
        }

        // Getters e Setters
        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        // Métodos equals, hashCode e toString
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AwesomeCepResponse that = (AwesomeCepResponse) o;

            return cep != null ? cep.equals(that.cep) : that.cep == null;
        }

        @Override
        public int hashCode() {
            return cep != null ? cep.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "AwesomeCepResponse{" +
                    "cep='" + cep + '\'' +
                    ", address='" + address + '\'' +
                    ", state='" + state + '\'' +
                    ", district='" + district + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }
}