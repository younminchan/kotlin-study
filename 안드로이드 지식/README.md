Android 개발하면서 필요한 기초 지식들

1. JAVA / Kotlin 의 차이
[구글은 2017년 5월 Google I/O에서 코틀린(kotlin)을 공식 언어로 채택, 2019년 Google I/O에서 코틀린 퍼스트 선언하여 코틀린을 메인언어로 지원하고 있습니다.]

*JAVA(객체지향 프로그래밍 -  클래스 내부에서만 로직 생성)
*Kotlin(함수형 프로그래밍 - 어디서나 사용가능)

Kotlin 장점: 표현력이 높고 간결함, 더 안전한 코드, 호환성, 구조화된 동시 실행(**코루틴(Coroutine)

*함수형 프로그래밍이란? 

- 변수와 상수: java는 final 유무(있으면 상수(변하지않음)) kotlin은 (var, val(상수)
- view의 사용 -> 자바는 findViewById로 접근 / kotlin은 id로 바로 접근 가능
- NULL의 안정성 -> 자바는 @Nullable / 코틀린은 ? 로 간편하게 null이 올 수 있는지 확인가능
- 객체초기화 -> 코틀린은 apply로 객체 따로 명시 없이 바로 객체함수 사용 가능
- Data Class -> 자바는 get, set 모두 선언해줘야함 / kotlin은 변수 선언으로 바로 get, set 모두 포함









2.코루틴(Coroutine)이란?
(안드로이드 공식문서) 코루틴은 비동기적으로 실행되는 코드를 간소화 하기 위해 Android에서 사용할 수 있는 동시 실행 설계 패턴
하나의 쓰레드 내에서 여러 개의 코루틴이 실행되는 개념으로 비동기 프로그래밍에 권장되는 동시 실행 설계 패턴

안드로이드 jetpack 라이브러리(AAC)에서는 코루틴을 쉽게 사용할 수 있도록 LifeCycle에 맞는 Scope제공

*CoroutineScope: 사용자 지정
*GlobalScope: 앱이 실행될 때부터 종료될 때까지 실행
*ViewModelScope: ViewModel 대상, ViewModel이 제거되면 코루틴 작업이 자동으로 취소
*LifecycleScope: Lifecycle 객체대상(Activity, Fragment, Service..) Lifecycle이 끝날 때 코루틴 작업이 자동으로 취소
*liveData: LiveData 대상, LiveData가 활성화되면 실행을 시작하고 비활성화되면 자동으로 취소됩니다.


-GlobalScope: 싱글톤으로 어플리케이션 생명주기에 따라 동작 / 과도하게 사용하면 어플리케이션 동작에 문제를 줄 수 있음 / cancel처리가 복잡 / throw exception 처리 복잡










Dispatchers.Main : 안드로이드의 메인 쓰레드로, UI 작업을 위해 사용해야 합니다.
예를 들어, UI를 구성하거나 LiveData를 업데이트 할 때 사용됩니다.

Dispatchers.IO : 네트워크, 디스크 I/O 실행에 최적화되어 있습니다.
예를 들어, Retrofit으로 네트워크 통신을 하거나, File이나 Room 데이터베이스에서 데이터를 읽고/쓸 때 사용됩니다. 

Dispatchers.Default : CPU 사용량이 많은 무거운 작업 처리에 최적화 되어 있습니다.
예를 들어, 데이터를 가공하거나 복잡한 연산, JSON 파싱을 할 때 주로 사용됩니다. 

1. 어떤 쓰레드에서 실행할 것 인지 Dispatchers 를 정하고 (Dispatchers.Main, Dispatchers.IO, Dispatchers.Default)

2. 코루틴이 실행될 Scope를 정하고 (CoroutineScope, ViewModelScope, LifecycleScope, liveData...)

3. launch 또는 async로 코루틴을 실행 시키면 됩니다!




- Thread에 비해 매우 가벼움
- 






3. 오버로딩 / 오버라이딩

*오버로당: 메소드 이름이 같고, 매개변수의 개수나 타입이 달라야한다.
*오버라이딩: 부모클래스를 상속받아 자식클래스에서 부모에있는 class를 재정의하기위해 사용