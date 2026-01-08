package mocka.orm.entity.orm.mybatis;

import mocka.orm.entity.orm.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {

    @Select("SELECT * FROM member")
    List<Member> findAll();

    Member findById(Long id);

    void insert(Member member);
}