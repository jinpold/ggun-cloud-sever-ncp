ggun 프로젝트 - Backend Repository README

[프로젝트 개요]

ggun 프로젝트는 사용자 친화적인 주식 거래 플랫폼을 제공하는 것을 목표로 합니다. 
이 플랫폼은 AI 기반의 자동매매 기능을 통해 투자 리스크를 최소화하고, 사용자의 관리 시간을 단축하여 안정적이고 효율적인 투자를 추천합니다.
프로젝트는 Microservices Architecture (MSA) 기반으로 설계되었으며, 다양한 서비스들이 유기적으로 연동되어 사용자에게 최고의 경험을 제공합니다.

[아키텍처 개요]

1. MSA 구조
CONFIG 서버: 클라우드 환경에 배포된 설정 관리 서버로, 모든 MSA 서비스의 설정 파일을 중앙에서 관리하고, 고가용성을 제공합니다.
EUREKA 서버: 서비스 디스커버리와 등록을 담당하며, 각 서비스가 서로를 쉽게 발견하고 통신할 수 있도록 지원합니다.
GATEWAY 서버: Spring Cloud Gateway를 사용하여 API 요청을 라우팅하며, 인증 및 권한 부여를 처리합니다.
SECRET 서버: GitHub에 저장된 설정 파일을 기반으로 보안 관리 및 서비스별 설정을 담당합니다.

3. CI/CD 파이프라인
Jenkins: CI/CD 파이프라인을 구축하여 자동화된 빌드, 테스트, 배포를 수행합니다.
Docker: 각 서비스를 컨테이너화하여 효율적인 환경 구성을 지원합니다.
Kubernetes: NAVER CLOUD에 Kubernetes를 배포하여 안정적이고 확장 가능한 서비스를 운영합니다.

4. 서비스 구성
1. USER-SERVICE
주요 기능: JWT 기반 로그인 인증/인가, Oauth2 소셜 로그인, 사용자 게시판 제공, 사용자 데이터 관리
2. ADMIN-SERVICE
주요 기능: 관리자 대시보드, 통계 및 뉴스 데이터 시각화, JPA QueryDSL을 활용한 데이터 분석, 회원 관리 및 거래 내역 통계
3. ACCOUNT-SERVICE
주요 기능: 주식계좌 관리, 실시간 주가 정보 제공, AI 알고리즘 기반 자동매매, 금융 거래 처리
4. CHAT-SERVICE
주요 기능: 종목별 실시간 채팅, 다대다 채팅, SSE 기반 실시간 대화
5. ALARM-SERVICE
주요 기능: 공지 알림, 단체 메일 발송, SSE 기반 실시간 공지, Kafka를 통한 관리자 1:1 채팅, AWS S3 연동 파일 업로드/다운로드

6. 핵심 기능
   
- AI 기반 자동매매

Facebook의 Prophet 모델을 사용하여 10일 후의 주가를 예측합니다.
yfinance의 종목별 3년간 데이터를 이용하여 예측치를 생성하며, 예측된 주가 상승률과 사용자의 투자 성향을 비교하여 자동 매수/매도를 진행합니다.

- 모의투자 및 성능 평가, 비교 분석

한국투자증권 모의투자 API를 사용하여 수강생 10명의 계좌를 개설, 투자 성향에 맞춰 모의투자를 진행합니다.
예측된 주가와 실제 모의투자를 비교 분석하여 AI 모델의 성능을 평가합니다.

7. 기술 스택

[Backend]

- Framework: Spring Boot (MVC, WebFlux, Security, Data JPA, Cloud Config, Cloud Gateway, Cloud Netflix Eureka, JWT, OAuth2.0, FastAPI)

- Database: MySQL, MongoDB, Redis

[DevOps]

Tools: Jenkins, Docker, Kubernetes, Synology NAS, GitHub

8. 협업 도구
   
Tools: Github, Slack, Jira, Notion
