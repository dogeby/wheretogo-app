# 어디갈까

<img width="40%" src="https://github.com/user-attachments/assets/7674555b-a817-425b-888c-696a169405c8"/>

- **한국관광공사 TourApi**를 활용한 관광지 소개 앱입니다.
- 원하는 지역의 관광지, 숙박, 음식점, 진행 중인 축제/행사/공연 등을 찾을 수 있습니다.

## 주요 화면

### 홈 화면

<img width="30%" src="https://github.com/user-attachments/assets/eef46b53-4e80-418e-8aa6-65a4974727e0"/>
<img width="30%" src="https://github.com/user-attachments/assets/81efeb46-3c91-487f-b3ad-10570bf28105"/>
<img width="30%" src="https://github.com/user-attachments/assets/d72d6bb8-110a-4f2c-a2f8-3e8ef354fb3c"/>

- 현재 진행 중인 축제, 관광지, 숙박, 음식점 정보가 목록으로 표시됩니다. 각 항목을 클릭하면 상세 정보를 확인할 수 있습니다.
- 화면 상단의 버튼을 클릭하여 지역별 콘텐츠나 검색 화면으로 쉽게 이동할 수 있습니다.
- 목록 상단의 카테고리 타이틀을 클릭하면 해당 타입의 전체 목록을 탐색할 수 있습니다.

### 지역 선택 및 콘텐츠 목록 화면

<img width="18%" src="https://github.com/user-attachments/assets/0e8d989b-db42-4ddb-9438-b4a46dea5b02"/>
<img width="18%" src="https://github.com/user-attachments/assets/f7c91dfd-c623-418a-839d-91adbd9b7cfa"/>
<img width="18%" src="https://github.com/user-attachments/assets/cab6e2ed-1d80-4207-9948-de3d846aacdf"/>
<img width="18%" src="https://github.com/user-attachments/assets/9292d627-700a-40d0-9ea8-4da4b665923a"/>
<img width="18%" src="https://github.com/user-attachments/assets/f2355ed6-b400-4c64-87aa-917ad71b1981"/>

- 사용자가 원하는 지역을 선택하면, 해당 지역의 여행지, 축제/공연/행사, 숙박, 음식점 등의 콘텐츠 목록이 표시됩니다.
- 콘텐츠 목록은 카테고리로 필터링할 수 있어, 사용자가 원하는 정보를 빠르게 찾을 수 있습니다.


### 콘텐츠 상세 화면

<img width="20%" src="https://github.com/user-attachments/assets/dd023e81-eeb1-4399-901b-dda093c9ffcd"/>
<img width="20%" src="https://github.com/user-attachments/assets/45d636a5-fb08-44df-b7c1-aee924b41c84"/>
<img width="20%" src="https://github.com/user-attachments/assets/45c91b10-bf72-4ce9-b91b-f394c0236c9d"/>
<img width="20%" src="https://github.com/user-attachments/assets/3be5ddc7-fd1b-49bd-92cf-f7cabc7c0c3f"/>

- 콘텐츠의 사진, 카테고리, 개요, 전화번호, 주소 및 지도를 볼 수 있습니다.
- 사진을 클릭하면 사진을 확대할 수 있는 화면이 나타납니다.

### 검색 화면

<img width="30%" src="https://github.com/user-attachments/assets/13c07e8d-0dc4-4037-8ea1-da7b1fa9b759"/>
<img width="30%" src="https://github.com/user-attachments/assets/b5f31c48-8fd5-4445-98f1-11f19d300c94"/>
<img width="30%" src="https://github.com/user-attachments/assets/dfe35aba-038f-4645-83ff-6c643e83af2f"/>

- 키워드를 통해 원하는 콘텐츠를 찾을 수 있습니다.

## 기술 스택
- Kotlin
- Coroutine, Flow
- Compose, Material3
- Hilt
- Navigation
- Room
- Datastore
- Retrofit2
- Kotlinx.serialization
- Paging3
- Coil
- Junit4

## 기술 사용

### 앱 아키텍처 및 모듈화
- **MVVM**
  - MVVM 패턴을 사용하여 앱의 구조를 데이터와 UI 로직으로 분리했습니다.
- **멀티모듈**
  - 앱을 여러 모듈로 분리하여 기능별로 구분하고 빌드 시간을 단축했습니다.

### 데이터 저장
- **Datastore**
  - 안전한 데이터 저장을 지원하여 네트워크를 통해 받은 데이터를 캐시하는 데 사용했습니다.
- **Room**
  - 사용자가 검색한 키워드를 저장하는 데 사용했습니다. Room은 SQLite를 추상화하여 데이터베이스 작업을 간편하게 해줍니다.

### 네트워크 통신
- **Retrofit2**
  - REST API와의 네트워크 통신을 지원합니다. API 호출을 간편하게 처리할 수 있으며, 서버와의 데이터 교환을 원활하게 해주기 때문에 TourApi와의 통신에 사용했습니다.
- **Kotlinx.serialization**
  - JSON과 Kotlin 데이터 클래스 간의 변환을 지원하며, 기본값 지원과 null 안전성 때문에 사용했습니다.

### 데이터 페이징
- **Paging3**
  - TourApi의 대량 데이터를 효율적으로 로드하고 표시하는 데 사용했습니다. 사용자가 스크롤 하여 데이터의 끝에 도달하면 자동으로 추가 데이터를 요청하며, Coroutine과 Flow를 지원합니다.

### CI
- **GitHub Actions**
  - Pull Request 시 자동으로 테스트와 코드 스타일 검사를 수행합니다.
  - `keystore.properties`와 API 키를 보호하기 위해 GitHub Actions의 Secrets와 Variables 기능을 사용했습니다.

## 개발 일지
[App Ui Design](https://www.figma.com/design/cL2DmmFQVC06T6rtXKV5Z0/App?node-id=0-1&t=4oYAWNbrZVdCm2v3-1)
