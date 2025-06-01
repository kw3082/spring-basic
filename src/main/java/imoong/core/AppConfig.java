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

public class AppConfig {

    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    private static MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }


}
