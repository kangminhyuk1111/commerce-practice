## 📌 Summary
3단계 기획서를 통해 요구사항을 분석하고 Review테이블과 클래스 설계, Review 정책과 코드 흐름을 정리했습니다.

ReviewService에서 Repository를 참조했었는데 validator도 Repository를 참조하다보니 의존 관계가 명확하지 않아서 클래스를 명확히 지정해주기 위해 Repository를 바로받지 않고 ReviewManager라는 객체를 만들었습니다.

- ReviewService: 비즈니스 로직 흐름 제어
- ReviewManager: CRUD
- ReviewPolicy: 정책 검증(중복, 구매기간 등..)
- ReviewRepository: 데이터베이스 접근 계층

ReviewManger없이 Repository를 Service에서 바로 접근하는 것 보다 ReviewManager를 만들어서 사용하는게 Service가 책임을 명확하게 할 수 있다고 생각했습니다.

리뷰를 작성 완료하게 되면 성공했습니다만 보여주면 될 것 같아서 굳이 Review를 반환하지 않았습니다.

## 💬 Review Points
1. 추가적으로 생각해볼만한 정책이 있을까요? <- 요구사항 분석/리뷰 정책 검증에 작성했습니다.
2. 지금 현재 상태에서 Order라는 도메인이 아직 미구현 상태인데 없는 상태에서 대신 할 방법이 있을까요?
3. Review쪽 패키지 구조를 결정하는게 어렵게 느껴집니다. 준서님은 현재 클래스들을 가지고 패키지 구성을 어떻게 하실 것 같으신지 의견이 궁금합니다.
4. 강의에서 강사님은 Read쪽을 작성할때 ReviewFinder라는 객체를 하나만들어서 사용하시던데 이 패턴은 어떻게 생각하시나요? 저는 Manager에서 다 해결하면 안될까라는 생각이 들었습니다. 쓰기작업과 읽기작업을 객체로 분리하신 것 같습니다.

## ✅ Checklist
- [x] Review 도메인 요구사항 파악
- [x] Review 도메인 요구사항 바탕으로 코드 구현
- [] Review 도메인 테스트 코드 작성

## 📎 References