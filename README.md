# Getting Started
```
./gradlew clean build
java -jar build/libs/kakao-coupon-0.0.1-SNAPSHOT.jar
```

# Tech stack
- JDK 11
- spring boot 2
- JPA
- H2DB
- Junit 5

# Schema
- table name: coupon

|field|type|key|
|---|---|---|
|ID|INTEGER|PRI|
|DISCOUNT_AMOUNT|INTEGER| |
|STATUS|VARCHAR||
|USABLE_FROM|TIMESTAMP||
|USABLE_UNTIL|TIMESTAMP||
|USE_MIN_AMOUNT|INTEGER||

# 요구사항
### 쿠폰목록조회API

- 상품 금액을 입력 받아 사용 가능한 쿠폰 목록을 조회합니다. 쿠폰 만료 일시가 가까운 순으로 조회됩니다.
- 쿠폰 만료 일시가 같은 경우 할인 금액이 큰 순으로 조회됩니다.

### 쿠폰사용처리API

- 쿠폰 id와 상품 금액을 입력 받고 쿠폰을 사용 처리 합니다. (status를 USED로 업데 이트)
- 해당 쿠폰이 사용 가능한 쿠폰이 아닌 경우 오류를 응답합니다.
- 사용 처리의 응답은 상품 금액, 결제 금액(상품 금액에서 할인 금액을 차감하고 남은 금액), 실제 할인 금액(쿠폰의 할인 금액 중 사용된 금액)을 포함합니다.

## 진행시 고려사항
- PDF에 함께 첨부되어온 mock 데이터를 복사해서 사용
  - 6월 31일 데이터가 있는데 일부러 페이크데이터를 넣은건지 고민했지만 30일로 변경해서 사용
- 개인적으로 가장 고민한 부분은 사용가능한 쿠분의 유효성체크부분인데 여러가지방법으로 구현할 수 있었음
  - 가장 간단하게 그냥 service의 메서드 내에서 if 분기처리
  - 그나마 좀 더 나은 private 메서드 분리(이후 진행은 분기)
  - 하지만 위와 같은 방안으로 작성하면 service의 코드가 증대하고, 각 조건에 대한 테스트가 매우 힘들어짐(if분기로나뉘어지면 뒤에있는 조건을 테스트하기위해서는 항상 앞에있는 조건을 true가 되도록 신경써야함. 핵심 테스트코드외의 부분에 신경을 써야함)
  - 인터페이스로 분리하고, 각 조건들에 대해서 그 구현체를 만드려고했지만 coupon 클래스 외의 파라미터(amount, dateTime)에 대한 인터페이스 추출이 힘들었음
  - dateTime의 경우 파라미터로 안받고 내부에서 `LocalDateTime.now()` 를 호출할수도있으나 이러면 테스트가 힘들어짐
  - 어떤식으로 추상화를 할지 고민하다가 `ValidateCondition` 이라는 파라미터클래스를 만들어 각 조건에 대한 추상화를 만들어 인터페이스 추출
  - 총 3개의 쿠폰 유효성중 상태가 `NORMAL` 인것을 체크하는 조건은 DB 조회시 WHERE 조건을 넣을수도있었으나 이럴경우 유효성체크로직이 일부는 애플리케이션코드에 들어가고, 일부는 DB로 들어가 분리되기때문에 일단은 같이 애플리케이션에 있는게 맞다고 판단함
  - `CouponValidator` 인터페이스 추출후 각 조건에 대한 테스트가 매우 쉬워짐
  