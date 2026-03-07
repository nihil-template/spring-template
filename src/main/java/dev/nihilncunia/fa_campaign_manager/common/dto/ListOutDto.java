package dev.nihilncunia.fa_campaign_manager.common.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "리스트 출력 DTO (Spring Data Page 확장형)")
public class ListOutDto<TData> {

  @JsonUnwrapped
  @Schema(description = "페이징 및 데이터 정보 (Page 객체의 모든 필드가 노출됩니다)")
  private Page<TData> page;

  /**
   * Page 객체를 기반으로 ListOutDto를 생성합니다.
   * @JsonUnwrapped에 의해 content, pageable, totalElements, totalPages, sort 등이 루트 레벨에 노출됩니다.
   */
  public static <T> ListOutDto<T> of(Page<T> page) {
    return ListOutDto.<T>builder()
      .page(page)
      .build();
  }

  /**
   * 단순 리스트를 기반으로 ListOutDto를 생성합니다. (페이징 미적용 시)
   */
  public static <T> ListOutDto<T> of(List<T> list) {
    // 페이징 미적용 시 unpaged PageImpl을 생성하여 Page 구조를 유지합니다.
    Page<T> page = new PageImpl<>(list, Pageable.unpaged(), list.size());
    return of(page);
  }
}
