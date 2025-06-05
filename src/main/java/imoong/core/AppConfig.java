package imoong.core;

import imoong.core.discount.DiscountPolicy;
import imoong.core.discount.FixDiscountPolicy;
import imoong.core.discount.RateDiscountPolicy;
import imoong.core.member.MemberRepository;
import imoong.core.member.MemberService;
import imoong.core.member.MemberServiceImpl;
import imoong.core.member.MemoryMemberRepository;
import imoong.core.order.OrderService;
import imoong.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public static MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }


}
