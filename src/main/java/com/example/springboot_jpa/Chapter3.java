package com.example.springboot_jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Chapter3 {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_study");
	EntityManager em = emf.createEntityManager();
	EntityTransaction transaction = em.getTransaction();

	public void practice3(){
		//준영속(detached)
		Member member = new Member();
		member.setId("1");
		member.setUsername("user1");

		//엔티티를 영속(persist entity)
		em.persist(member);

		//엔티티 조회_1차캐시에 없으면 DB에서 조회(inquiry entity from 1st level cache. If it's not exist, search from DataBase)
		Member findMember = em.find(Member.class, "1");
		System.out.println("member1's id: "+findMember.getId());

		//같은 엔티티 인스턴스(identical entity instance -> "==" compare is true in java)
		Member a = em.find(Member.class, "1");
		Member b = em.find(Member.class, "1");

		boolean x = a==b? true:false;
		System.out.println("a == b is " + x);

		//create member
		Member newMemberA = new Member("memberA", "A", 25);
		Member newMemberB = new Member("memberB", "B", 25);

		//트랜잭션 시작(transaction start)
		transaction.begin();

		//persist entity & 아직 DB에 저장하지 않음(not save at DB yet)
		em.persist(newMemberA);
		em.persist(newMemberB);

		//커밋하는 순간 DB에 Insert SQL을 보낸다.(Send Insert SQL when call "commit()")
		transaction.commit();

		transaction.begin();
		newMemberA = em.find(Member.class, "memberA");
		em.remove(newMemberA);
		transaction.commit();

		
	}
}
