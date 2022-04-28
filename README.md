## 스펙
- `javac 11.0.12`

## 프로젝트 설명
- 아래의 유닛들이 맵에 소환됩니다 `(영어이름, 한글이름, 심볼)`
```해병(Marine, 마린, M)
전차(Tank, 탱크, T)
망령(Wraith, 레이스, W)
미사일 포탑(Turret, 터렛, U)
지뢰(Mine, 마인, N)
똑똑한 지뢰(SmartMine, 스마트 마인, A)
파괴자(Destroyer, 디스트로이어, D)
```
- 50 프래임동안 맵에 소환된 유닛들은 특정한 행동(이동, 적발견, 공격)을 합니다
- 결과를 확인합니다

## 테스트
- cd 

## 데모영상
- [LINK](https://youtu.be/7DalTaIuk_E)

## 실행
#### 컴파일
- `javac -d class ./src/academy/pocu/comp2500/assignment3/*.java ./src/academy/pocu/comp2500/assignment3/app/*.java`
#### 실행
- `java -ea -cp ./class academy.pocu.comp2500.assignment3.app.Program`


