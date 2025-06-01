package imoong.core;

import imoong.core.member.Grade;
import imoong.core.member.Member;
import imoong.core.member.MemberService;
import imoong.core.member.MemberServiceImpl;
import imoong.core.order.Order;
import imoong.core.order.OrderService;
import imoong.core.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        OrderService orderService = appConfig.orderService();
        MemberService memberService = appConfig.memberService();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
        System.out.println("order.calculatePrice() = " + order.calculatePrice());

    }

}
