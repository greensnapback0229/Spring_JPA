package com.example.springboot_jpa;

import java.util.List;

import org.springframework.boot.SpringApplication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Chapter2 {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_study");
	EntityManager em = emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();

	public void practice2(){
		try{
			tx.begin();
			logic(em);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void logic(EntityManager em){
		String id = "id";
		Member member = new Member();
		member.setId(id);
		member.setUsername(id);
		member.setAge(2);

		//등록o
		em.persist(member);

		//수정
		member.setAge(20);

		//한 건 조회
		Member findMember = em.find(Member.class, id);
		System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

		//목록 조회
		List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
		System.out.println("members.size=" + members.size());

		em.remove(member);
	}
}
