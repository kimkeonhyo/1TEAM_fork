## 프로젝트 소개

| 개발 기간 : 2024.12.06 - 2025.01.16

실시간 주식 시세 조회 서비스 성능 최적화 프로젝트

## 목표
### 클라우드 퍼포먼스 최적화
1. 개발 및 운영 환경에서의 로드 밸런싱 및 스케일링 테스트 실행
- 로드 밸런싱 전략 수립
- 자동 확장 최적화
2. 고가용성 및 재해 복구를 고려한 클라우드 서비스의 오류 대응 전략 개발
3. 실시간 모니터링 시스템과 경보 설정을 통한 운영 효율성 향상

## 기술 스택

Front: React(v18.3.1)

Back: SpringBoot(3.4.0), Java(17)

- Dependency: web, h2, lombok, Slf4j, springdata JPA

DB: Redis(7.4) & MySQL(8.0)

Message Queue: Kafka(3.8)

Infra: ECS, ALB, RDS, SNS, Lambda, Fargate, Route53

Monitoring: CloudWatch, Grafana

## 브랜치전략 & 커밋컨벤션

### Branch Convention

`HEADER/{내용}` 

e.g. `master`, `develop`, `feature/login`

|HEADER|설명|
|:--:|:--:|
|master|기준이 되는 브랜치|
|develop|개발 브랜치. feature 브랜치에서 작업한 기능이 merge되는 브랜치|
|feature|기능 단위로 개발하는 브랜치. 기능 개발이 완료되면 develop 브랜치에 merge|

### Commit Convention

`HEADER: {내용}` 

e.g. `feat: 로그인 기능 구현`

|HEADER|설명|
|:--:|:--:|
|feat|새로운 기능 구현|
|refactor|내부 로직은 변경하지 않고 기존 코드 리팩토링|
|fix|버그, 오류, 충돌 해결|
|add|feat 이외의 부수적인 코드 추가, 라이브러리 추가 작업|
|update|기능 수정|
|chore|잡일. 버전 코드 수정, 패키지 구조 변경, 파일 이동, 가독성이나 변수명 수정|

## 아키텍처
![Architecture](https://github.com/user-attachments/assets/9d1e28e1-cb48-49a5-8ea9-6553ba1d1423)




## 팀원 & 역할 분담

|팀원|Github|역할|
|:------:|:---:|:---:|
|[류경표](https://github.com/kpryu6)|<img src="https://avatars.githubusercontent.com/u/113777043?v=4" height=90 width=90></img>|API 개발, 재해복구 및 알람 서비스 구현|
|[김건효](https://github.com/kimkeonhyo)|<img src="https://avatars.githubusercontent.com/u/178240347?v=4" height=90 width=90></img>|API 개발, 재해복구 및 알람 서비스 구현|
|[김연희](https://github.com/Yeonhee-Kim)|<img src="https://avatars.githubusercontent.com/u/76810691?v=4" height=90 width=90></img>|API 개발, DB 관리|
|[박서연](https://github.com/seoyeon0201)|<img src="https://avatars.githubusercontent.com/u/125520029?v=4" height=90 width=90></img>|API 개발, 기본 인프라 구성|
|[나보영](https://github.com/naboyeong)|<img src="https://avatars.githubusercontent.com/u/70682434?v=4" height=90 width=90></img>|API 개발, 컨테이너 서비스|
|[전재열](https://github.com/woduf1020)|<img src="https://avatars.githubusercontent.com/u/87353985?v=4" height=90 width=90></img>|API 개발, 모니터링 구성|


## 프로젝트 구조(추후)
## 기능 (추후)
