
```
paymentservice
├─ .gitignore
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ pplbo
   │  │        └─ ecommerce
   │  │           └─ paymentservice
   │  │              ├─ config
   │  │              │  └─ KafkaConfig.java
   │  │              ├─ controller
   │  │              │  └─ PaymentController.java
   │  │              ├─ dto
   │  │              │  ├─ PaymentRequest.java
   │  │              │  └─ PaymentResponse.java
   │  │              ├─ HelloWorldController.java
   │  │              ├─ kafka
   │  │              │  └─ PaymentResponseProducer.java
   │  │              ├─ model
   │  │              │  └─ Payment.java
   │  │              ├─ PaymentserviceApplication.java
   │  │              ├─ repository
   │  │              │  └─ PaymentRepository.java
   │  │              └─ service
   │  │                 └─ PaymentService.java
   │  └─ resources
   │     └─ application.properties
   └─ test
      └─ java
         └─ com
            └─ pplbo
               └─ ecommerce
                  └─ paymentservice
                     └─ PaymentserviceApplicationTests.java

```