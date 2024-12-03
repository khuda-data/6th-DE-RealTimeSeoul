# 실시간 서울시 도시데이터 처리 파이프라인 구축

## 프로젝트 목적

Apache Kafka를 이용하여 '서울 열린 데이터 광장'에서 제공하는 서울시 실시간 도시데이터 API를 활용해 본다.
Kafka Streams와 Kafka Connector를 이용해 적절한 소스에서 데이터를 수집하고, 토픽을 분기 및 적재하는 과제형 프로젝트를 진행한다.
<br><br>

## 관련 이론
![architecture](https://github.com/user-attachments/assets/053d988a-559a-4fca-a8fa-5dfcc7b84c3e)
1. Source Connector를 통해 원천 데이터 Kafka로 전 송
2. Kafka Streams를 통해 스트리밍 데이터 처리
3. Sink Connector를 통해 데이터 적재 <br>
Kafka Streams: 토픽에 적재된 데이터를 실시간 변환, 다른 토픽에 적재 <br>
Kafka Connect: 특정 작 업 형태를 템플릿화 <br>
• 프로듀서 역할을 하는 Source Connector <br>
• 컨슈머 역할을 하는 Sink Connector
<br>

## 프로젝트 방법
서울시에서 제공하는 서울 열린데이터 광장 AD 를 활용하여 핫플 혼잡도와 관련된 데이터를 실시간으로 수집하고, 이를 Apache Kafka 기반의 스트림 처리 아키텍처를 통해 분석 및 저장한다.
1. 데이터 수집 <br>
• 소스 데이터는 서울 열린데이터 광장 API를 활용한다. <br>
• HTTP Connector를 활용해 데이터 소스에서 all.region 토픽으로 produce하도록 구성했다.
2. 데이터 스트림 처리 <br>
• Kafka Streams 사용 <br>
• allregion 토픽에 저장된 데이터를 Kafka Streams를 활용하여 실시간으로 분기한다.
3. 데이터 저장 <br>
• Sink Connector 사용 <br>
• Kafka Sink Connector를 활용하여 처리된 데이터를 AWS S3 Object Storage에 저장한다.
4. 데이터 시각화 및 결과 도출 <br>
• 실시간 시각화 <br>
• Kafka Streams로 처리된 데이터를 기반으로 핫플레이스 혼잡도를 실시간으로 시각화했다. <br>
• 특정 지역의 실시간 현황을 지도 기반으로 표현하여 시각적 효과를 극대화하였다.
<br>

## 프로젝트 과정
File Source Connector와 HDFS Sink Connector에 이어, HTTP Connector를 활용해 데이터 소스에서 Extract 과정을 수행하고자 한다. 이후 Kafka Streams를 활용해 많은 데이터를 분기하고 적절한 토픽에 전달함으로써 Transform 과정을 수행한다. 마지막으로 S3 Sink Connector를 활용해 말단 토픽에 적재된 데이터를 S3 Bucket에 저장함으로써 Load 과정을 수행한다.

![image](https://github.com/user-attachments/assets/b3809587-15b8-4ca6-9206-2bee78ba3334)
1. 데이터 소스 정의 : 인구정보 , 도로정보 , 주차장 , 전기차충전소 , 대중교통 등의 수집해야 할 데이터 소스로 정의한다.
2. 데이터 수집 : 수집된 데이터를 통합적으로 관리하기 위해 Kafka Connect를 활용한다.
3. 데이터 처리 및 라우팅 : 수집된 데이터는 Kafka 내부에서 통합 관리되고 , 모든 데이터는 중심 노드로 연결된다. 세부 정보로도 구분되어 실시간 상태를 모니터링한다.
4. 데이터 저장 및 관리 : 정리된 데이터가 Amazon S3 에 저장된다.
<br>

![image](https://github.com/user-attachments/assets/99ff0c9c-53b3-43e2-bb9b-524fee9152a0)

최종적으로 서울 열린 데이터광장에서 제공하는 좌측 그림과 같이 서울시 내 핫스팟 지역 , 도로 정보를 시각화하여 한 눈에 알아보기 쉽도록 지도를 구성한다.
<br><br>

## 프로젝트 결과
본 프로젝트의 목표는 Kafka 를 기반으로 ' 서울시 실시간 도시데이터' API를 활용해 사용자에게 필요한 다양한 용도에 따라 세부 API 를 제공하는 것이다.
Kafka Streams를 활용해 API 로부터 응답받은 총 209 개의 인자를 핫스팟, 도로(주차장, 전기차 충전소 등), 도로 교통 사고, 대중교통(버스, 지하철 등), 업종 등 총 12 개의 토픽으로 분류했다. 이를 통해 특정 카테고리의 데이터가 필요한 사용자는 S3 Bucket에 접근하여 원하는 데이터를 손쉽게 활용할 수 있도록 구성했다.
<br><br>

## 참고문헌
• Apache Kafka 애플리케이션 프로그래밍 with JAVA, 최원영 <br>
• 서울시 실시간 도시데이터 매뉴얼
