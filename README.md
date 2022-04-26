# Project: QR_Check IN System
* 호텔의 대면 체크인 방식을 어플과 QR코드를 활용하여 비대면으로 전환
* 대학 졸업논문
* 2020.10 ~ 2021.06

## 0. Overview
- 최근 코로나로 인한 비대면 시스템 확산에 따라 직접 호텔키를 받아 체크인을 하는 것이 아닌 어플로 QR코드를 받아
호텔키 대신 사용함으로써 대면 접촉 최소화 및 기존 호텔키의 번거로운 휴대성과 분실 위험 방지를 하기 위함 입니다.
- 기존의 호텔 방을 예약할 경우에는 카드키 혹은 일반 키를 직접 대면으로 지급 받게 되고 키 같은 경우에는 분실 위험도가 높습니다.
그리고 여러 사람들이 같이 방을 쓰는 경우에는 키를 가지고 있는 한 사람에게 의존하여 방 문을 들어갈 수 있습니다.
따라서 호텔방을 예약할 때 해당 방에 대한 QR코드를 지급 받음으로써 방을 사용하는 이용자들간에는 QR코드를 공유할 수 있어 누구나
방문을 열 수 있습니다. 물론 QR코드가 타인에게 노출될 시 QR코드를 변경할 수 있습니다
</br></br>
<img src="https://user-images.githubusercontent.com/101568892/165222069-27193252-4e4a-4203-b46f-6fd77667d3ce.png">

## 1. Goals
- 방을 등록하고 예약할 수 있는 어플 구현
- QR코드 인식기와 실제 도어락을 연결
- 사용자와 방정보를 관리하는 DB설계
- 방 예약시 문의사항을 위한 채팅 구현

## 2. Function
- 로그인/회원가입 기능
- 방 등록/예약 기능
- 해당 방에 대한 QR코드 변환 기능
- 오픈 채팅방 기능
- QR코드 인식 후 상황에 따른 LCD센서와 부저센서 동작 및 도어락 동작

## 3. Flow Chart
<img src="https://user-images.githubusercontent.com/101568892/165222378-cf105955-5c8f-4a7d-80b4-7e9dddd02820.JPG">

## 4. Development Environment
<img src="https://user-images.githubusercontent.com/101568892/165222427-11fba32e-df2c-438c-ae3b-491926a18022.png" width="500">

## 5. UI
<img src="https://user-images.githubusercontent.com/101568892/165222757-b523f957-1cfa-4533-8605-2ee900af505b.png" width="250"> <img src="https://user-images.githubusercontent.com/101568892/165222819-9db36907-5ff4-4b87-9942-656db9e340b3.png" width="260">  
<img src="https://user-images.githubusercontent.com/101568892/165222914-af3003e6-7f8f-4f30-930e-97f242ae6b68.png" width="250"><img src="https://user-images.githubusercontent.com/101568892/165222978-0356eee1-40c5-45c1-81d9-068e56d6b109.png" width="250">   
<img src="https://user-images.githubusercontent.com/101568892/165223217-2ea5924f-8201-451b-a0c5-88210ea573f7.png" width="250">
