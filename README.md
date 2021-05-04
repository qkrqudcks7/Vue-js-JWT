# Spring Boot + Vue.js 연습 (게시판)


## rest api , spring security jwt , vue.js , vuex 위주로 작성 
<hr/>

## :pushpin: 1. Spring security JWT(Json Web Token) 
토큰 기반 인증 시스템에서 주로 사용하는 JWT를 SpringSecurity 기반으로 로그인 처리를 했습니다.

### :question: 왜 토큰 기반인가?

#### [ 토큰 기반 인증 시스템 ]
인증받은 사용자들에게 토큰을 발급하고, 서버에 요청을 할 때 헤더에 토큰을 함께 보내도록 하여 유효성 검사를 한다.
이런 시스템은 더이상 사용자의 인증 정보를 서버에 유지하지 않고 클라이언트 측에서 들어오는 요청만으로 작업을 처리한다.
따라서 사용자의 로그인 여부에 신경쓰지 않고 쉽게 시스템을 확장할 수 있다.

![](https://blog.kakaocdn.net/dn/ogoAg/btqAriyT5sY/YYt2wkEz50kKN47mLwRDXK/img.png)

1. 사용자가 아이디 , 비밀번호로 로그인
2. 서버에서 해당 정보를 확인
3. 해당 정보가 있다면, 사용자에게 토큰을 발급
4. 클레이언트에서 전달받은 토큰을 저장하고, 서버에 요청을 할 때마다 해당 토큰을 서버에 함께 전달
5. 서버는 토큰을 검증하고, 요청에 응답

### :pushpin: 1. postgresql에 xlsx 파일 저장
> 빅데이터 엑셀 파일에 결측치를 제거하고 정제하는 과정을 가짐. 저장할 때 csv파일로 바꿔 저장함. db에 엑셀 연동 저장방식 중, csv 저장 방식을 사용하기 위함.

> 엑셀 항목 중 가격, 상품명 , 품질 등과 같은 필수적인 컬럼은 nullable=false , 그 외 나머지 값들은 nullable=true 지정.

> 맵핑하기 위한 각각의 Long 타입 id값 생성

> csv 저장 성공 후, spring boot와 연동

<pre>
<code>
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/--------
    driver-class-name: org.postgresql.Driver
    username: ------
    password: -----
 </code>
 </pre>
 <hr/>
 
 
 ### 📌 2. MVC 패턴
 
 > Repository - Service - domain - dto - controller 방식으로 구현
 
 <hr/>
 
 
 ### 3.1. 카테고리별 검색(Spring Data Jpa , Query Dsl)
![image](https://user-images.githubusercontent.com/66015002/107529789-96c09200-6bfe-11eb-9179-c0a4fe2a1d40.png)
 
 <pre>
<code>
Page<Main> findMainByKeyword2(String keyword,Pageable pageable,String priority);
 </code>
 </pre>
 > 위 코드와 같이 검색 단어인 keyword 변수 , 페이징 처리를 위한 pageable 변수 , 가격 품질 등의 카테고리를 뜻하는 priority 변수를 참조.
 
 <pre>
<code> 
NumberExpression<Integer> productRankPath = new CaseBuilder()
                .when(main2.productRank.eq("유기농")).then(1)
                .when(main2.productRank.eq("무농약")).then(2)
                .when(main2.productRank.eq("무항생제")).then(3)
                .when(main2.productRank.eq("상품")).then(4)
                .when(main2.productRank.eq("중품")).then(5)
                .when(main2.productRank.eq("M과")).then(6)
                .when(main2.productRank.eq("S과")).then(7)
                .when(main2.productRank.eq("1+등급")).then(8)
                .when(main2.productRank.eq("1등급")).then(9)
                .when(main2.productRank.eq("냉장")).then(10)
                .otherwise(11);
 </code>
 </pre>
 
 > 가격과 같은 우선순위는 orderby로 쉽게 정렬할 수 있다. 또한 ㄱ,ㄴ,ㄷ 순의 문자일 경우도 마찬가지이다. 하지만 위와 같은 카테고리일 경우 커스텀 정렬이 필요하다.
예를들어 NumberExpression 방법을 쓸 수 있다. new caseBuilder()를 통해 정렬의 우선순위를 내가 임의로 정할 수 있다. 
if else와 비슷하다고 볼 수 있다. 유기농일 경우 1순위 ... 냉장이면 10 순위와 같은 방식이다.


<pre>
<code> 
JPQLQuery<Main> query = from(main2).where(main2.day.eq(20191108)
                .and(main2.itemName.contains(keyword)));

        if(priority.equals("가격높은순")){
            query=query.orderBy(main2.price.desc());
        }
        if(priority.equals("가격낮은순")){
            query=query.orderBy(main2.price.asc());
        }
        if(priority.equals("품질높은순")){
            query=query.orderBy(productRankPath.asc());
        }
        if(priority.equals("품질낮은순")){
            query=query.orderBy(productRankPath.desc());
        }
        if(priority.equals("도매")) {
            query=query.orderBy(productClsRankPath.asc());
        }
        if(priority.equals("소매")) {
            query=query.orderBy(productClsRankPath.desc());
        }
 </code>
 </pre>
 
 > 따라서 NumberExpression으로 제작한 커스텀 정렬을 productRankPath의 변수에 담아 query DSL로 작성할 수 있다.
 전체적인 코드를 설명하면, main2 도메인으로부터 날짜가 2019-11-8이고 아이템 이름이 keyword에 해당하면 다음과 같은 정렬을 이룬다는 것이다.
 필자가 설명한 것처럼 가격은 desc나 asc로 편하게 작성할 수 있지만 , 품질과 같은 카테고리는 커스텀 정렬을 한 것을 볼 수 있다.
 
 <hr/>
 
### 3.2. 3중 필터링 정렬 검색
 ![image](https://user-images.githubusercontent.com/66015002/107529564-55c87d80-6bfe-11eb-98ec-71a278a51e6e.png)
 ![image](https://user-images.githubusercontent.com/66015002/107529628-67aa2080-6bfe-11eb-869b-8a4150d7e5b5.png)
> 쉽게 설명하면 예를 들어, 고객이 1순위로 가격이 낮은순 2순위로 품질이 높은순 3순위로 소매가인 상품을 보고 싶은 것이다.

<pre>
<code>
Page<Main> findByKeyword(String keyword, String nation, Pageable pageable,String priority1,String priority2,String priority3);
 </code>
 </pre>
 <hr/>
 
 > 3개의 필터가 필요하기 때문에 priority 변수도 총 3개를 불러왔다. (nation은 지역을 의미하는 변수이다)
 
 <pre>
<code>
JPQLQuery<Main> query = from(main).where(main.stateName.eq(nation)
                .and(main.day.eq(20191108))
                .and(main.itemName.contains(keyword)));

        if(priority1.equals("가격높은순")){
            query=query.orderBy(main.price.desc());
        }
        if(priority1.equals("가격낮은순")){
            query=query.orderBy(main.price.asc());
        }
        if(priority1.equals("품질높은순")){
            query=query.orderBy(productRankPath.asc());
        }
        if(priority1.equals("품질낮은순")){
            query=query.orderBy(productRankPath.desc());
        }
        if(priority1.equals("도매")) {
            query=query.orderBy(productClsRankPath.asc());
        }
        if(priority1.equals("소매")) {
            query=query.orderBy(productClsRankPath.desc());
        }

        if(priority2.equals("가격높은순")){
            query=query.orderBy(main.price.desc());
        }
        if(priority2.equals("가격낮은순")){
            query=query.orderBy(main.price.asc());
        }
        if(priority2.equals("품질높은순")){
            query=query.orderBy(productRankPath.asc());
        }
        if(priority2.equals("품질낮은순")){
            query=query.orderBy(productRankPath.desc());
        }
        if(priority2.equals("도매")) {
            query=query.orderBy(productClsRankPath.asc());
        }
        if(priority2.equals("소매")) {
            query=query.orderBy(productRankPath.desc());
        }

        if(priority3.equals("가격높은순")){
            query=query.orderBy(main.price.desc());
        }
        if(priority3.equals("가격낮은순")){
            query=query.orderBy(main.price.asc());
        }
        if(priority3.equals("품질높은순")){
            query=query.orderBy(productRankPath.asc());
        }
        if(priority3.equals("품질낮은순")){
            query=query.orderBy(productRankPath.desc());
        }
        if(priority3.equals("도매")) {
            query=query.orderBy(productClsRankPath.asc());
        }
        if(priority3.equals("소매")) {
            query=query.orderBy(productClsRankPath.desc());
        }
 </code>
 </pre>
 <hr/>
 
 > 이런 방식으로 각각 priority1 , 2 , 3 순서로 그에 해당하는 우선순위를 배당하는 방법이다. 확실히 노가다성이 보여, 좋은 코드는 아닌 것 같다.
 일단 직접 페이지에서 쇠고기를 검색해본 결과(가격 낮은순, 품질 높은순 , 소매가) 가격이 7000원으로 같으면 품질이 높은 소고기를 우선으로 보여주는 정렬이 확인 되었다.
 위 코드를 간추려 더 좋은 기능을 하는 코드를 만들려면 고민을 많이 해봐야 할 것 같다.
  <hr/>
  
 ### 4. 카카오 지도 API 
 ![image](https://user-images.githubusercontent.com/66015002/107529974-c2437c80-6bfe-11eb-8697-28afb1558782.png)
 
 Link: [Kakao Developer][kakao]
 
 [kakao]: https://developers.kakao.com/
 > 카카오 지도 api를 사용하여 각 시장별 위치 서비스를 제공하였다. 우선 kakao developer에서 map api를 받아온다. 그 다음 js에 api key를 저장하고 각종 기능들을 구현한다.
 각 시장들의 위치 좌표를 작성하여 저장하는 방식으로 구현하였다. 따라서 그 좌표를 보내주면 바로 그 시장을 볼 수 있다.
 <pre>
<code>
// 맨 오른쪽 변수 이름 수정하여 지도 변경 가능
const map = new kakao.maps.Map(container, mapName);

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤 생성
var mapTypeControl = new kakao.maps.MapTypeControl();

map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는 컨트롤 생성
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 마커 표시 위치
var markerPosition = mapName.center;

// 마커 생성
var marker = new kakao.maps.Marker({
    position: markerPosition
});
 </code>
 </pre>

### 5. 각 제품들의 월별 가격 변동 현황 사진
![image](https://user-images.githubusercontent.com/66015002/107530150-ee5efd80-6bfe-11eb-995b-74569406c32b.png)
 
