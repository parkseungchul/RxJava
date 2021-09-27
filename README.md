## [RxJava2 강의](https://www.youtube.com/playlist?list=PLogzC_RPf25EOeUVOnOIMDCYRhNwYTo9D)

### [001. RxJava2 기본 문법 Generic, Lambda, Function, Stream](https://youtu.be/Arr06MUsPLY) 
- 00:00 Why Java?
- 01:38 Generic Type
- 25:26 Lambda
- 44:37 Functional Interface (Function, Consumer, Predicate, Supplier)
- 1:05:04 Stream

### [002. RxJava2 생산자와 소비자](https://youtu.be/sdbccXXnulE)
- 00:00 환경 구성
- 02:24 flowable subscribe 생산자와 소비자
- 11:24 ConnectableFlowable (hot publisher)
- 25:03 disposable subscriber 구독 해지
- 32:02 new Subscriber 로 구독 과정 보기
- 38:28 single, maybe, mono, flux publisher 타입
- 44:18 onBackpressureBuffer publisher 배압 전략

### [003. RxJava2  데이터 변환 연산자](https://youtu.be/lEsf90DyKYE)
- 00:00 지난 시간 내용
- 01:03 fromArray fromIterable 배열, 리스트 publisher
- 10:17 fromCallable 반환값을 끝으로 통지하는 publisher
- 11:44 range 지정한 숫자만큼 통지 publisher
- 13:13 interval 지정한 간격만자 숫자 통보 publisher
- 14:57 timer 지정한 시간 지난 후에 0을 리턴하는 publisher
- 16:26 defer 구독한 시점에 publisher 데이터 생성
- 21:13 map 데이터 변환
- 24:06 floatMap Multi Thread 순서 보장 안됨 (얍삽한 토끼)
- 39:21 concatMap Single Thread 순서 보장 (느린 거북이)
- 41:18 concatMapEager Multi Thread 순서 보장 (빠른 거북이)
- 43:13 merge 두 개의 publisher 합체
- 45:34 retry 재 처리 publisher
- 48:31 onErrorReturn 에러 처리 publisher
- 50:21 toList Single list 만들어주는 마법 (OOM 위험)
- 53:04 toMap Single Map 만들기 (OOM 위험)
- 58:45 toMultiMap Single MultiMap 만들기 (OOM 위험)

### [004. RxJava2  데이터 제한과 결합 연산자](https://youtu.be/T4xL8lGVi1o)
- 00:00 지난시간 학습 내용
- 02:04 filter 걸러주는 녀석
- 06:27 distinct, distinctUtilChanged 강아지들의 염원 증복 제거
- 14:17 take takeUtil takeWhile takeLast 나한테 몇 개나 어떻게 줄 수 있어요
- 18:54 skip skipUtil skipWhile skipLast 너는 좀 건너뛰자
- 22:32 throttleFirst throttleLast throttleWithTimeout debounce 특정 조건으로 조절
- 30:37 sample 해당 시점에서의 데이터를 샘플링
- 33:19 elementAt elementAtOrError 지정 된 값을 가져오거나 대채하던가
- 38:33 merge mergeDelayError 데이터를 잘 섞어 보자
- 49:25 concat concatArrayDelayError 데이터를 잘 연결 해 보자
- 54:10 concatEager concatArrayEagerDelayError 데이터를 좀 빠르게 잘 연결 해 보자
- 57:26 startWith startWithArray 자신은 나중에 데이터는 먼저
- 1:04:10 zip 두 개의 합