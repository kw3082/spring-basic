package imoong.core.singleton;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class StatefulServiceTest {

    // 기본 테스트 - 이건 @Configuration 없어도 같은 인스턴스
    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        System.out.println("=== 기본 테스트 (@Configuration 없음) ===");
        System.out.println("statefulService1 = " + statefulService1);
        System.out.println("statefulService2 = " + statefulService2);
        System.out.println("같은 인스턴스? " + (statefulService1 == statefulService2));
    }

    // @Configuration 차이를 보기 위한 의존성 있는 테스트
    @Test
    void configurationDifference(){
        System.out.println("\n=== @Configuration 없는 경우 ===");
        ApplicationContext ac1 = new AnnotationConfigApplicationContext(TestConfigWithoutConfiguration.class);

        System.out.println("\n=== @Configuration 있는 경우 ===");
        ApplicationContext ac2 = new AnnotationConfigApplicationContext(TestConfigWithConfiguration.class);
    }

    // @Configuration 없는 설정
    static class TestConfig {
        @Bean
        StatefulService statefulService(){
            return new StatefulService();
        }
    }

    // 의존성이 있는 경우 - @Configuration 없음
    static class TestConfigWithoutConfiguration {
        @Bean
        public CommonService commonService() {
            System.out.println("CommonService 생성됨! 해시코드: " + this.hashCode());
            return new CommonService();
        }

        @Bean
        public ServiceA serviceA() {
            System.out.println("ServiceA 생성 - CommonService 주입: " + commonService().hashCode());
            return new ServiceA(commonService()); // 직접 메서드 호출
        }

        @Bean
        public ServiceB serviceB() {
            System.out.println("ServiceB 생성 - CommonService 주입: " + commonService().hashCode());
            return new ServiceB(commonService()); // 또 직접 메서드 호출
        }
    }

    // 의존성이 있는 경우 - @Configuration 있음
    @Configuration
    static class TestConfigWithConfiguration {
        @Bean
        public CommonService commonService() {
            System.out.println("CommonService 생성됨! 해시코드: " + this.hashCode());
            return new CommonService();
        }

        @Bean
        public ServiceA serviceA() {
            System.out.println("ServiceA 생성 - CommonService 주입: " + commonService().hashCode());
            return new ServiceA(commonService()); // CGLIB 프록시가 같은 인스턴스 반환
        }

        @Bean
        public ServiceB serviceB() {
            System.out.println("ServiceB 생성 - CommonService 주입: " + commonService().hashCode());
            return new ServiceB(commonService()); // 같은 인스턴스 반환
        }
    }

    // 테스트용 클래스들
    static class CommonService {
        // 공통으로 사용할 서비스
    }

    static class ServiceA {
        private final CommonService commonService;

        public ServiceA(CommonService commonService) {
            this.commonService = commonService;
        }
    }

    static class ServiceB {
        private final CommonService commonService;

        public ServiceB(CommonService commonService) {
            this.commonService = commonService;
        }
    }

    static class StatefulService {
        // 기존 StatefulService
    }
}