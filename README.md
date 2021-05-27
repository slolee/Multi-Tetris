# 사용된 기술
- <b>Programming Language</b> : JAVA (JDK 1.8.0_191)
- <b>UI Framwork</b> : Swing
- <b>Database</b> : Oracle Database 11g Express Edition
<br></br>

# 프로젝트 소개
싱글 플레이와 1대1 대전이 가능한 테트리스 게임이다.

Socket/Thread를 통해 여러 컴퓨터에서 동시에 접속해 게임을 즐길 수 있도록 구성했다. 

싱글 플레이를 통해 기록을 갱신할 수 있고 이를 통해 다른 사용자와 경쟁할 수 있다. Score가 높아질수록 점점 게임의 속도를 증가시켜 플레이어가 도전하는 재미를 얻을 수 있도록 구현했다.

대기실을 통해 다른 플레이어와 1대1 대전을 즐길 수 있다. 여러줄을 한번에 터뜨리면 상대방에게 공격이 가도록 구현해 서로 경쟁함에 있어서 재미있는 요소를 추가했다. 서로 대전한 결과를 사용자의 상태정보에서 확인할 수 있고 그에 맞게 승률이 출력되도록 구현했다.
<br></br>

# 기능 소개
![1](https://user-images.githubusercontent.com/38906956/119756103-01f85080-bede-11eb-84fb-ebf15159bc85.png)
![2](https://user-images.githubusercontent.com/38906956/119756110-06246e00-bede-11eb-88f8-5a58e6a3e5b2.png)
![3](https://user-images.githubusercontent.com/38906956/119756112-07559b00-bede-11eb-9c3c-16e517c1c561.png)
![4](https://user-images.githubusercontent.com/38906956/119756115-091f5e80-bede-11eb-9708-145dcf4dba91.png)
![5](https://user-images.githubusercontent.com/38906956/119756123-0b81b880-bede-11eb-9d09-e868879ce9a5.png)
![6](https://user-images.githubusercontent.com/38906956/119756126-0de41280-bede-11eb-8a11-fc2ca1c98405.png)
<br></br>

# 사용법
## Server
1. Tetris Server 폴더를 서버 컴퓨터에 다운로드 받는다.
2. Oracle Database 11g Express Edition 을 설치한다.
[다운로드](https://www.oracle.com/database/technologies/xe-prior-releases.html)
3. 데이터베이스에 접속해 tetrisDB 사용자를 추가하고 패스워드를 tetris로 등록한다.
4. [1] 쿼리를 이용해 테이블을 생성한다.
5. Tetris Server를 실행한다.

[1]
 ```create table users(ID varchar(32) primary key, PW varchar(32) not null, NAME varchar(32), HIGH_SCORE number, TOTAL_WIN number, TOTAL_LOSE number, TOTAL_RATING float(2));``` 
<br></br>

## Client
1. Tetris Client, Tetris DB, Font 폴더를 클라이언트에 다운로드 받는다.
2. Tetris DB, Font 폴더를 C드라이브 밑으로 이동시킨다.
3. Tetris Client를 실행한다. (EXE파일로 만들어 배포하면 Eclipse 없이 사용 가능.)
