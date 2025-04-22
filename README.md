# 경매 시스템 예제

경매 도메인에 정통 "도메인 주도 설계"와 "클린 아키텍처"를 반영한 api 입니다.

경매 모듈을 세 관점으로 분리 했습니다. 애플리케이션, 도메인, 인프라 입니다.

각 모듈들은 각 관심사에 해당하는 주요 객체들과 서비스를 가집니다. 여기서 "서비스"란 **객체 간 협력을 다루어 로직을 제공**하는 것을 의미합니다. 고전적인 계층형 아키텍처에서든 클린 아키텍처나 엔터프라이즈 애플리케이션이나 도메인 주도 설계 관점에서 service의 본질적으로 레이어 개념이 아니라 **책임**의 개념입니다.

```
[APP]       → Usecase (App Service)
[Domain]    → Domain Entity + Domain Service
[Infra]     → DataGateway(Repository Impliments) / Query (Infra Service) + JPA Repository / MyBatis / Message Queue / File System

auction-practice/
├── auction-app/       # 애플리케이션 계층
│   ├── api
│   ├── dto
│   │   ├── request
│   │   └── response
│   └── service           # usecase - 애플리케이션 기능 로직
├── auction-domain/    # 도메인 계층
│   ├── command
│   ├── entity
│   └── service           # domain - 비즈니스 로직
├── auction-infra/     # 인프라 계층
│   ├── jpa
│   ├── projection
│   └── service           # 공유 의존성과의 동작 구현 로직
```

## 애플리케이션 관심사

app 모듈은 api(Application Programming Interface, API)로써 Presentation과 Usecase로 구성됩니다.

- **Presentation**: 사용자의 요청(Request) 을 받아서, 시스템 내부로 전달하고, 내부에서 처리된 결과인 응답(Response) 을 사용자에게 전달합니다.
- **Usecase**(Application Service): 애플리케이션 서비스를 다룹니다. 하나의 유스케이스(기능 단위)에 대한 트랜잭션을 처리합니다.

애플리케이션 로직에 대한 테스트를 수행합니다.

## 도메인 관심사

domain 모듈은 가장 고수준의 안정된 의존성을 가진 비즈니스 로직을 다룹니다. 도메인 서비스와 애그리거트로 구성됩니다.

- **Domain Service**: 도메인 서비스는 비즈니스 로직을 애플리케이션 로직으로 노출하지 않는 것이 목적입니다.
- **Aggregate**: 애그리거트는 도메인 모델로서 도메인에 대한 책임을 갖습니다.

비즈니스 로직에 대한 단위 테스트를 수행합니다.

## 인프라 관심사

infra 모듈은 기술적인 로직을 다룹니다. 인프라 서비스와 공유 의존성을 다루는 객체들로 이루어집니다.

- **Infra Service**: 인프라 서비스는 비즈니스 로직과 애플리케이션 로직에 대한 필요한 실제 구현을 제공해줍니다.
- **Shared Dependency**: 스프링 데이터 JPA Repository 같은 영속성 인터페이스나 구현체 같은 것들을 의미합니다.

기술적 관점에 대한 테스트를 수행합니다.
