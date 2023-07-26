package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
  
  List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
  
  @Query("select m from Member m where m.username = :username and m.age = :age")
  List<Member> findMember(@Param("username") String username, @Param("age") int age);
  
  @Query("select m.username from Member m")
  List<String> findUsernameList();
  
  @Query("select new study.datajpa.dto.MemberDTO(m.id, m.username, t.name) from Member m join m.team t") // DTO 조회
  List<MemberDTO> findMemberDTO();
  
  @Query("select m from Member m where m.username in :names")
  List<Member> findByNames(@Param("names") Collection<String> names);
  
  List<Member> findListByUsername(String username); // 컬렉션 조회
  Member findMemberByUsername(String username); // 단건 조회
  
  Optional<Member> findOptionalByUsername(String username); // 옵셔널 조회
  // top3와 같은 기능도 있다!
  
  @Query(value = "select m from Member m", countQuery = "select count(m.username) from Member m") // totalCount의 성능 개선을 위한 쿼리 분리하기
  Page<Member> findByAge(int age, Pageable pageable);
  
  Slice<Member> findSliceByAge(int age, Pageable pageable);
  
  @Modifying(clearAutomatically = true) // em.clear를 자동으로 해주는 기능
  @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
  int bulkAgePlus(@Param("age") int age);
  // 벌크 연산 시 영속성 컨텍스트를 무시하고 쿼리를 바로 디비에 날려버린다.
}
