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
- `./src/wargame/app/Program.java` 에서 확인 가능합니다


## 데모영상
- [LINK](https://youtu.be/7DalTaIuk_E)

## 실행
#### 컴파일
- `javac -d class ./src/wargame/*.java ./src/wargame/app/*.java`
#### 실행
- `java -ea -cp ./class wargame.app.Program `

## 이슈 로그
### 유닛 행동 이슈
##### 상황
- 모든 유닛은 각 프래임마다 특정 행동을 할수 있다. 예를들어 "지뢰"와 "똑똑한 지뢰"는 적이 와서 부딪히면 폭발한다. 그리고 "전차", "망령", "해병", "미사일 포탑"은 적이 공격 사정권 안에 있으면 공격할수 있다. 마지막으로 "전차", "망령", "해병"은 적이 시야안에 있으면 적을 내 공격권 안으로 넣기 위해서 움직일수 있다.
- 이런 동작들을 한 프래임안에 일괄적으로 처리해야한다.
##### 해결
- 각각 동작("부딪히면 폭발(ICollisionEventListner)", "사정권안에 있으면 적 공격(IThinkable)", "이동(IMovable)")을 정의하고 동작에 맞는 인터페이스를 만든다.
- 각 동작을 수행할 수 있는 유닛의 클래스가 각 동작의 인터페이스를 상속 받는다.
- 프로그램이 시작될 때 ICollisionEventListner, IThinkable, IMovable 배열에 각동작에 해당하는 유닛을 집어넣는다.
- 프래임 안에서 각 동작(ICollisionEventListner, IThinkable, IMovable)에 해당하는 인터페이스 배열을 반복문으로 돌리면서 동작을 다형적으로 실행한다
