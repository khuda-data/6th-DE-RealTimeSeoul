package com.example;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.json.JSONObject;
import org.json.JSONArray;

public class CityDataStreams {

    public static void main(String[] args) {
        // Kafka Streams 설정
        var props = new java.util.Properties();
        props.put("application.id", "city-data-processor");
        props.put("bootstrap.servers", "khuda-kafka:9092");

        // StreamsBuilder 생성
        StreamsBuilder builder = new StreamsBuilder();

        // 입력 토픽에서 데이터 수신
        KStream<String, String> sourceStream = builder.stream("all.region");

        // 데이터 처리 및 토픽 분배
        processAndRouteData(sourceStream);

        // Kafka Streams 시작
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // 종료 시 정리 작업
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    // 데이터 처리 및 카테고리별로 토픽 전송
    private static void processAndRouteData(KStream<String, String> sourceStream) {
        // 핫스팟 데이터
        sourceStream.mapValues(CityDataStreams::extractHotspotData)
                .to("hotspot.region", Produced.with(Serdes.String(), Serdes.String()));

        // 도로 데이터
        sourceStream.mapValues(CityDataStreams::extractRoadData)
                .to("road.region", Produced.with(Serdes.String(), Serdes.String()));

        // 대중교통 데이터
        sourceStream.mapValues(CityDataStreams::extractTransitData)
                .to("transit.region", Produced.with(Serdes.String(), Serdes.String()));

        // 날씨 데이터
        sourceStream.mapValues(CityDataStreams::extractWeatherData)
                .to("weather.region", Produced.with(Serdes.String(), Serdes.String()));

        // 문화 행사 데이터
        sourceStream.mapValues(CityDataStreams::extractEventData)
                .to("event.region", Produced.with(Serdes.String(), Serdes.String()));

        // 업종 데이터
        sourceStream.mapValues(CityDataStreams::extractCommercialData)
                .to("commercial.region", Produced.with(Serdes.String(), Serdes.String()));
    }

    // 핫스팟 데이터 처리
    private static String extractHotspotData(String rawData) {
        JSONObject cityData = new JSONObject(rawData);
        JSONArray livePopulationStatus = cityData.getJSONArray("LIVE_PPLTN_STTS");
        JSONObject result = new JSONObject();
        result.put("LIVE_PPLTN_STTS", livePopulationStatus);
        return result.toString();
    }

    // 도로 데이터 처리
    private static String extractRoadData(String rawData) {
        JSONObject cityData = new JSONObject(rawData);
        JSONObject result = new JSONObject();
        result.put("ROAD_TRAFFIC_STTS", cityData.getJSONObject("ROAD_TRAFFIC_STTS"));
        result.put("ACDNT_CNTRL_STTS", cityData.getJSONArray("ACDNT_CNTRL_STTS"));
        result.put("CHARGER_STTS", cityData.getJSONArray("CHARGER_STTS"));
        return result.toString();
    }

    // 대중교통 데이터 처리
    private static String extractTransitData(String rawData) {
        JSONObject cityData = new JSONObject(rawData);
        JSONObject result = new JSONObject();
        result.put("SUB_STTS", cityData.getJSONArray("SUB_STTS"));
        result.put("BUS_STN_STTS", cityData.getJSONArray("BUS_STN_STTS"));
        result.put("SBIKE_STTS", cityData.getJSONArray("SBIKE_STTS"));
        return result.toString();
    }

    // 날씨 데이터 처리
    private static String extractWeatherData(String rawData) {
        JSONObject cityData = new JSONObject(rawData);
        JSONArray weatherStatus = cityData.getJSONArray("WEATHER_STTS");
        JSONObject result = new JSONObject();
        result.put("WEATHER_STTS", weatherStatus);
        return result.toString();
    }

    // 문화 행사 데이터 처리
    private static String extractEventData(String rawData) {
        JSONObject cityData = new JSONObject(rawData);
        JSONArray eventStatus = cityData.getJSONArray("EVENT_STTS");
        JSONObject result = new JSONObject();
        result.put("EVENT_STTS", eventStatus);
        return result.toString();
    }

    // 업종 데이터 처리
    private static String extractCommercialData(String rawData) {
        JSONObject cityData = new JSONObject(rawData);
        JSONObject liveCommercialStatus = cityData.getJSONObject("LIVE_CMRCL_STTS");
        JSONObject result = new JSONObject();
        result.put("LIVE_CMRCL_STTS", liveCommercialStatus);
        return result.toString();
    }
}