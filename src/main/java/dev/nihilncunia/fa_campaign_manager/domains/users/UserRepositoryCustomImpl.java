package dev.nihilncunia.fa_campaign_manager.domains.users;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.nihilncunia.fa_campaign_manager.common.dto.ListOutDto;
import dev.nihilncunia.fa_campaign_manager.common.helper.SearchHelper;
import dev.nihilncunia.fa_campaign_manager.common.repository.CustomRepositorySupport;

public class UserRepositoryCustomImpl extends CustomRepositorySupport implements UserRepositoryCustom {

  private final UserMapper userMapper;

  public UserRepositoryCustomImpl(JPAQueryFactory queryFactory, UserMapper userMapper) {
    super(queryFactory);
    this.userMapper = userMapper;
  }

  @Override
  public ListOutDto<UserOutDto> findAll(UserInDto userInDto) {
    QUserEntity user = QUserEntity.userEntity;

    BooleanBuilder builder = SearchHelper.buildAll(userInDto, user);

    JPAQuery<UserEntity> contentQuery = jpaQueryFactory
        .selectFrom(user)
        .where(builder);

    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(user.count())
        .from(user)
        .where(builder);

    return applyDynamicPagination(
        userInDto,
        user,
        contentQuery,
        countQuery,
        user.id.desc(),
        userMapper::toDtoList);
  }
}
