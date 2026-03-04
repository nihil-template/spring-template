package dev.nihilncunia.fa_campaign_manager.common;

import dev.nihilncunia.fa_campaign_manager.common.dto.DeletedListDto;

public abstract class CommonMapper {
  
  /**
   * 삭제 처리 결과 정보를 DeletedListDto로 변환합니다.
   * 모든 Mapper에서 공통으로 사용 가능합니다.
   *
   * @param entityType 삭제된 엔티티 타입 이름
   * @param deletedCount 삭제된 건수
   * @return DeletedListDto 객체
   */
  public static DeletedListDto toDeletedListDto(String entityType, Integer deletedCount) {
    return DeletedListDto.builder()
      .entityType(entityType)
      .deletedCount(deletedCount)
      .build();
  }
}
