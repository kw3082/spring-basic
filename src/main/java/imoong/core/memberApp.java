package imoong.core;

import imoong.core.member.Grade;
import imoong.core.member.Member;
import imoong.core.member.MemberService;
import imoong.core.member.MemberServiceImpl;

public class memberApp {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        Member member = new Member(1L, "A", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("findMember = " + findMember.getName());
        System.out.println("member = " + member.getName());
    }
}
